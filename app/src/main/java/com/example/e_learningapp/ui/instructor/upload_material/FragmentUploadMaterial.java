package com.example.e_learningapp.ui.instructor.upload_material;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.e_learningapp.BaseFragment;
import com.example.e_learningapp.MainActivity;
import com.example.e_learningapp.databinding.FragmentUploadMaterialBinding;
import com.example.e_learningapp.databinding.TakeAttendaceFragmentBinding;
import com.example.e_learningapp.ui.instructor.attendance.TakeAttendanceFragmentArgs;
import com.example.e_learningapp.ui.instructor.attendance.TakeAttendanceViewModel;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.File;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentUploadMaterial extends BaseFragment {

    private FragmentUploadMaterialBinding binding ;
    private UploadMaterialViewModel viewModel ;
    Uri uri ;

    private String courseId ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUploadMaterialBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(UploadMaterialViewModel.class);
        courseId = FragmentUploadMaterialArgs.fromBundle(requireArguments()).getCourseId();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        observer();
        onClicks();
    }

    private void onClicks() {
        binding.btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent , 0 );
            }
        });

        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uri == null){
                    Toast.makeText(myContext, "Please Add Pdf", Toast.LENGTH_SHORT).show();
                }else {
                    MainActivity.startLoading();
                    viewModel.uploadFile(uri , courseId);
                }
            }
        });



    }

    private void observer() {

        viewModel.uploadFile.observe(myActivity, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                MainActivity.stopLoading();
                Toast.makeText(myContext, s, Toast.LENGTH_SHORT).show();

            NavHostFragment.findNavController(FragmentUploadMaterial.this).popBackStack();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( data != null && data.getData() != null && requestCode == 0 ){

            uri  = data.getData();

          binding.pdfView.fromUri(uri)
                    .enableSwipe(true)

                    .enableDoubletap(true)
                     .defaultPage(0)
                    .enableAnnotationRendering(true)
                    .scrollHandle(null)
                    .enableDoubletap(true)
                    .load();
        }

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null ;
    }
}
