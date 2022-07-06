package com.example.mynotes.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mynotes.model.UserAuthService;

public class RegistrationViewModelFactory implements ViewModelProvider.Factory {

    private UserAuthService userAuthService;

    public  RegistrationViewModelFactory(UserAuthService userAuthService){
        this.userAuthService = userAuthService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return  (T) new RegistrationViewModel(userAuthService);
    }
}
