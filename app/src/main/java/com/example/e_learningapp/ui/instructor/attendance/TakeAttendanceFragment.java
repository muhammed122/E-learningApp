package com.example.e_learningapp.ui.instructor.attendance;

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
import com.example.e_learningapp.adapters.AdapterAddStudent;
import com.example.e_learningapp.databinding.FragmentAddStudentsBinding;
import com.example.e_learningapp.databinding.TakeAttendaceFragmentBinding;
import com.example.e_learningapp.ui.instructor.add_students.AddStudentsViewModel;
import com.example.e_learningapp.ui.instructor.add_students.FragmentAddStudentsArgs;
import com.example.e_learningapp.utils.Helper;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TakeAttendanceFragment extends BaseFragment {



    private TakeAttendaceFragmentBinding binding ;
    private TakeAttendanceViewModel viewModel ;
    private int code ;
    private String courseId ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = TakeAttendaceFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(TakeAttendanceViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseId = TakeAttendanceFragmentArgs.fromBundle(requireArguments()).getCourseId();
        observer();
        onClicks();
    }
    private void observer(){
        viewModel.attendanceStatus.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(myContext, s, Toast.LENGTH_SHORT).show();
                MainActivity.stopLoading();
            }
        });

    }

    private void onClicks (){
        binding.btnGenerateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              code =   Helper.generateCode() ;
              binding.textCode.setText(code+"");
              binding.btnConfirm.setEnabled(true);
            }
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.openAttendance(courseId , code);
                MainActivity.startLoading();
            }
        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null ;
    }
}
