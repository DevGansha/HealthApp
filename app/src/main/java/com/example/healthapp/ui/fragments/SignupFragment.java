package com.example.healthapp.ui.fragments;

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
import androidx.navigation.Navigation;

import com.example.healthapp.R;
import com.example.healthapp.data.entities.UserData;
import com.example.healthapp.databinding.FrSignupBinding;
import com.example.healthapp.ui.viewmodels.LoginViewModel;
import com.example.healthapp.ui.viewmodels.SignupViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignupFragment extends Fragment {

    SignupViewModel signupViewModel;
    FrSignupBinding signupBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        signupViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(SignupViewModel.class);
        signupBinding = DataBindingUtil.inflate(inflater, R.layout.fr_signup, container, false);
        return signupBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signupBinding.loginBtn.setOnClickListener(view1 -> {
            long success = signupViewModel.signup(new UserData(
                    String.valueOf(signupBinding.nameTxt.getText()),
                    Integer.parseInt(String.valueOf(signupBinding.ageTxt.getText())),
                    String.valueOf(signupBinding.emailTxt.getText()),
                    String.valueOf(signupBinding.pwdTxt.getText())));

            if(success != -1) {
                Toast.makeText(getContext(), "Signup Successfully", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view1).navigate(R.id.action_signupFragment_to_loginFragment);
            }
        });
    }
}
