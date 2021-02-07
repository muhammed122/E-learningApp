package com.example.e_learningapp.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.e_learningapp.R;
import com.example.e_learningapp.databinding.FragmentLoginBinding;
import com.google.firebase.database.FirebaseDatabase;


public class LoginFragment extends Fragment {


    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding ;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onClicks();

        FirebaseDatabase.getInstance().getReference().child("dasd").setValue(1);

    }


    private void  onClicks(){

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToSignUp(view);
            }
        });

    }

    private void goToSignUp (View v){

        NavDirections directions = LoginFragmentDirections.actionLoginFragmentToSignUpFragment();
        Navigation.findNavController(v).navigate(directions);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding  = null ;
    }
}