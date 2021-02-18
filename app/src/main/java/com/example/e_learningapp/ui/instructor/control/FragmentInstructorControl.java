package com.example.e_learningapp.ui.instructor.control;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_learningapp.BaseFragment;
import com.example.e_learningapp.R;
import com.example.e_learningapp.databinding.ChooseBetweenDocStudentBinding;
import com.example.e_learningapp.databinding.CourseControlInstructorFragmentBinding;


public class FragmentInstructorControl extends BaseFragment {


    private CourseControlInstructorFragmentBinding binding ;

    private String courseId ;
    private String courseName ;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = CourseControlInstructorFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       courseId =  FragmentInstructorControlArgs.fromBundle(getArguments()).getCourseId();
       courseName = FragmentInstructorControlArgs.fromBundle(getArguments()).getCourseName();


        onClicks();
    }

    private void onClicks(){
        binding.btnAddMember.setOnClickListener((View.OnClickListener) view ->
                navigate(FragmentInstructorControlDirections.actionFragmentInstructorControlToFragmentAddStudents(courseId , courseName)));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=  null ;
    }
}