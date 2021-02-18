package com.example.e_learningapp.ui.auth.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningapp.data.MySharedPrefrance;
import com.example.e_learningapp.models.ModelAuth;
import com.example.e_learningapp.utils.Const;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    MutableLiveData<String>  loginLiveData = new MutableLiveData<>();
    private FirebaseAuth auth ;
    private DatabaseReference ref ;
    private ModelAuth model ;

    @Inject
    public LoginViewModel(FirebaseAuth auth , DatabaseReference reference) {
        ref = reference ;
        this.auth  = auth ;
    }


    public void login (String email , String password){

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                getUserInfo(authResult.getUser().getUid());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loginLiveData.setValue(e.getLocalizedMessage());
            }
        });

    }

    private void getUserInfo(String userId){


        ref.child(Const.REF_USERS).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model = snapshot.getValue(ModelAuth.class);

                MySharedPrefrance.setUserEmail(model.getEmail());
                MySharedPrefrance.setUserId(model.getUserId());
                MySharedPrefrance.setUserName(model.getUserName());
                MySharedPrefrance.setUserType(model.getUserType());
                loginLiveData.setValue("success");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loginLiveData.setValue(error.getMessage());
            }
        });
    }


}