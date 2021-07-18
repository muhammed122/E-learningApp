package com.example.e_learningapp.ui.student.grades;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningapp.data.MySharedPrefrance;
import com.example.e_learningapp.models.ModelChat;
import com.example.e_learningapp.models.ModelGrads;
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
public class GradesViewModel extends ViewModel {


    private FirebaseAuth auth ;
    private DatabaseReference ref ;

    MutableLiveData<ModelGrads> gradsMutableLiveData = new MutableLiveData<>();

    MutableLiveData<ArrayList<ModelChat>> massagesLiveData = new MutableLiveData<>();


    @Inject
    public GradesViewModel(DatabaseReference reference , FirebaseAuth auth  ) {
        ref = reference ;
        this.auth = auth ;

    }



    public void getDegrees (String courseId){

        ref.child(Const.REF_COURSES)
                .child(courseId)
                .child(Const.REF_COURSE_MEMBERS)
                .child(Helper.removeDotForFireBase(MySharedPrefrance.getUserEmail()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ModelGrads modelGrads = snapshot.getValue(ModelGrads.class);
                        gradsMutableLiveData.setValue(modelGrads);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


}
