package com.example.e_learningapp.ui.instructor.grads;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningapp.BaseFragment;
import com.example.e_learningapp.MainActivity;
import com.example.e_learningapp.adapters.AdapterRecyclerGrads;
import com.example.e_learningapp.databinding.FragmentGradsBinding;
import com.example.e_learningapp.databinding.FragmentStuedntPageBinding;
import com.example.e_learningapp.models.ModelMembers;
import com.example.e_learningapp.ui.instructor.control.InstructorControlViewModel;
import com.example.e_learningapp.ui.instructor.home.InstructorHomeViewModel;
import com.example.e_learningapp.ui.student.control.StudentPageViewModel;
import com.example.e_learningapp.ui.student.grades.GradesFragment;
import com.example.e_learningapp.ui.student.grades.GradesFragmentArgs;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GradsFragment extends BaseFragment {

    private FragmentGradsBinding binding;

    private String courseId;

    private AdapterRecyclerGrads adapterRecyclerGrads = new AdapterRecyclerGrads();

    InstructorHomeViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGradsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(InstructorHomeViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseId = GradesFragmentArgs.fromBundle(requireArguments()).getCourseId();
        MainActivity.startLoading();
        viewModel.getGrades(courseId);

        observers();
    }

    private void observers (){
        viewModel.gradsLiveData.observe(requireActivity(), new Observer<ArrayList<ModelMembers>>() {
            @Override
            public void onChanged(ArrayList<ModelMembers> modelMembers) {
                MainActivity.stopLoading();
                adapterRecyclerGrads.setList(modelMembers);
                binding.recyclerGrads.setAdapter(adapterRecyclerGrads);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
