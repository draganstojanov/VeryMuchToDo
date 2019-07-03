package com.andraganoid.verymuchtodo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andraganoid.verymuchtodo.model.Message;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    public FirebaseAuth mAuth;
    public String registrationName;
    public String registrationMail;
    public String registrationPass;
    public String userMail;
    public String userPass;

}
