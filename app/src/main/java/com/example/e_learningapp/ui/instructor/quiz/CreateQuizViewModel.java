package com.example.e_learningapp.ui.instructor.quiz;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningapp.models.ModelQuiz;
import com.example.e_learningapp.utils.Const;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CreateQuizViewModel extends ViewModel {


    private DatabaseReference ref;
    MutableLiveData<String> createQuiz = new MutableLiveData<>();


    @Inject
    public CreateQuizViewModel(DatabaseReference reference) {
        ref = reference;
    }

    public void createQuiz(ArrayList<ModelQuiz> questions, String courseId) {

        ref.child(Const.REF_QUIZ).child(courseId).push().setValue(questions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                createQuiz.setValue("Uploaded");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                createQuiz.setValue(e.getLocalizedMessage());
            }
        });


    }


}
