package com.example.e_learningapp.ui.instructor.control;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_learningapp.BaseFragment;
import com.example.e_learningapp.R;
import com.example.e_learningapp.databinding.ChooseBetweenDocStudentBinding;
import com.example.e_learningapp.databinding.CourseControlInstructorFragmentBinding;


public class FragmentInstructorControl extends BaseFragment {


    private CourseControlInstructorFragmentBinding binding;

    private String courseId;
    private String courseName;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = CourseControlInstructorFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseId = FragmentInstructorControlArgs.fromBundle(getArguments()).getCourseId();
        courseName = FragmentInstructorControlArgs.fromBundle(getArguments()).getCourseName();


        onClicks();
    }

    private void onClicks() {


        binding.btnAddMember.setText(courseId);
        binding.btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)myContext.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText( binding.btnAddMember.getText());
                Toast.makeText(myContext, "Copied to clipboard", Toast.LENGTH_SHORT).show();


            }
        });

        binding.showAllGradeInCourseHomeDocotr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(FragmentInstructorControlDirections.actionFragmentInstructorControlToGradsFragment(courseId));
            }
        });

        binding.btnOpenTakeAttendance.setOnClickListener(view -> {
            navigate(FragmentInstructorControlDirections.actionFragmentInstructorControlToTakeAttendanceFragment(courseId));

        });

        binding.openToUploadNewMatrialInCourseHomeDocotr.setOnClickListener(view -> {
            navigate(FragmentInstructorControlDirections.actionFragmentInstructorControlToFragmentUploadMaterial(courseId));

        });
        binding.openToUploadNewMatrialInCourseHomeDocotr.setOnClickListener(view -> {
            navigate(FragmentInstructorControlDirections.actionFragmentInstructorControlToFragmentUploadMaterial(courseId));

        });
        binding.openQuzeToMakeItInCourseHomeDocotr.setOnClickListener(view -> {
            navigate(FragmentInstructorControlDirections.actionFragmentInstructorControlToFragmentCreateQuiz(courseId));

        });
        binding.chatdoctorid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(FragmentInstructorControlDirections.actionFragmentInstructorControlToChatFragment(courseId));
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}