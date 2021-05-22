package com.example.e_learningapp.ui.student.grades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningapp.BaseFragment;
import com.example.e_learningapp.databinding.FragmentGradesViewStudentBinding;
import com.example.e_learningapp.databinding.FragmentStuedntPageBinding;
import com.example.e_learningapp.ui.student.control.StudentPageFragment;
import com.example.e_learningapp.ui.student.control.StudentPageFragmentArgs;
import com.example.e_learningapp.ui.student.control.StudentPageViewModel;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class GradesFragment extends BaseFragment {

    private FragmentGradesViewStudentBinding binding;
    private String courseId;
    private String courseName;

    StudentPageViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGradesViewStudentBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(StudentPageViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void  initView (){

        binding.textView9.setText(StudentPageFragment.quizGrade);
        binding.textView14.setText(StudentPageFragment.attendanceGrade);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
