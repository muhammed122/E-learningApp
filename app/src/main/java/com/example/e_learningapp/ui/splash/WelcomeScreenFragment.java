package com.example.e_learningapp.ui.splash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.e_learningapp.BaseFragment;
import com.example.e_learningapp.databinding.ChooseBetweenDocStudentBinding;
import com.example.e_learningapp.utils.Const;

public class WelcomeScreenFragment extends BaseFragment {



    private ChooseBetweenDocStudentBinding binding ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = ChooseBetweenDocStudentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }
    private void initViews(){

        binding.studentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.doctorCheckBox.setChecked(false);
                    Const.userType = Const.STUDENT_USER;
                }else {
                    Const.userType = "";

                }


            }
        });
        binding.doctorCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.studentCheckBox.setChecked(false);
                    Const.userType = Const.INSTRUCTOR_USER;
                }else {
                    Const.userType = "";
                }
            }
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Const.userType.equals("")){

                    Toast.makeText(myContext, "Please select the type", Toast.LENGTH_SHORT).show();

                }else {
                    navigate(WelcomeScreenFragmentDirections.actionWelcomeScreenFragmentToLoginFragment());

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
