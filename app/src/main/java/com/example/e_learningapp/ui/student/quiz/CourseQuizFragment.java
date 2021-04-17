package com.example.e_learningapp.ui.student.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.e_learningapp.BaseFragment;
import com.example.e_learningapp.R;
import com.example.e_learningapp.adapters.AdapterRecyclerMaterial;
import com.example.e_learningapp.databinding.FragmentSolveQuzeBinding;
import com.example.e_learningapp.models.ModelQuiz;
import com.example.e_learningapp.ui.instructor.quiz.FragmentCreateQuiz;
import com.example.e_learningapp.ui.student.material.CourseMaterialViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class CourseQuizFragment  extends BaseFragment {


    CourseQuizViewModel viewModel;


    private int rightAnswer = 0;
    private int grade = 0;
    private int position = 0;
    private String courseId, quizId;
    private FragmentSolveQuzeBinding binding;
    private AdapterRecyclerMaterial adapterRecyclerMaterial;
    private List<ModelQuiz> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSolveQuzeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CourseQuizViewModel.class);
        adapterRecyclerMaterial = new AdapterRecyclerMaterial();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        courseId = CourseQuizFragmentArgs.fromBundle(requireArguments()).getCourseId();
        quizId = CourseQuizFragmentArgs.fromBundle(requireArguments()).getQuizId();
        viewModel.getQuiz(courseId, quizId);
        onClick();
        observers();
    }

    private void onClick() {

        binding.buttonConfermToSolveNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightAnswer != 0) {
                    checkAnswer();
                    initView();
                } else {
                    Toast.makeText(myContext, "Choose Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.chooseOneInSolveQuze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswer = 1;
                binding.chooseOneInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.bottomNavigationTextColor));
                binding.chooseTwoInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));

                binding.chooseThreeInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));

                binding.chooseFoureInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));


            }
        });
        binding.chooseTwoInSolveQuze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswer = 2;
                binding.chooseTwoInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.bottomNavigationTextColor));
                binding.chooseOneInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));

                binding.chooseThreeInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));

                binding.chooseFoureInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));

            }
        });
        binding.chooseThreeInSolveQuze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswer = 3;
                binding.chooseThreeInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.bottomNavigationTextColor));
                binding.chooseOneInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));

                binding.chooseTwoInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));

                binding.chooseFoureInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));
            }
        });
        binding.chooseFoureInSolveQuze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswer = 4;
                binding.chooseFoureInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.bottomNavigationTextColor));
                binding.chooseOneInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));

                binding.chooseThreeInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));

                binding.chooseTwoInSolveQuze
                        .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));
            }
        });


    }

    private void checkAnswer() {
        if (list.get(position).getRightAnswer() == rightAnswer) {
            grade++;
        }
        if (position == (list.size() - 1)) {
            NavHostFragment.findNavController(CourseQuizFragment.this).popBackStack();
        } else {
            position++;
        }
        resetAnswer();
    }
    private void resetAnswer ()
    {
        rightAnswer = 0 ;
        binding.chooseOneInSolveQuze
                .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));
        binding.chooseFoureInSolveQuze
                .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));

        binding.chooseThreeInSolveQuze
                .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));

        binding.chooseTwoInSolveQuze
                .setBackgroundColor(ContextCompat.getColor(myContext, R.color.white));
    }
    private void initView() {
       if (position == (list.size() - 1)){
           binding.buttonConfermToSolveNextQuestion.setText("Finish");
       }
        binding.questionContentInSolveQuze.setText(list.get(position).getQuestion());
        binding.chooseOneInSolveQuze.setText(list.get(position).getAnswer1());
        binding.chooseTwoInSolveQuze.setText(list.get(position).getAnswer2());
        binding.chooseThreeInSolveQuze.setText(list.get(position).getAnswer3());
        binding.chooseFoureInSolveQuze.setText(list.get(position).getAnswer4());

    }


    private void observers (){

        viewModel.quizLiveData.observe(myActivity, new Observer<List<ModelQuiz>>() {
            @Override
            public void onChanged(List<ModelQuiz> modelQuizs) {
                list = modelQuizs ;
                initView();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Toast.makeText(myContext, "Your Answer has been uploaded", Toast.LENGTH_SHORT).show();
        viewModel.uploadResult(courseId,quizId,grade);
        binding = null ;
    }
}
