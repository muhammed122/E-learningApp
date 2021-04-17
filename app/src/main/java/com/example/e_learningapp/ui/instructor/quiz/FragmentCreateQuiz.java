package com.example.e_learningapp.ui.instructor.quiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.e_learningapp.BaseFragment;
import com.example.e_learningapp.MainActivity;
import com.example.e_learningapp.R;
import com.example.e_learningapp.databinding.FragmentCreateQuizBinding;
import com.example.e_learningapp.databinding.FragmentUploadMaterialBinding;
import com.example.e_learningapp.models.ModelQuiz;
import com.example.e_learningapp.ui.instructor.upload_material.FragmentUploadMaterialArgs;
import com.example.e_learningapp.ui.instructor.upload_material.UploadMaterialViewModel;
import com.example.e_learningapp.utils.Const;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentCreateQuiz extends BaseFragment {

    private FragmentCreateQuizBinding binding;
    private CreateQuizViewModel viewModel;
    private ArrayList<ModelQuiz> listQuiz = new ArrayList<>();
    private int rightAnswer = 0;
    private String courseId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateQuizBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CreateQuizViewModel.class);
        courseId = FragmentUploadMaterialArgs.fromBundle(requireArguments()).getCourseId();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseId = FragmentCreateQuizArgs.fromBundle(requireArguments()).getCourseId();


        observer();
        onClicks();
    }

    private void onClicks() {
        binding.buttonMakeNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
        binding.btnUploadQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listQuiz.size() != 0) {
                    MainActivity.startLoading();
                    viewModel.createQuiz(listQuiz, courseId);
                } else {
                    Toast.makeText(myContext, "You should add at least on question", Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.checkBoxOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rightAnswer = 1;
                    binding.checkBoxTwo.setChecked(false);
                    binding.checkBoxThree.setChecked(false);
                    binding.checkBoxFour.setChecked(false);
                    binding.checkBoxOne.setClickable(false);
                }

            }
        });
        binding.checkBoxTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rightAnswer = 2;
                    binding.checkBoxOne.setChecked(false);
                    binding.checkBoxThree.setChecked(false);
                    binding.checkBoxFour.setChecked(false);
                    binding.checkBoxTwo.setClickable(false);
                }

            }
        });
        binding.checkBoxThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rightAnswer = 3;
                    binding.checkBoxOne.setChecked(false);
                    binding.checkBoxTwo.setChecked(false);
                    binding.checkBoxFour.setChecked(false);
                    binding.checkBoxThree.setClickable(false);
                }

            }
        });
        binding.checkBoxFour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rightAnswer = 4;
                    binding.checkBoxOne.setChecked(false);
                    binding.checkBoxThree.setChecked(false);
                    binding.checkBoxTwo.setChecked(false);
                    binding.checkBoxFour.setClickable(false);
                }

            }
        });

    }

    private void validation() {
        String question = binding.editQuestion.getText().toString().trim();
        String choose1 = binding.editAnswer1.getText().toString().trim();
        String choose2 = binding.editAnswer2.getText().toString().trim();
        String choose3 = binding.editAnswer3.getText().toString().trim();
        String choose4 = binding.editAnswer4.getText().toString().trim();
        if (question.equals("")) {
            binding.editQuestion.setError(getString(R.string.required));
        } else if (choose1.equals("")) {
            binding.editAnswer1.setError(getString(R.string.required));
        } else if (choose2.equals("")) {
            binding.editAnswer2.setError(getString(R.string.required));
        } else if (choose3.equals("")) {
            binding.editAnswer3.setError(getString(R.string.required));
        } else if (choose4.equals("")) {
            binding.editAnswer4.setError(getString(R.string.required));
        } else if (rightAnswer == 0) {
            Toast.makeText(myContext, "Please Select Right Answer", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(myContext, "Added", Toast.LENGTH_SHORT).show();
            listQuiz.add(new ModelQuiz(question, choose1, choose2, choose3, choose4, rightAnswer));
            binding.editQuestion.setText("");
            binding.editAnswer1.setText("");
            binding.editAnswer2.setText("");
            binding.editAnswer3.setText("");
            binding.editAnswer4.setText("");

        }

    }

    private void observer() {

        viewModel.createQuiz.observe(myActivity, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                MainActivity.stopLoading();
                Toast.makeText(myContext, s, Toast.LENGTH_SHORT).show();

                NavHostFragment.findNavController(FragmentCreateQuiz.this).popBackStack();
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
