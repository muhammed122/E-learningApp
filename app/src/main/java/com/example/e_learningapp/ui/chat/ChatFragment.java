package com.example.e_learningapp.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningapp.BaseFragment;
import com.example.e_learningapp.R;
import com.example.e_learningapp.adapters.AdapterRecyclerChat;
import com.example.e_learningapp.databinding.FragmentChatBinding;
import com.example.e_learningapp.databinding.FragmentStuedntPageBinding;
import com.example.e_learningapp.models.ModelChat;
import com.example.e_learningapp.ui.student.control.StudentPageViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatFragment extends BaseFragment  {


    private FragmentChatBinding binding ;
    private String courseId  ;


    private AdapterRecyclerChat adapterRecyclerChat ;
    ChatViewModel viewModel ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =FragmentChatBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this ).get(ChatViewModel.class);

        adapterRecyclerChat = new AdapterRecyclerChat ();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseId = ChatFragmentArgs.fromBundle(requireArguments()).getCourseId();
        viewModel.getMassages(courseId);
        onClicks();
        observers();
    }

    private void onClicks (){

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String massage = binding.editEmail.getText().toString().trim();
                if (!massage.equals("")){
                    binding.editEmail.setText("");
                    viewModel.sendMassage(courseId, massage);
                }else {
                    binding.editEmail.setError(getString(R.string.required));
                }

            }
        });

    }
    private void observers (){
        viewModel.massagesLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ModelChat>>() {
            @Override
            public void onChanged(ArrayList<ModelChat> modelChats) {
                adapterRecyclerChat.setList(modelChats);
                binding.recyclerChat.setAdapter(adapterRecyclerChat);
            }
        });
    }
}
