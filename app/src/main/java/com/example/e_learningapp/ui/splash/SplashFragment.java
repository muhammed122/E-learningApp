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

import com.example.e_learningapp.R;

public class SplashFragment extends Fragment {

    private SplashViewModel splashViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        splashViewModel =
                new ViewModelProvider(this).get(SplashViewModel.class);
        View root = inflater.inflate(R.layout.activity_spalsh, container, false);

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goTo(view);
            }
        }, 3000);


    }

    private void goTo (View view){
        NavDirections navDirections  = SplashFragmentDirections.actionSplashFragmentToLoginFragment();
        Navigation.findNavController(view).navigate(navDirections);
      //  Navigation.findNavController(view).popBackStack(R.id.splashFragment , true);
    }
}