package com.example.e_learningapp.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.e_learningapp.BaseFragment;
import com.example.e_learningapp.R;
import com.example.e_learningapp.data.MySharedPrefrance;
import com.example.e_learningapp.utils.Const;

public class SplashFragment extends BaseFragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_spalsh, container, false);

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                action();
            }
        }, 3000);


    }

    private void action(){
        if (MySharedPrefrance.getUserEmail().equals("")){
            navigate(SplashFragmentDirections.actionSplashFragmentToWelcomeScreenFragment());
        }else {
            if (MySharedPrefrance.getUserType().equals(Const.INSTRUCTOR_USER)){

                navigate(SplashFragmentDirections.actionSplashFragmentToInstructorHomeFragment());
            }else {

                navigate(SplashFragmentDirections.actionSplashFragmentToStudentHome());
            }

        }

    }
}