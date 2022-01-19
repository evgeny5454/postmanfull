package com.evgeny_m.postman.data.repository

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.evgeny_m.postman.data.utils.AppValueEventListener
import com.evgeny_m.postman.domain.domain.models.DomainDataContact
import com.evgeny_m.postman.domain.domain.repository.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import java.util.concurrent.TimeUnit

const val NODE_USERS = "users"
const val NODE_USERS_NAMES = "users_names"
const val NODE_USERS_PHONES = "users_phones"
const val NODE_CONTACTS = "contacts"
const val NODE_MESSAGES = "messages"


const val FROM = "from"
const val MESSAGE_TYPE = "type"
const val TEXT = "messageText"
const val MESSAGE_KEY = "messageKey"
const val SERVER_TIME_STAMP = "serverTimeStamp"


const val CHILD_USERNAME = "user_name"
const val CHILD_FIRST_NAME = "first_name"
const val CHILD_LAST_NAME = "last_name"
const val CHILD_BIO = "bio"
const val CHILD_STATUS = "status"
const val CHILD_USER_PHOTO_URL = "user_photo"
const val CHILD_USER_PHONE = "phone"
const val CHILD_USER_ID = "id"


const val FOLDER_PROFILE_IMAGE = "profile_image"

private var mutableVerificationId = MutableLiveData<String>()
var verificationId: LiveData<String> = mutableVerificationId

private var mutableCurrentUserId = MutableLiveData<String>()
var currentUserId: LiveData<String> = mutableCurrentUserId

private var mutableUserName = MutableLiveData<Boolean>()
var userName: LiveData<Boolean> = mutableUserName

private var mutableOldUserNameString = MutableLiveData<String>()
var oldUserNameString: LiveData<String> = mutableOldUserNameString

private var mutableUpdateData = MutableLiveData<String>()
var updateData: LiveData<String> = mutableUpdateData

private var mutableUserData = MutableLiveData<com.evgeny_m.postman.data.models.DataUserModel>()
var userData: LiveData<com.evgeny_m.postman.data.models.DataUserModel> = mutableUserData

private var mutablePhone = MutableLiveData<String>()
private var phone: LiveData<String> = mutablePhone

private var mutableContactsAdd = MutableLiveData<Boolean>()
var contactsAdd: LiveData<Boolean> = mutableContactsAdd

private var mutableDataUpdate = MutableLiveData<Boolean>()
var dataUpdate: LiveData<Boolean> = mutableDataUpdate

private var mutableContacts = MutableLiveData<List<DomainDataContact>>()
var contacts: LiveData<List<DomainDataContact>> = mutableContacts

private var mutableDataContacts = MutableLiveData<List<com.evgeny_m.postman.data.models.UserData>>()
var dataContacts: LiveData<List<com.evgeny_m.postman.data.models.UserData>> = mutableDataContacts

private var mutableMessages = MutableLiveData<List<com.evgeny_m.postman.data.models.MessageModel>>()
var messages: LiveData<List<com.evgeny_m.postman.data.models.MessageModel>> = mutableMessages

