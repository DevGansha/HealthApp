package com.example.healthapp.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.healthapp.R;
import com.example.healthapp.data.entities.UserData;
import com.example.healthapp.databinding.FrLoginBinding;
import com.example.healthapp.ui.viewmodels.LoginViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    FrLoginBinding frLoginBinding;
    LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(LoginViewModel.class);
        frLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fr_login, container, false);
        return frLoginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        frLoginBinding.signUpBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_loginFragment_to_signupFragment));

        frLoginBinding.loginBtn.setOnClickListener(view1 -> {
            UserData userData = loginViewModel.login(String.valueOf(frLoginBinding.emailTxt.getText()).trim(), String.valueOf(frLoginBinding.pwdTxt.getText()).trim());

            if(userData != null){
                SharedPreferences sharedpreferences = getActivity().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("user_id", String.valueOf(userData.id));
                editor.putString("user_name", userData.full_name);
                editor.apply();

                Navigation.findNavController(view1).navigate(R.id.action_loginFragment_to_mainActivity);
                Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
