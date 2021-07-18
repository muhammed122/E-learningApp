package com.example.e_learningapp.ui.student.home;

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
import com.example.e_learningapp.R;
import com.example.e_learningapp.adapters.AdapterRecyclerCourses;
import com.example.e_learningapp.data.MySharedPrefrance;
import com.example.e_learningapp.databinding.FragmentStudentHomeBinding;
import com.example.e_learningapp.models.ModelCourse;
import com.example.e_learningapp.ui.instructor.add_students.AddStudentsViewModel;
import com.example.e_learningapp.utils.Helper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StudentHomeFragment extends BaseFragment {


    StudentHomeViewModel viewModel ;
    private AdapterRecyclerCourses  adapter ;
    private FragmentStudentHomeBinding binding ;


    AddStudentsViewModel viewModel2 ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =FragmentStudentHomeBinding.inflate(inflater, container, false);
        viewModel = new  ViewModelProvider(this ).get(StudentHomeViewModel.class);
        adapter = new AdapterRecyclerCourses();
        viewModel2 = new ViewModelProvider(this).get(AddStudentsViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getCourses();
        MainActivity.startLoading();
        observers();
        onClicks();

    }

    private void onClicks (){

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

        binding.btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String courseId = binding.editEmail.getText().toString().trim() ;
                if (courseId.equals("") ){
                    binding.editEmail.setError(getString(R.string.required));

                }else {
                    MainActivity.startLoading();
                    viewModel.checkJoinToCourse( courseId);
                }
            }
        });
        adapter.setOnClick(new AdapterRecyclerCourses.OnClick() {
            @Override
            public void onItemClick(String id, String name) {
                navigate(StudentHomeFragmentDirections.actionStudentHomeToStudentPageFragment(name, id));

            }
        });


    }

    private void observers (){

        viewModel.checkJoinToCourseLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if ("".equals(s))
                    return;
                viewModel2.addStudent
                        (Helper.removeDotForFireBase(MySharedPrefrance.getUserEmail())
                                , binding.editEmail.getText().toString() , s);
                binding.editEmail.setText("");
                binding.editEmail.clearFocus();
                MainActivity.stopLoading();
                viewModel.checkJoinToCourseLiveData.setValue("");

            }
        });

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