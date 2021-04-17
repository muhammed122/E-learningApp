package com.example.e_learningapp.ui.student.quiz;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningapp.data.MySharedPrefrance;
import com.example.e_learningapp.models.ModelAuth;
import com.example.e_learningapp.models.ModelQuiz;
import com.example.e_learningapp.models.ModelUserAnswer;
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
public class CourseQuizViewModel extends ViewModel {



    private DatabaseReference ref ;

    MutableLiveData<List<String>> allQuizLiveData = new  MutableLiveData();

    MutableLiveData<List<ModelQuiz>> quizLiveData = new  MutableLiveData();

    MutableLiveData<String> errorLiveData = new  MutableLiveData();

    MutableLiveData<Boolean> checkUserAnswered = new MutableLiveData();


    FirebaseAuth auth ;
    private ArrayList<String> allQuizList = new ArrayList<>();

    private ArrayList<ModelQuiz> quizList = new ArrayList<>();


    @Inject
    public CourseQuizViewModel( DatabaseReference reference  ,  FirebaseAuth auth) {
        ref = reference ;
        this.auth = auth  ;

    }

    public void getAllQuiz (String courseId) {
        ref.child(Const.REF_QUIZ).child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                allQuizList.clear();
                if (snapshot.getValue() != null) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        allQuizList.add(snapshot1.getKey());
                    }
                    allQuizLiveData.setValue(allQuizList);
                } else {
                    errorLiveData.setValue("No Quiz");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorLiveData.setValue(error.getMessage());
            }
        });

    }
    public void uploadResult (String courseId , String quizId, int grade){
        ref.child(Const.REF_QUIZ_ANSWER).child(courseId).child(quizId)
                .child(auth.getUid()).setValue(new ModelUserAnswer(MySharedPrefrance.getUserName()
                                 , MySharedPrefrance.getUserEmail(),MySharedPrefrance.getUserId(), grade));

    }
    public void checkIfUserAnswered(String courseId  , String quizId ){
        ref.child(Const.REF_QUIZ_ANSWER).child(courseId).child(quizId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                checkUserAnswered.setValue(snapshot.hasChild(auth.getUid()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void getQuiz (String courseId , String quizId){
        ref.child(Const.REF_QUIZ).child(courseId).child(quizId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                quizList.clear();
                if (snapshot.getValue() != null) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        quizList.add(snapshot1.getValue(ModelQuiz.class));

                    }
                    quizLiveData.setValue(quizList);
                }else {
                    errorLiveData.setValue("No Quiz");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorLiveData.setValue(error.getMessage());
            }
        });
    }
}
