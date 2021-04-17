package com.example.e_learningapp.ui.student.material;

import android.content.Intent;
import android.net.Uri;
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
import com.example.e_learningapp.adapters.AdapterRecyclerCourses;
import com.example.e_learningapp.adapters.AdapterRecyclerMaterial;
import com.example.e_learningapp.databinding.FragmentCourseMaterialBinding;
import com.example.e_learningapp.databinding.FragmentStudentHomeBinding;
import com.example.e_learningapp.ui.student.home.StudentHomeViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CourseMaterialFragment  extends BaseFragment {


    CourseMaterialViewModel viewModel ;

    private String courseId ;
    private FragmentCourseMaterialBinding binding ;
    private AdapterRecyclerMaterial adapterRecyclerMaterial ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =FragmentCourseMaterialBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this ).get(CourseMaterialViewModel.class);
        adapterRecyclerMaterial = new AdapterRecyclerMaterial();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        courseId = CourseMaterialFragmentArgs.fromBundle(requireArguments()).getCourseId();
        MainActivity.startLoading();
        viewModel.getMaterial(courseId);
        observers();
        onClick();

    }

    private void onClick (){
        adapterRecyclerMaterial.setOnClick(new AdapterRecyclerMaterial.OnClick() {
            @Override
            public void onItemClick(String name) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(name));
                startActivity(browserIntent);
            }
        });
    }
    private void observers(){

        viewModel.filesLiveData.observe(myActivity, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                adapterRecyclerMaterial.setList(strings);
                binding.recyclerMaterial.setAdapter(adapterRecyclerMaterial);
                MainActivity.stopLoading();
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
}
