package com.andraganoid.verymuchtodo;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class MainViewModel extends ViewModel {
    public FirebaseAuth mAuth;
    public String registrationName;
    public String registrationMail;
    public String registrationPass;
    public String userMail;
    public String userPass;
}
