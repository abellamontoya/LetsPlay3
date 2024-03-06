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
import com.google.firebase.firestore.DocumentSnapshot;

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

        photo = view.findViewById(R.id.photoImageView);
        String url = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
        Glide.with(requireContext()).load(url).circleCrop().into(photo);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.profileFragment);
            }
        });

        horizontalAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Contact contact = documentSnapshot.toObject(Contact.class);
                Bundle bundle = new Bundle();
                bundle.putString("ImageURL", contact.getImageURL());
                bundle.putString("Name", contact.getName());// Cambiar a "ImageURL"
                navigateToChat(bundle);
            }
        });

        verticalAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Contact contact = documentSnapshot.toObject(Contact.class);
                Bundle bundle = new Bundle();
                bundle.putString("ImageURL", contact.getImageURL()); // Cambiar a "ImageURL"
                bundle.putString("Name", contact.getName());// Cambiar a "ImageURL"
                navigateToChat(bundle);
            }
        });
    }

    private void navigateToChat(Bundle bundle) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.chat, bundle);
    }


    interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    static class ContactsHorizontalAdapter extends FirestoreRecyclerAdapter<Contact, ContactsHorizontalAdapter.PostViewHolder> {
        private OnItemClickListener listener;

        ContactsHorizontalAdapter(@NonNull FirestoreRecyclerOptions<Contact> options) {
            super(options);
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Contact contact) {
            Glide.with(holder.itemView.getContext()).load(contact.getImageURL()).into(holder.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal, parent, false);
            return new PostViewHolder(view);
        }

        void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
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
        private OnItemClickListener listener;

        ContactsAdapter(@NonNull FirestoreRecyclerOptions<Contact> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Contact contact) {
            holder.textView.setText(contact.getName());
            Glide.with(holder.itemView.getContext()).load(contact.getImageURL()).into(holder.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_recylcer, parent, false);
            return new PostViewHolder(view);
        }

        void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
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
