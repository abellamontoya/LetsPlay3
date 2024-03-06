package com.example.instaestepario;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {

    ImageView photoImageView;
    TextView displayNameTextView, emailTextView;
    ImageButton backButton;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        photoImageView = view.findViewById(R.id.photoImageView);
        displayNameTextView = view.findViewById(R.id.displayNameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        backButton = view.findViewById(R.id.backButton); // Cambiar el tipo a ImageView
        ImageView settingsIcon = view.findViewById(R.id.settingsIcon); // Mantener el tipo ImageView

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            displayNameTextView.setText(user.getDisplayName());
            emailTextView.setText(user.getEmail());

            Glide.with(requireView()).load(user.getPhotoUrl()).into(photoImageView);
        }

        // Configurar OnClickListener para el botón backButton
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el NavController desde el Navigation Host
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                // Navegar de vuelta al fragmento anterior
                navController.popBackStack();
            }
        });

        // Configurar OnClickListener para el botón settingsIcon
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el NavController desde el Navigation Host
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                // Navegar al SettingsFragment
                navController.navigate(R.id.settingsFragment);
            }
        });
    }
}
