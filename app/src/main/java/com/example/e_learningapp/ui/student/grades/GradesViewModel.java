package com.example.e_learningapp.ui.student.grades;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningapp.models.ModelChat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import javax.inject.Inject;

public class GradesViewModel extends ViewModel {


    private FirebaseAuth auth ;
    private DatabaseReference ref ;

    MutableLiveData<ArrayList<ModelChat>> massagesLiveData = new MutableLiveData<>();


    @Inject
    public GradesViewModel(DatabaseReference reference , FirebaseAuth auth  ) {
        ref = reference ;
        this.auth = auth ;

    }


}
