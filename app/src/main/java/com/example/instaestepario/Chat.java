package com.example.instaestepario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

public class Chat extends Fragment {

    private ImageView goBack;
    private ImageView contactPhoto;
    private TextView contactName;
    private Button mapButton;

    public Chat() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obteniendo referencia al botón de retroceso
        goBack = view.findViewById(R.id.goBack);
        contactPhoto = view.findViewById(R.id.profilePicture);
        contactName = view.findViewById(R.id.contactName);
        mapButton = view.findViewById(R.id.map);

        // Obtén la URL de la foto del contacto del argumento pasado desde el fragmento de contacto
        String ImageURL = getArguments().getString("ImageURL");
        String ContactName = getArguments().getString("Name");
        if (ImageURL != null) {
            // Carga la foto del contacto en el ImageView
            Glide.with(requireContext()).load(ImageURL).into(contactPhoto);
            contactName.setText(ContactName);
        }

        // Configurando OnClickListener para el botón de retroceso
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navega hacia atrás al fragmento de contactos
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.popBackStack();
            }
        });

        // Configurando OnClickListener para el botón de map
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navega al MapFragment
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.mapFragment);
            }
        });
    }
}