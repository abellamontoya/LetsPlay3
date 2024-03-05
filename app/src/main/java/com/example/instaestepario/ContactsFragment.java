package com.example.instaestepario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ContactsFragment extends Fragment {
    private ImageView photo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView horizontalRecyclerView = view.findViewById(R.id.HorizontalRecycler);
        RecyclerView verticalRecyclerView = view.findViewById(R.id.VerticalRecycler);

        // Configure Firestore query for horizontal RecyclerView
        Query horizontalQuery = FirebaseFirestore.getInstance().collection("contactos")
                .limit(50);

        horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        FirestoreRecyclerOptions<Contact> horizontalOptions = new FirestoreRecyclerOptions.Builder<Contact>()
                .setQuery(horizontalQuery, Contact.class)
                .setLifecycleOwner(getViewLifecycleOwner())
                .build();
        ContactsHorizontalAdapter horizontalAdapter = new ContactsHorizontalAdapter(horizontalOptions);
        horizontalRecyclerView.setAdapter(horizontalAdapter);

        Query verticalQuery = FirebaseFirestore.getInstance().collection("contactos")
                .limit(50);

        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        FirestoreRecyclerOptions<Contact> verticalOptions = new FirestoreRecyclerOptions.Builder<Contact>()
                .setQuery(verticalQuery, Contact.class)
                .setLifecycleOwner(getViewLifecycleOwner())
                .build();
        ContactsAdapter verticalAdapter = new ContactsAdapter(verticalOptions);
        verticalRecyclerView.setAdapter(verticalAdapter);

        // Load user's photo
        photo = view.findViewById(R.id.photoImageView);
        String url = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
        Glide.with(requireContext()).load(url).circleCrop().into(photo);

        // Set click listener for photoImageView
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.profileFragment);
            }
        });
    }


    static class ContactsHorizontalAdapter extends FirestoreRecyclerAdapter<Contact, ContactsHorizontalAdapter.PostViewHolder> {
        ContactsHorizontalAdapter(@NonNull FirestoreRecyclerOptions<Contact> options) {
            super(options);
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Contact contact) {
            Glide.with(holder.itemView.getContext()).load(contact.ImageURL).into(holder.imageView);
        }

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal, parent, false);
            return new PostViewHolder(view);
        }

        static class PostViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            PostViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.photoImageView);
            }
        }
    }

    static class ContactsAdapter extends FirestoreRecyclerAdapter<Contact, ContactsAdapter.PostViewHolder> {

        ContactsAdapter(@NonNull FirestoreRecyclerOptions<Contact> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Contact contact) {
            holder.textView.setText(contact.Name);
            Glide.with(holder.itemView.getContext()).load(contact.ImageURL).into(holder.imageView);
        }

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_recylcer, parent, false);
            return new PostViewHolder(view);
        }

        static class PostViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            ImageView imageView;

            PostViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.Name);
                imageView = itemView.findViewById(R.id.contactImageView);
            }
        }
    }
}
