package com.example.e_learningapp.ui.instructor.add_students;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningapp.models.ModelAuth;
import com.example.e_learningapp.models.ModelMembers;
import com.example.e_learningapp.utils.Const;
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
public class AddStudentsViewModel extends ViewModel {

    MutableLiveData<ArrayList<ModelMembers>> membersLiveData = new MutableLiveData<>();
    MutableLiveData<String> status = new MutableLiveData<>();
    private FirebaseAuth auth ;
    private DatabaseReference ref ;
    private ModelMembers model ;
    private ArrayList<ModelMembers> list ;


    @Inject
    public AddStudentsViewModel(FirebaseAuth auth , DatabaseReference reference) {
        ref = reference ;
        this.auth  = auth ;
        model  = new ModelMembers();
        list = new ArrayList<>();
    }

    public void addStudent(String studentEmail  , String courseId , String courseName){

        model.setCourseId(courseId);
        model.setStudentEmail(studentEmail);
        model.setCourseName(courseName);

        ref.child(Const.REF_COURSE_MEMBERS).child(studentEmail).child(courseId).setValue(model);

        ref.child(Const.REF_COURSES).child(courseId).child(Const.REF_COURSE_MEMBERS)
                .child(studentEmail).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                status.setValue(Const.SUCCESS);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                status.setValue(e.getLocalizedMessage());
            }
        });



    }

    public void getMembers(String courseId){
        ref.child(Const.REF_COURSES).child(courseId).child(Const.REF_COURSE_MEMBERS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot snap: snapshot.getChildren()) {
                            list.add(snap.getValue(ModelMembers.class));
                        }
                        membersLiveData.setValue(list);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        status.setValue(error.getMessage());
                    }
                });


    }

    public void deleteStudent (String courseId , String studentEmail){


        ref.child(Const.REF_COURSES).child(courseId).child(Const.REF_COURSE_MEMBERS).child(studentEmail).setValue(null);
        ref.child(Const.REF_COURSE_MEMBERS).child(studentEmail).child(courseId).setValue(null);


    }




}
