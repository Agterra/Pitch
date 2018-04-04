/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.image.Image;

/**
 *
 * @author agterra
 */
public enum Symbole {
    
    ROND("file:src/assets/circle.png"),
    
    TRIANGLE("file:src/assets/triangle.png"),
    
    ETOILE("file:src/assets/star.png"),
    
    CARRE("file:src/assets/square.png"),
    
    LOSANGE("file:src/assets/losange.png"),
    
    VIDE("file:src/assets/vide.png");
    
    private final Image image;
    
    private Symbole(String imagePath) {
        
        System.out.println(imagePath);
        
        this.image = new Image(imagePath);
        
    }
    
    public Image getImage() {
        
        return this.image;
        
    }
    
}
