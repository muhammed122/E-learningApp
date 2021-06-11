package com.example.e_learningapp.ui.instructor.home;

import android.content.Intent;
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
import com.example.e_learningapp.data.MySharedPrefrance;
import com.example.e_learningapp.databinding.FragmentHomeDoctorAllCourseBinding;
import com.example.e_learningapp.models.ModelCourse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class InstructorHomeFragment extends BaseFragment {

    InstructorHomeViewModel viewModel ;
    private FragmentHomeDoctorAllCourseBinding binding ;
    private AdapterRecyclerCourses adapter ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this ).get(InstructorHomeViewModel.class);
        binding =FragmentHomeDoctorAllCourseBinding.inflate(inflater, container, false);
        adapter = new AdapterRecyclerCourses();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onClicks();
        MainActivity.startLoading();
        viewModel.getCourses();
        viewModel.coursesLiveData.observe(myActivity, new Observer<ArrayList<ModelCourse>>() {
            @Override
            public void onChanged(ArrayList<ModelCourse> modelCourses) {

                if (modelCourses.size()!= 0 ) {
                    MainActivity.stopLoading();
                    adapter.setList(modelCourses);
                    binding.recyclerInstructorCourse.setAdapter(adapter);
                }else {
                    MainActivity.stopLoading();
                    Toast.makeText(myContext, "No Courses", Toast.LENGTH_SHORT).show();
                }

            }
        });

        viewModel.errorLiveData.observe(myActivity, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(myContext, s, Toast.LENGTH_SHORT).show();
                MainActivity.stopLoading();
            }
        });

    }


    private void onClicks(){

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MySharedPrefrance.setUserEmail("");
                MySharedPrefrance.setUserId("");
                MySharedPrefrance.setUserName("");
                MySharedPrefrance.setUserType("");
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(myActivity , MainActivity.class);
                startActivity(intent);
                myActivity.finishAffinity();
            }
        });
        binding.btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              navigate(InstructorHomeFragmentDirections.actionInstructorHomeFragmentToCreateCourse());
            }
        });

        adapter.setOnClick(new AdapterRecyclerCourses.OnClick() {
            @Override
            public void onItemClick(String id ,String name) {
                navigate(InstructorHomeFragmentDirections.actionInstructorHomeFragmentToFragmentInstructorControl(id, name));
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null ;
    }
}