package com.example.e_learningapp.ui.instructor.home;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningapp.data.MySharedPrefrance;
import com.example.e_learningapp.models.ModelCourse;
import com.example.e_learningapp.utils.Const;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class InstructorHomeViewModel extends ViewModel {


    MutableLiveData<ArrayList<ModelCourse>> coursesLiveData = new MutableLiveData<>();
    MutableLiveData<String> errorLiveData = new MutableLiveData<>() ;
    private ArrayList<ModelCourse> list = new ArrayList<>();
    private DatabaseReference ref ;

    @Inject
    public InstructorHomeViewModel(DatabaseReference reference) {
        ref = reference ;
    }

    public void getCourses(){

        ref.child(Const.REF_COURSES).orderByChild(Const.REF_INSTRUCTOR_ID).equalTo(MySharedPrefrance.getUserId())
                          .addListenerForSingleValueEvent(new ValueEventListener() {
                              @Override
                              public void onDataChange(@NonNull DataSnapshot snapshot) {
                                  list.clear();
                                  for (DataSnapshot snap: snapshot.getChildren()) {
                                      list.add(snap.getValue(ModelCourse.class));
                                  }

                                  coursesLiveData.setValue(list);

                              }

                              @Override
                              public void onCancelled(@NonNull DatabaseError error) {
                                 errorLiveData.setValue(error.getMessage());
                              }
                          });


    }

}
