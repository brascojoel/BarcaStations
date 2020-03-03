package com.example.barcastations;

import android.graphics.Bitmap;

public class CardModel {

    private String line;
    private String description;
    private Bitmap image;

    public CardModel(String line, String description, Bitmap image) {
        this.line = line;
        this.description = description;
        this.image = image;
    }

    public CardModel() {
    }


    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
