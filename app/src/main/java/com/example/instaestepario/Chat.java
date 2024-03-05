package com.example.instaestepario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Chat extends Fragment {

    private ImageView goBack;

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

        // Configurando OnClickListener para el botón de retroceso
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Accediendo al FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Reemplazando el ChatFragment con el ContactsFragment
                fragmentManager.beginTransaction()
                        .replace(R.id.chat, new ContactsFragment())
                        .commit();
            }
        });
    }
}