class FirebaseRepository(private val context: Context) :
    Firebase {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var storage = FirebaseStorage.getInstance().reference
    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private var dataListener: AppValueEventListener
    private var refUsers: DatabaseReference
    private var refMessages: DatabaseReference


    val userId = currentUserId.value.toString()

    init {
        mutableCurrentUserId.value = auth.currentUser?.uid.toString()

        dataListener = AppValueEventListener {
            mutableDataUpdate.value = true
            //mutableDataUpdate.value = false
        }
        refUsers = database.child(NODE_USERS)
        refMessages = database.child(NODE_MESSAGES).child(userId)
        refUsers.addValueEventListener(dataListener)
        refMessages.addValueEventListener(dataListener)
    }

    override fun registerUserByPhoneNumber(phoneNumber: String) {

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                mutableCurrentUserId.value = auth.currentUser?.uid.toString()
                mutablePhone.value = phoneNumber
            }

            override fun onVerificationFailed(exeptionMessage: FirebaseException) {

            }

            override fun onCodeSent(
                id: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                mutableVerificationId.value = id
            }
        }

        mutablePhone.value = phoneNumber
        val activity: Activity = context as Activity
        val option = PhoneAuthOptions.newBuilder()
            .setPhoneNumber(phoneNumber)
            .setTimeout(120L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(option)
    }

    override fun enterSmsCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId.value.toString(), code)
        auth.signInWithCredential(credential).addOnCompleteListener {
            mutableCurrentUserId.value = auth.currentUser?.uid.toString()

            database
                .child(NODE_USERS)
                .child(currentUserId.value.toString())
                .child(CHILD_USER_PHONE)
                .setValue(phone.value)
            database
                .child(NODE_USERS_PHONES)
                .child(phone.value.toString())
                .setValue(currentUserId.value)


        }.addOnFailureListener {

        }
    }

    override fun logOut() {
        auth.signOut()
        mutableCurrentUserId.value = "null"
    }

    override fun editFullName(firstName: String, lastName: String) {
        database
            .child(NODE_USERS)
            .child(userId)
            .child(CHILD_FIRST_NAME)
            .setValue(firstName)
            .addOnSuccessListener {
                mutableUpdateData.value = "true"
                mutableUpdateData.value = "null"

            }
            .addOnFailureListener {
                mutableUpdateData.value = "false"
                mutableUpdateData.value = "null"

            }

        if (lastName.isNotEmpty()) {
            database
                .child(NODE_USERS)
                .child(userId)
                .child(CHILD_LAST_NAME)
                .setValue(lastName)
        }
    }

    override fun editBio(string: String) {
        database
            .child(NODE_USERS)
            .child(userId)
            .child(CHILD_BIO)
            .setValue(string)
            .addOnSuccessListener {
                mutableUpdateData.value = "true"
                mutableUpdateData.value = "null"

            }
            .addOnFailureListener {
                mutableUpdateData.value = "false"
                mutableUpdateData.value = "null"
            }
    }

    override fun editUserName(string: String) {
        if (string.isNotEmpty()) {
            database
                .child(NODE_USERS)
                .child(userId)
                .child(CHILD_USERNAME)
                .setValue(string)

            database.child(NODE_USERS_NAMES).child(oldUserNameString.value.toString()).removeValue()

            database
                .child(NODE_USERS_NAMES)
                .child(string)
                .setValue(userId)
                .addOnSuccessListener {
                    mutableUpdateData.value = "true"
                    mutableUpdateData.value = "null"
                }
                .addOnFailureListener {
                    mutableUpdateData.value = "false"
                    mutableUpdateData.value = "null"
                }
        } else {
            mutableUpdateData.value = "false"
            mutableUpdateData.value = "null"
        }
    }

    override fun editPhone(string: String) {
        //TODO("Not yet implemented")
    }

    override fun editPhoto(string: String) {
        val uri : Uri = string.toUri()
        val path = storage.child(FOLDER_PROFILE_IMAGE)
            .child(currentUserId.value.toString())
        path.putFile(uri)
            .addOnSuccessListener {
            Log.d("editPhoto", it.toString())
                it.storage.downloadUrl.addOnSuccessListener { uri ->
                    Log.d("editPhoto", uri.toString())
                    database
                        .child(NODE_USERS)
                        .child(currentUserId.value.toString())
                        .child(CHILD_USER_PHOTO_URL)
                        .setValue(uri.toString())
                }
        }
            .addOnFailureListener {
                Log.d("editPhoto", it.toString())
            }
    }

    override fun checkUserName(string: String) {
        database
            .child(NODE_USERS_NAMES).addValueEventListener(AppValueEventListener { dataSnapshot ->
                if (dataSnapshot.hasChild(string)) {
                    mutableUserName.value = false
                    if (dataSnapshot.child(string).value == userId) {
                        mutableOldUserNameString.value = string
                        Log.d(
                            "AAAAAAAAAAAAAAAAAAAAAAA",
                            "$string ${currentUserId.value.toString()}"
                        )
                    }
                } else {
                    mutableUserName.value = true
                }
            })
    }

    override fun setOnline() {
        database
            .child(NODE_USERS)
            .child(currentUserId.value.toString())
            .child(CHILD_STATUS)
            .setValue("online")
    }

    override fun setOffline() {
        database
            .child(NODE_USERS)
            .child(currentUserId.value.toString())
            .child(CHILD_STATUS)
            .setValue("offline")
    }

    override fun foundUserDataById(userId: String) {
        if (userId != "null") { //?
            database.child(NODE_USERS).child(userId).get().addOnSuccessListener {
                val user =
                    com.evgeny_m.postman.data.models.DataUserModel(
                        firstName = it.child(CHILD_FIRST_NAME).value.toString(),
                        lastName = it.child(CHILD_LAST_NAME).value.toString(),
                        userName = it.child(CHILD_USERNAME).value.toString(),
                        bio = it.child(CHILD_BIO).value.toString(),
                        userPhotoUri = it.child(CHILD_USER_PHOTO_URL).value.toString(),
                        phone = it.child(CHILD_USER_PHONE).value.toString()
                    )
                mutableUserData.value = user
                Log.d("aa", "$user")
            }
        }
    }

    override fun checkContacts(array: ArrayList<DomainDataContact>) {
        val dateMap = mutableMapOf<String, Any>()
        val listUsers = mutableListOf<com.evgeny_m.postman.data.models.UserData>()
        //val listContacts = mutableListOf<DomainDataContact>()
        database
            .child(NODE_USERS_PHONES)
            .addListenerForSingleValueEvent(AppValueEventListener{ it ->
                it.children.forEach{ phoneNumber ->
                    array.forEach{ contact ->
                        if (phoneNumber.key == contact.phone) {
                            val userId = phoneNumber.value.toString()
                            dateMap[CHILD_USER_ID] = userId
                            dateMap[CHILD_FIRST_NAME] = contact.name
                            database
                                .child(NODE_CONTACTS)
                                .child(currentUserId.value.toString())
                                .child(userId)
                                .updateChildren(dateMap).addOnCompleteListener {
                                    database
                                        .child(NODE_CONTACTS)
                                        .child(currentUserId.value.toString())
                                        .child(userId)
                                        .get().addOnSuccessListener { snapshot ->
                                                val user = DomainDataContact(
                                                    name = snapshot
                                                        .child(CHILD_FIRST_NAME)
                                                        .value.toString(),
                                                    phone = snapshot
                                                        .child(CHILD_USER_ID)
                                                        .value.toString()
                                                )
                                                //listContacts.add(user)
                                                database.child(NODE_USERS)
                                                    .child(userId).get()
                                                    .addOnSuccessListener {
                                                    val user =
                                                        com.evgeny_m.postman.data.models.UserData(
                                                            id = userId,
                                                            name = user.name,
                                                            status = it
                                                                .child(CHILD_STATUS)
                                                                .value.toString(),
                                                            photo = it
                                                                .child(CHILD_USER_PHOTO_URL)
                                                                .value.toString(),
                                                            bio = it.child(CHILD_BIO).value.toString(),
                                                            phone = it.child(CHILD_USER_PHONE).value.toString(),
                                                            userName = it.child(CHILD_USERNAME).value.toString()
                                                        )
                                                    listUsers.add(user)
                                                    mutableDataContacts.value = listUsers
                                                    Log.d("UserFIREBASE",
                                                        listUsers.toString())
                                                }
                                        }
                                }
                        }
                    }
                }
            })
    }

    override fun sendMessage(message: String, toUserId: String) {

        val currentUserId = currentUserId.value.toString()

        val refDialogUser = "$NODE_MESSAGES/$currentUserId/$toUserId"
        val refDialogReceivingUser = "$NODE_MESSAGES/$toUserId/$currentUserId"
        val messageKey = database.child(refDialogUser).push().key

        val mapMessage = hashMapOf<String, Any>()
        mapMessage[FROM] = currentUserId
        //mapMessage[MESSAGE_TYPE] = type
        mapMessage[TEXT] = message
        mapMessage[MESSAGE_KEY] = messageKey.toString()
        mapMessage[SERVER_TIME_STAMP] = ServerValue.TIMESTAMP

        val mapNewDialog = hashMapOf<String, Any>()
        mapNewDialog["$refDialogUser/$messageKey"] = mapMessage
        mapNewDialog["$refDialogReceivingUser/$messageKey"] = mapMessage
        database.updateChildren(mapNewDialog)
    }

    override fun getMessagesList(userId: String) {
        val listMessages = mutableListOf<com.evgeny_m.postman.data.models.MessageModel>()
        database
            .child(NODE_MESSAGES)
            .child(currentUserId.value.toString())
            .child(userId).get().addOnSuccessListener { snapshot ->

                snapshot.children.forEach {
                    val message = com.evgeny_m.postman.data.models.MessageModel(
                        from = it.child(FROM).value.toString(),
                        messageText = it.child(TEXT).value.toString(),
                        messageKey = it.child(MESSAGE_KEY).value.toString()
                    )
                    listMessages.add(message)
                }
                mutableMessages.value = listMessages
        }

    }


}