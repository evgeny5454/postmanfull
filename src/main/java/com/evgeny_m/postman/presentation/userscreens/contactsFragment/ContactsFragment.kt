package com.evgeny_m.postman.presentation.userscreens.contactsFragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgeny_m.data.models.Contact
import com.evgeny_m.data.models.ContactRoom
import com.evgeny_m.data.models.UserData
import com.evgeny_m.data.repository.dataContacts
import com.evgeny_m.data.viewmodels.UserViewModel
import com.evgeny_m.postman.R
import com.evgeny_m.postman.databinding.FragmentContactsBinding
import com.evgeny_m.postman.presentation.userscreens.settings.utils.initBackButton
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModel
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
const val PERMISSION_REQUEST = 200

class ContactsFragment : Fragment() {

    private lateinit var binding: FragmentContactsBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var viewModelSettings: SettingsViewModel
    private var listDatabase = mutableListOf<String>()
    private var emptyList = false

    init {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        viewModelSettings = ViewModelProvider(
            this,
            SettingsViewModelFactory(requireContext())
        )[SettingsViewModel::class.java]
        initBackButton(binding.toolbar)
        val adapter = ContactsAdapter(requireContext())
        val layoutManager = LinearLayoutManager(requireContext())
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter


        userViewModel.readAllContacts.observe(viewLifecycleOwner, Observer { list ->
            CoroutineScope(Dispatchers.IO).launch {
                initContacts()
            }

            if (list.isEmpty()) {
                binding.progress.visibility = View.VISIBLE
                emptyList = true

            } else {
                val listToAdapter = mutableListOf<UserData>()
                list.forEach { it ->
                    val user = UserData(
                        id = it.dataBaseId,
                        name = it.name,
                        status = it.status,
                        photo = it.photo
                    )
                    listToAdapter.add(user)
                }
                binding.progress.visibility = View.GONE
                adapter.addContacts(listToAdapter)
                /*listToAdapter.forEach {
                    val id = it.id
                    Log.d("LISTDATABASE", id)
                    listDatabase.add(id)
                    Log.d("LISTDATABASE", listDatabase.toString())
                }*/
                emptyList = false
            }
        })

        dataContacts.observe(viewLifecycleOwner, Observer { users ->
            users.forEach { user ->
                if (listDatabase.contains(user.id)) {
                    Log.d("ADDNEWUSER",
                        "listDatabase contains ${user.id} ")
                } else {
                    Log.d("ADDNEWUSER",
                        "listDatabase not contains ${user.id} ")
                    userViewModel.readAllContacts
                        .observe(viewLifecycleOwner, Observer {userDatabase ->
                            userDatabase.forEach {
                                it.dataBaseId
                                Log.d("ADDNEWUSER", it.dataBaseId)
                            }
                            Log.d("ADDNEWUSER",
                                "users size =${users.size} database size = ${userDatabase.size} ")
                            if (users.size > userDatabase.size && userDatabase.isEmpty() && !listDatabase.contains(user.id)) {
                                addContactToRoom(user)
                                Log.d("ADDNEWUSER", "user id ${user.id} added ")
                            } else if (users.size > userDatabase.size && userDatabase.isNotEmpty() && !listDatabase.contains(user.id))  {
                                addContactToRoom(user)
                                Log.d("ADDNEWUSER",
                                    "users size =${users.size} database size = ${userDatabase.size} ")
                            }
                        })
                }
            }
        })

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.clear -> {
                    findNavController().popBackStack()
                    userViewModel.deleteAllData()
                    true
                }
                else -> false
            }
        }
        return binding.root
    }


    private fun addContactToRoom(user: UserData) {
        val listToRoom = mutableListOf<ContactRoom>()
        val newUser = ContactRoom(
            id = 0,
            dataBaseId = user.id,
            name = user.name,
            status = user.status,
            photo = user.photo
        )
        listToRoom.add(newUser)
        userViewModel.addListContacts(listToRoom)
        Log.d("SizeContactsFromBD", "${listToRoom.size}")
    }

    @SuppressLint("Range")
    private fun initContacts() {

        if (checkPermission(READ_CONTACTS)) {
            val arrayContacts = arrayListOf<Contact>()
            val cursor = requireActivity().contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            cursor?.let {
                while (cursor.moveToNext()) {
                    val displayName =
                        it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phone =
                        it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val contact = Contact(
                        name = displayName,
                        phone = phone.replace(Regex("[\\s,()-]"), "")
                    )
                    if (contact.phone[0].toString() == "8") {
                        contact.phone = contact.phone.replaceFirst("8", "+7")
                    }
                    if (arrayContacts.contains(contact)) {
                        arrayContacts.remove(contact)
                    }
                    arrayContacts.add(contact)
                }
            }
            cursor?.close()
            viewModelSettings.checkContacts(arrayContacts)
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= 23
            && ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(permission),
                PERMISSION_REQUEST
            )
            false
        } else true
    }

    companion object {
        //private lateinit var listContacts: List<ContactRoom>
    }
}