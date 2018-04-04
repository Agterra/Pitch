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
public enum Lien {
    
    VERTICAL("file:src/assets/vertical.png"),
    
    HORIZONTAL("file:src/assets/horizontal.png"),
    
    COUDE_HAUT_DROIT("file:src/assets/coude_haut_droit.png"),
    
    COUDE_HAUT_GAUCHE("file:src/assets/coude_haut_gauche.png"),
    
    COUDE_BAS_DROIT("file:src/assets/coude_bas_droit.png"),
    
    COUDE_BAS_GAUCHE("file:src/assets/coude_bas_gauche.png"),
    
    VIDE("file:src/assets/vide.png");
    
    private final Image image;
    
    private Lien( String pathName) {
        
        this.image = new Image(pathName);
        
    }
    
    public Image getImage() {
        
        return this.image;
        
    }
    
}
