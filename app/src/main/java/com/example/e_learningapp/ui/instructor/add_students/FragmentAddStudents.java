package com.example.e_learningapp.ui.instructor.add_students;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_learningapp.BaseFragment;
import com.example.e_learningapp.MainActivity;
import com.example.e_learningapp.adapters.AdapterAddStudent;
import com.example.e_learningapp.databinding.FragmentAddStudentsBinding;
import com.example.e_learningapp.models.ModelMembers;
import com.example.e_learningapp.utils.Const;
import com.example.e_learningapp.utils.Helper;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentAddStudents extends BaseFragment {

    private FragmentAddStudentsBinding binding ;
    AddStudentsViewModel viewModel ;
    private String courseId ;
    private String courseName ;
    private AdapterAddStudent adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddStudentsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(AddStudentsViewModel.class);
        adapter = new AdapterAddStudent();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseId = FragmentAddStudentsArgs.fromBundle(getArguments()).getCourseId();
        courseName =FragmentAddStudentsArgs.fromBundle(getArguments()).getCourseName();
        onClicks();
        viewModel.getMembers(courseId);
        viewModel.membersLiveData.observe(myActivity, new Observer<ArrayList<ModelMembers>>() {
            @Override
            public void onChanged(ArrayList<ModelMembers> modelMembers) {

                MainActivity.stopLoading();

                    adapter.setList(modelMembers);
                    if (binding!= null)
                    binding.recyclerStudents.setAdapter(adapter);

            }
        });
        viewModel.status.observe(myActivity, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                MainActivity.stopLoading();
                if (s.equals(Const.SUCCESS)){
                    binding.editEmail.setText("");
                    Toast.makeText(myContext, Const.SUCCESS, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(myContext, s , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onClicks(){

        binding.btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        adapter.setOnDelete(new AdapterAddStudent.OnDelete() {
            @Override
            public void onDeleteClick(String studentEmail, String courseId) {
                viewModel.deleteStudent(courseId, studentEmail);
            }
        });

    }
    private void validation(){
      String email  =   binding.editEmail.getText().toString().trim();
      if (email.isEmpty()){
          binding.editEmail.setError("Required");
      }else {


          MainActivity.startLoading();
          viewModel.addStudent(Helper.removeDotForFireBase(email), courseId , courseName);

      }

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null ;
    }
}