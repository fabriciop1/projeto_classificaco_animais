/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.business;

import java.util.ArrayList;

/**
 *
 * @author Fabricio
 */
public class Animal {
    
    private int id;
    private String description;
    private ArrayList<Score> scores;
    private ArrayList<Image> images;
    
    public Animal() {
        
    }

    public Animal(String description, ArrayList<Score> scores, ArrayList<Image> images) {
        this.description = description;
        this.scores = scores;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
    
}
