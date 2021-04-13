package com.example.e_learningapp.ui.instructor.upload_material;

import android.app.ProgressDialog;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningapp.R;
import com.example.e_learningapp.utils.Const;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UploadMaterialViewModel extends ViewModel {


    private DatabaseReference ref;
    private StorageReference storage, sRef;

    MutableLiveData<String> uploadFile = new MutableLiveData<>();


    @Inject
    public UploadMaterialViewModel(DatabaseReference reference, StorageReference storage) {
        ref = reference;
        this.storage = storage;

    }

    public void uploadFile(Uri filePath, String courseId) {

        if (filePath != null) {


            sRef = storage.child("file/" + courseId +"/" +System.currentTimeMillis());

            sRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    final StorageReference filePath = sRef;

                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String finalpath = uri.toString();


                            ref.child(Const.REF_FILES).child(courseId).push().setValue(finalpath);
                            uploadFile.setValue("Uploaded");
                        }
                    });
                }
            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            uploadFile.setValue(e.getLocalizedMessage());
                        }
                    });


        }
    }


}
