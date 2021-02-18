package com.example.e_learningapp.ui.instructor.create_course;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningapp.models.ModelAuth;
import com.example.e_learningapp.models.ModelCourse;
import com.example.e_learningapp.utils.Const;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CreateCourseViewModel extends ViewModel {


    MutableLiveData<String> createLiveData = new MutableLiveData<>() ;
    private DatabaseReference ref ;
    ModelCourse modelCourse ;

    @Inject
    public CreateCourseViewModel(DatabaseReference reference) {
        ref = reference ;
    }

    public void createCourse(ModelCourse model){
        modelCourse  = model ;
        String id = ref.push().getKey();
        modelCourse.setCourseId(id);

        ref.child(Const.REF_COURSES).child(id).setValue(modelCourse).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                createLiveData.setValue(Const.SUCCESS);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              createLiveData.setValue(e.getLocalizedMessage());
            }
        });



    }
}
