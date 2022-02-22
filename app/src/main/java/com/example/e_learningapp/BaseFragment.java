package com.example.e_learningapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

public abstract class BaseFragment extends Fragment {




    protected Context myContext ;
    protected View myView  ;
    protected FragmentActivity myActivity ;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myContext = context ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myView = view  ;
        myActivity = requireActivity();

       

    }

    protected void navigate( NavDirections navDirections){

        Navigation.findNavController(myView).navigate(navDirections);

    }
}
