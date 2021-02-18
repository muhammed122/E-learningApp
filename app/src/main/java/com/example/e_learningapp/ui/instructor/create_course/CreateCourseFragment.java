package com.example.e_learningapp.ui.instructor.create_course;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningapp.BaseFragment;
import com.example.e_learningapp.MainActivity;
import com.example.e_learningapp.data.MySharedPrefrance;
import com.example.e_learningapp.databinding.ChooseBetweenDocStudentBinding;
import com.example.e_learningapp.databinding.FragmentCreateCourseBinding;
import com.example.e_learningapp.models.ModelCourse;
import com.example.e_learningapp.utils.Const;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateCourseFragment extends BaseFragment {

     CreateCourseViewModel viewModel ;
    private FragmentCreateCourseBinding binding ;
    private String courseName;
    private String quizGrade ;
    private String projectGrade ;
    private String attendGrade ;
    private ModelCourse modelCourse ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(CreateCourseViewModel.class);
        modelCourse = new ModelCourse();
        binding = FragmentCreateCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.createLiveData.observe(myActivity, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                MainActivity.stopLoading();
                if (s.equals(Const.SUCCESS)){
                    Toast.makeText(myContext, Const.SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }
        });
          onClicks();
    }

    private void onClicks(){

        binding.btnCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });



    }

    private void validation(){
        courseName = binding.editCourseName.getText().toString().trim() ;
        quizGrade  = binding.editQuizGrade.getText().toString().trim();
        attendGrade  = binding.editAttendGrade.getText().toString().trim();
        projectGrade  = binding.editProjectGrade.getText().toString().trim();

        if (courseName.isEmpty()){
            binding.editCourseName.setError("Required");
        }else if (quizGrade.isEmpty()){
            binding.editQuizGrade.setError("Required");
        }else if (attendGrade.isEmpty()){
            binding.editAttendGrade.setError("Required");
        }else if (projectGrade.isEmpty()){
            binding.editProjectGrade.setError("Required");
        }else {

            MainActivity.startLoading();
            modelCourse.setCourseName(courseName);
            modelCourse.setAssignmentGrade(Double.parseDouble(quizGrade));
            modelCourse.setAttendanceGrade(Double.parseDouble(attendGrade));
            modelCourse.setProjectsGrade(Double.parseDouble(projectGrade));
            modelCourse.setInstructorId(MySharedPrefrance.getUserId());
            viewModel.createCourse(modelCourse);

        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null ;
    }
}