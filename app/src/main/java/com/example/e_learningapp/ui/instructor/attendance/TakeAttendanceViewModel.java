package com.example.e_learningapp.ui.instructor.attendance;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningapp.models.ModelMembers;
import com.example.e_learningapp.utils.Const;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TakeAttendanceViewModel extends ViewModel{


    private DatabaseReference ref ;
     MutableLiveData<String> attendanceStatus = new MutableLiveData<>();


    @Inject
    public TakeAttendanceViewModel(DatabaseReference reference) {
        ref = reference ;

    }

    public void openAttendance (String courseId ,int code){

        ref.child(Const.REF_ATTENDANCE).child(courseId).child(code+"").setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        attendanceStatus.setValue(Const.SUCCESS);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                attendanceStatus.setValue(e.getLocalizedMessage());
            }
        }) ;



    }

}
