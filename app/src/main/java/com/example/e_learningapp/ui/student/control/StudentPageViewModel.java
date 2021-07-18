package com.example.e_learningapp.ui.student.control;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningapp.data.MySharedPrefrance;
import com.example.e_learningapp.models.ModelCourse;
import com.example.e_learningapp.utils.Const;
import com.example.e_learningapp.utils.Helper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class StudentPageViewModel extends ViewModel {

    MutableLiveData<String> attendLiveData = new MutableLiveData<>();
    MutableLiveData<ModelCourse> courseDetails = new MutableLiveData<>();

    private FirebaseAuth auth;
    private DatabaseReference ref;

    @Inject
    public StudentPageViewModel(DatabaseReference reference, FirebaseAuth auth) {
        ref = reference;
        this.auth = auth;

    }

    public void getCourseData(String courseId) {

        ref.child(Const.REF_COURSES).child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ModelCourse modelCourse = snapshot.getValue(ModelCourse.class);
                courseDetails.setValue(modelCourse);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void checkCode(String courseId, String attendCode) {

        ref.child(Const.REF_ATTENDANCE).child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.hasChild(attendCode)) {

                    if (snapshot.child(attendCode).hasChild(MySharedPrefrance.getUserId())) {
                        attendLiveData.setValue("You are already attended");
                    } else {
                        registerAttendance(courseId, attendCode);
                    }
                } else {
                    attendLiveData.setValue("Wrong Code");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void registerAttendance(String courseId, String attendCode) {

        ref.child(Const.REF_ATTENDANCE).child(courseId).child(attendCode).child(auth.getUid())
                .setValue(auth.getUid())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        attendLiveData.setValue(e.getLocalizedMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                addDegreeForAttendance(courseId);
                attendLiveData.setValue("Success");
            }
        });

    }

    private void addDegreeForAttendance(String courseId) {
        ref.child(Const.REF_COURSES)
                .child(courseId)
                .child(Const.REF_COURSE_MEMBERS)
                .child(Helper.removeDotForFireBase(MySharedPrefrance.getUserEmail()))
                .child("attendanceGrade").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                double degree = snapshot.getValue(Double.class);

                ref.child(Const.REF_COURSES)
                        .child(courseId)
                        .child(Const.REF_COURSE_MEMBERS)
                        .child(Helper.removeDotForFireBase(MySharedPrefrance.getUserEmail()))
                        .child("attendanceGrade").setValue(degree + 1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
