/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
    
}
