package com.example.e_learningapp.ui.auth.login;

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
import com.example.e_learningapp.databinding.FragmentLoginBinding;

import com.example.e_learningapp.utils.Const;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends BaseFragment {


    public LoginViewModel loginViewModel;
    private FragmentLoginBinding binding ;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
         binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginViewModel.loginLiveData.observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                MainActivity.stopLoading();
                if ("success".equals(s)){
                    if (Const.userType.equals(Const.INSTRUCTOR_USER)){
                        navigate(LoginFragmentDirections.actionLoginFragmentToInstructorHomeFragment());
                    }else {
                        navigate(LoginFragmentDirections.actionLoginFragmentToStudentHome());
                    }

                }else {
                    Toast.makeText(myContext, s, Toast.LENGTH_SHORT).show();
                }
            }
        });
        onClicks();

    }


    private void  onClicks(){
         binding.loginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 login();
             }
         });

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment());
            }
        });

    }
    private void login(){
        String email =binding.editTextEmail.getText().toString().trim();
        String password =binding.editTextPassword.getText().toString() ;
        if(email.isEmpty()){
            binding.editTextEmail.setError("Required");
        }else if(password.isEmpty()){
            binding.editTextPassword.setError("Required");
        }else if (password.length() < 8){
            Toast.makeText(myContext, "Password is should be 8 characters", Toast.LENGTH_SHORT).show();
        }else {
            MainActivity.startLoading();
            loginViewModel.login(email, password);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding  = null ;
    }
}