package com.example.e_learningapp.ui.student.control;

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
import com.example.e_learningapp.R;
import com.example.e_learningapp.adapters.AdapterRecyclerCourses;
import com.example.e_learningapp.databinding.FragmentStudentHomeBinding;
import com.example.e_learningapp.databinding.FragmentStuedntPageBinding;
import com.example.e_learningapp.ui.student.home.StudentHomeViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StudentPageFragment extends BaseFragment {


    private FragmentStuedntPageBinding  binding ;
    private String courseId  ;
    private String courseName ;

    StudentPageViewModel viewModel ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =FragmentStuedntPageBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this ).get(StudentPageViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        courseId = StudentPageFragmentArgs.fromBundle(requireArguments()).getCouseId();
        courseName = StudentPageFragmentArgs.fromBundle(requireArguments()).getName();
        onClicks();
        observers();
    }
    private void onClicks (){

        binding.btnAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = binding.editAttend.getText().toString().trim();
                if (code.equals("")){
                    binding.editAttend.setError(getString(R.string.required));
                }else {
                    MainActivity.startLoading();
                    viewModel.checkCode(courseId , code);
                }
            }
        });

        binding.btnMatrial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(StudentPageFragmentDirections.actionStudentPageFragmentToCourseMaterialFragment(courseId));
            }
        });

        binding.btnSolveQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(StudentPageFragmentDirections.actionStudentPageFragmentToAllQuizFragment(courseId));
            }
        });
        binding.chatInStudentpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(StudentPageFragmentDirections.actionStudentPageFragmentToChatFragment(courseId));
            }
        });


    }

    private void observers(){
        viewModel.attendLiveData.observe(myActivity, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                MainActivity.stopLoading();
                Toast.makeText(myContext, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
