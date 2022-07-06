package com.example.mynotes.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mynotes.model.Status;
import com.example.mynotes.model.User;
import com.example.mynotes.model.UserAuthService;

public class RegistrationViewModel extends ViewModel {

    private UserAuthService authService;
    private MutableLiveData<Status> _userRegistrationStatus = new MutableLiveData<>();
    public LiveData<Status> userRegistrationStatus = (LiveData<Status>)_userRegistrationStatus;

    public RegistrationViewModel(UserAuthService authService){
        this.authService = authService;
    }

    public void userRegistration(User user){
        authService.registrationUser(user, (status, msg) -> _userRegistrationStatus.setValue(new Status(status, msg)));
    }

}
