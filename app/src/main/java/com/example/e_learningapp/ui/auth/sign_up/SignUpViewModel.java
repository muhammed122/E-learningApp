package com.example.e_learningapp.ui.auth.sign_up;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningapp.data.MySharedPrefrance;
import com.example.e_learningapp.models.ModelAuth;
import com.example.e_learningapp.utils.Const;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SignUpViewModel extends ViewModel {

    MutableLiveData<String> authLiveData  = new MutableLiveData<>();

    private FirebaseAuth auth ;
    private DatabaseReference ref ;
    private ModelAuth model ;

    @Inject
    public SignUpViewModel(FirebaseAuth auth , DatabaseReference reference) {
        ref = reference ;
        this.auth  = auth ;
    }

    public void signUp(ModelAuth model , String password){

        this.model = model ;
        auth.createUserWithEmailAndPassword(model.getEmail() , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                model.setUserId(authResult.getUser().getUid());
              uploadToFirebase(model );

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                authLiveData.setValue(e.getLocalizedMessage());

            }
        });

    }

    public void uploadToFirebase(ModelAuth model ){

        ref.child(Const.REF_USERS).child(auth.getUid()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                MySharedPrefrance.setUserEmail(model.getEmail());
                MySharedPrefrance.setUserId(model.getUserId());
                MySharedPrefrance.setUserName(model.getUserName());
                MySharedPrefrance.setUserType(model.getUserType());
                authLiveData.setValue("success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                authLiveData.setValue(e.getLocalizedMessage());
            }
        });

    }


}