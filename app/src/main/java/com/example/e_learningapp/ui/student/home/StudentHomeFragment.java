package com.example.e_learningapp.ui.student.home;

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
import com.example.e_learningapp.adapters.AdapterRecyclerCourses;
import com.example.e_learningapp.databinding.FragmentStudentHomeBinding;
import com.example.e_learningapp.models.ModelCourse;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StudentHomeFragment extends BaseFragment {


    StudentHomeViewModel viewModel ;
    private AdapterRecyclerCourses  adapter ;
    private FragmentStudentHomeBinding binding ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =FragmentStudentHomeBinding.inflate(inflater, container, false);
        viewModel = new  ViewModelProvider(this ).get(StudentHomeViewModel.class);
        adapter = new AdapterRecyclerCourses();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getCourses();
        MainActivity.startLoading();
        viewModel.coursesLiveData.observe(myActivity, new Observer<ArrayList<ModelCourse>>() {
            @Override
            public void onChanged(ArrayList<ModelCourse> modelCourses) {
                MainActivity.stopLoading();
                if (modelCourses.size() == 0 ){
                    Toast.makeText(myContext, "No Courses", Toast.LENGTH_SHORT).show();
                    return;
                }
                adapter.setList(modelCourses);
                binding.recyclerViewHomeStudent.setAdapter(adapter);
            }
        });
        viewModel.errorLiveData.observe(myActivity, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                MainActivity.stopLoading();
                Toast.makeText(myContext, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null ;
    }
}