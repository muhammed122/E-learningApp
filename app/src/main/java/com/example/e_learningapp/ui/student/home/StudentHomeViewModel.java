package com.example.e_learningapp.ui.student.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningapp.data.MySharedPrefrance;
import com.example.e_learningapp.models.ModelCourse;
import com.example.e_learningapp.utils.Const;
import com.example.e_learningapp.utils.Helper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class StudentHomeViewModel extends ViewModel {

    MutableLiveData<ArrayList<ModelCourse>> coursesLiveData = new MutableLiveData<>();
    MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private ArrayList<ModelCourse> list;
    private DatabaseReference ref;

    FirebaseAuth auth ;
    @Inject
    public StudentHomeViewModel(DatabaseReference reference, FirebaseAuth auth) {
        ref = reference;
        list = new ArrayList<>();
        this.auth = auth ;
    }

    public void getCourses() {


        ref.child(Const.REF_COURSE_MEMBERS).child(Helper.removeDotForFireBase(MySharedPrefrance.getUserEmail()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            list.add(snap.getValue(ModelCourse.class));
                        }
                        coursesLiveData.setValue(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        errorLiveData.setValue(error.getMessage());
                        Log.e("TAG1", "onDataChange: " + error.getMessage());
                    }
                });
    }



}
