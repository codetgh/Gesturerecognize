package com.example.sumit.gesturerecognize;

import java.util.ArrayList;
import java.util.List;

public class GestureImage {

    private int drawableImage;
    private List<String> name;

    public GestureImage(int drawableImage, ArrayList<String> name) {
        this.drawableImage = drawableImage;
        this.name = name;
    }

    public int getDrawableImage() {
        return drawableImage;
    }

    public void setDrawableImage(int drawableImage) {
        this.drawableImage = drawableImage;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }
}
