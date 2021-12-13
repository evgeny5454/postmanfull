// Generated by view binder compiler. Do not edit!
package com.evgeny_m.postman.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.evgeny_m.postman.R;
import com.google.android.material.card.MaterialCardView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentNavigationBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialCardView navDrawerPhotoFiend;

  @NonNull
  public final TextView navUserName;

  @NonNull
  public final TextView navUserPhone;

  @NonNull
  public final ImageView userPhotoFelid;

  private FragmentNavigationBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialCardView navDrawerPhotoFiend, @NonNull TextView navUserName,
      @NonNull TextView navUserPhone, @NonNull ImageView userPhotoFelid) {
    this.rootView = rootView;
    this.navDrawerPhotoFiend = navDrawerPhotoFiend;
    this.navUserName = navUserName;
    this.navUserPhone = navUserPhone;
    this.userPhotoFelid = userPhotoFelid;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentNavigationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentNavigationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_navigation, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentNavigationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.navDrawer_photo_fiend;
      MaterialCardView navDrawerPhotoFiend = rootView.findViewById(id);
      if (navDrawerPhotoFiend == null) {
        break missingId;
      }

      id = R.id.navUserName;
      TextView navUserName = rootView.findViewById(id);
      if (navUserName == null) {
        break missingId;
      }

      id = R.id.navUserPhone;
      TextView navUserPhone = rootView.findViewById(id);
      if (navUserPhone == null) {
        break missingId;
      }

      id = R.id.userPhotoFelid;
      ImageView userPhotoFelid = rootView.findViewById(id);
      if (userPhotoFelid == null) {
        break missingId;
      }

      return new FragmentNavigationBinding((ConstraintLayout) rootView, navDrawerPhotoFiend,
          navUserName, navUserPhone, userPhotoFelid);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}