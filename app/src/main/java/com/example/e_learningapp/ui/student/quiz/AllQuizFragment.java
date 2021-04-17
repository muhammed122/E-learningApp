package com.example.e_learningapp.ui.student.quiz;

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
import com.example.e_learningapp.adapters.AdapterRecyclerQuizes;
import com.example.e_learningapp.databinding.FragmentAllQuizlBinding;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AllQuizFragment extends BaseFragment {

    CourseQuizViewModel viewModel ;

    private String courseId , quizId;
    private FragmentAllQuizlBinding binding ;
    private AdapterRecyclerQuizes adapterRecyclerQuizes ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAllQuizlBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this ).get(CourseQuizViewModel.class);
        adapterRecyclerQuizes = new AdapterRecyclerQuizes();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        courseId = AllQuizFragmentArgs.fromBundle(requireArguments()).getCourseId();

        MainActivity.startLoading();
        viewModel.getAllQuiz(courseId);
        observers();
        onClicks();
    }

    private void onClicks (){

        adapterRecyclerQuizes.setOnClick(new AdapterRecyclerQuizes.OnClick() {
            @Override
            public void onItemClick(String name) {

                MainActivity.startLoading();
                quizId = name ;
                viewModel.checkIfUserAnswered(courseId,name );


            }
        });
    }


    private void observers (){

        viewModel.allQuizLiveData.observe(myActivity, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                adapterRecyclerQuizes.setList(strings);
                binding.recyclerMaterial.setAdapter(adapterRecyclerQuizes);
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

        viewModel.checkUserAnswered.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                MainActivity.stopLoading();
                if (aBoolean){
                    Toast.makeText(myContext, "you are already answered", Toast.LENGTH_SHORT).show();
                }else {
                    navigate(AllQuizFragmentDirections.actionAllQuizFragmentToCourseQuizFragment(courseId,quizId));

                }
            }
        });



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null ;
    }
}
