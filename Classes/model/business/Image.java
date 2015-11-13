/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.business;

import java.sql.Blob;

/**
 *
 * @author Fabricio
 */
public class Image {
    
    private int id;
    private Animal animal;
    private Blob image;
    
    public Image() {
        
    }
    public Image(Animal animal, Blob image) {
        this.animal = animal;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }  
    
}
