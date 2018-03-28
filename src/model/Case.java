/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author agterra
 */
public class Case {
    
    private Symbole symbole;
    
    private Lien lien;
    
    private int x;
    
    private int y;
    
    public Case () {
        
        this.x = -1;
        
        this.y = -1;
        
        this.symbole = Symbole.VIDE;
        
        this.lien = Lien.VIDE;
        
    }
    
    public Case (Symbole symbole, Lien lien, int x, int y) {
        
        this.symbole = symbole;
        
        this.lien = lien;
        
        this.x = x;
        
        this.y = y;
        
    }
    
    public Case (int x, int y) {
        
        this.symbole = Symbole.VIDE;
        
        this.lien = Lien.VIDE;
        
        this.x = x;
        
        this.y = y;
        
    }

    public Symbole getSymbole() {
        return symbole;
    }

    public void setSymbole(Symbole symbole) {
        this.symbole = symbole;
    }

    public Lien getLien() {
        return lien;
    }

    public void setLien(Lien lien) {
        this.lien = lien;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public boolean equals( Object o ){
        
        if( o instanceof Case ) {
        
            Case c = (Case)o;
            
            if( this.symbole == c.symbole ) {
                
                return true;
                
            }
        
        }
        
        return false;
        
    }

}
