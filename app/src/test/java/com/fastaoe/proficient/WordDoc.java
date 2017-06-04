package com.fastaoe.proficient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinjin on 17/5/29.
 */

public class WordDoc implements Cloneable {
    private String text;
    private ArrayList<String> images = new ArrayList<>();

    @Override
    public WordDoc clone() {
        try {
            WordDoc doc = (WordDoc) super.clone();
            doc.text = this.text;
            doc.images = (ArrayList<String>)this.images.clone();
            return doc;
        } catch (Exception e) {
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getImages() {
        return images;
    }

    public WordDoc setImage(String image) {
        this.images.add(image);
        return this;
    }

    public void showDoc() {
        System.out.println("--doc start--");
        System.out.println(text);
        for (String image : images) {
            System.out.println(image);
        }
        System.out.println("--doc end--");
        System.out.println();
    }
}
