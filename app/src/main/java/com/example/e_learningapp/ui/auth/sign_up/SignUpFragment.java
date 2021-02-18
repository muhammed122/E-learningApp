package com.example.e_learningapp.ui.auth.sign_up;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_learningapp.BaseFragment;
import com.example.e_learningapp.MainActivity;
import com.example.e_learningapp.databinding.FragmentSignUpBinding;
import com.example.e_learningapp.models.ModelAuth;
import com.example.e_learningapp.utils.Const;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpFragment extends BaseFragment {


    public SignUpViewModel signUpViewModel ;
    private FragmentSignUpBinding binding  ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
       binding= FragmentSignUpBinding.inflate(inflater, container, false);

       signUpViewModel.authLiveData.observe(requireActivity(), new Observer<String>() {
           @Override
           public void onChanged(String s) {
               MainActivity.stopLoading();
               if ("success".equals(s)){
                   if (Const.userType.equals(Const.INSTRUCTOR_USER)){
                       navigate(SignUpFragmentDirections.actionSignUpFragmentToInstructorHomeFragment());
                   }else {
                       navigate(SignUpFragmentDirections.actionSignUpFragmentToStudentHome());
                   }

               }else {
                   Toast.makeText(myContext, s, Toast.LENGTH_SHORT).show();
               }
           }
       });

       binding.registerButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               validation();
           }
       });

        return binding.getRoot();
    }

    private void validation(){
        String email =binding.editTextEmail.getText().toString().trim();
         String name =binding.editTextName.getText().toString().trim() ;
         String password =binding.editTextPassword.getText().toString() ;
        if (name.isEmpty()){
            binding.editTextName.setError("Required");
        }else if(email.isEmpty()){
            binding.editTextEmail.setError("Required");

        }else if(password.isEmpty()){
            binding.editTextPassword.setError("Required");
        }else if (password.length() < 8){
            Toast.makeText(myContext, "Password is should be 8 characters", Toast.LENGTH_SHORT).show();
        }else {
            MainActivity.startLoading();
            signUpViewModel.signUp(new ModelAuth(name,email, Const.userType , "" , Const.userType), password);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}