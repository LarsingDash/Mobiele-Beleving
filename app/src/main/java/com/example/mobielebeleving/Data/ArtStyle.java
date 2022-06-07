package com.example.mobielebeleving.Data;

import android.graphics.Color;
import android.media.Image;

public class ArtStyle {
    private Image image;
    private Color color;

    public ArtStyle(Image image, Color color) {
        this.image = image;
        this.color = color;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
