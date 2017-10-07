package com.example.drklrd.photaharu;

/**
 * Created by drklrd on 10/7/17.
 */

public class Phota {

    private String title,description,image;

    public Phota(String title, String description, String image){
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getImage(){
        return image;
    }

    public void setTitle(String title){
        this.title= title;
    }

    public void setDescription(String description){
        this.description= description;
    }

    public void setImage(String image){
        this.image = image;
    }
}
