package com.example.e_learningapp.ui.student.material;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningapp.models.ModelMembers;
import com.example.e_learningapp.utils.Const;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class CourseMaterialViewModel  extends ViewModel {



    private DatabaseReference ref ;
    private ArrayList<String> fileLinks = new ArrayList<>();
    MutableLiveData<List<String>> filesLiveData = new  MutableLiveData();

    MutableLiveData<String> errorLiveData = new  MutableLiveData();


    @Inject
    public CourseMaterialViewModel(FirebaseAuth auth , DatabaseReference reference) {
        ref = reference ;

    }

    public void getMaterial (String courseId){

        ref.child(Const.REF_FILES).child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue() != null) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        fileLinks.add(snapshot1.getValue(String.class));
                    }
                    filesLiveData.setValue(fileLinks);
                }else {
                    errorLiveData.setValue("No Material");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorLiveData.setValue(error.getMessage());
            }
        });



    }
}
