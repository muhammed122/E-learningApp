package com.example.e_learningapp.di;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class HiltModule {

    @Singleton
    @Provides
    public FirebaseAuth getAuth(){

        return FirebaseAuth.getInstance();
    }
    @Singleton
    @Provides
    public DatabaseReference getRef(){

        return FirebaseDatabase.getInstance().getReference();
    }

    @Singleton
    @Provides
    public StorageReference getStorage(){

        return FirebaseStorage.getInstance().getReference();
    }


}
