package com.example.e_learningapp.ui.chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_learningapp.data.MySharedPrefrance;
import com.example.e_learningapp.models.ModelChat;
import com.example.e_learningapp.utils.Const;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatViewModel  extends ViewModel {

    private FirebaseAuth auth ;
    private DatabaseReference ref ;
    private ArrayList<ModelChat> list = new ArrayList<>();
    MutableLiveData<ArrayList<ModelChat>> massagesLiveData = new MutableLiveData<>();


    @Inject
    public ChatViewModel(DatabaseReference reference , FirebaseAuth auth  ) {

        ref = reference ;
        this.auth = auth ;

    }

    public void sendMassage (String courseId , String  massage  , String userName){


       ref.child(Const.REF_CHATS).child(courseId).push()
               .setValue(new ModelChat(massage , MySharedPrefrance.getUserId(),userName));

    }

    public void getMassages (String courseId){

        ref.child(Const.REF_CHATS).child(courseId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    list.add(snapshot1.getValue(ModelChat.class));
                }
                massagesLiveData.setValue(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
