package com.example.instaestepario;

public class Contact {
    private String Name;
    private String ImageURL;

    public Contact() {}

    public Contact(String name, String imageURL) {
        this.Name = name;
        this.ImageURL = imageURL;
    }

    public String getName() {
        return Name;
    }

    public String getImageURL() {
        return ImageURL;
    }
}
