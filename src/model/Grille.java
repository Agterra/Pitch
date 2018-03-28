/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.*;

/**
 *
 * @author agterra
 */
public class Grille extends Observable{
    
    private ArrayList <Chemin> chemins;
    
    Chemin cheminActuel = new Chemin();
    
    private int largeur;
    
    private int longueur;
    
    private Case[][] plateau;
    
    public Grille() {
        
        this.chemins = new ArrayList<>();
        
        this.largeur = 3;
        
        this.longueur = 3;
        
        this.plateau = new Case[3][3];
                
    }

    public Grille(int largeur, int longueur) {
        
        this.chemins = new ArrayList<>();
        
        this.largeur = largeur;
        
        this.longueur = longueur;
        
        this.plateau = new Case[longueur][largeur];
    
    }

    public ArrayList<Chemin> getChemins() {
        return chemins;
    }

    public void setChemins(ArrayList<Chemin> chemins) {
        this.chemins = chemins;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public int getLongueur() {
        return longueur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public Case[][] getPlateau() {
        return plateau;
    }

    public void setPlateau(Case[][] plateau) {
        this.plateau = plateau;
    }
    
    public void clic ( int x, int y ) {
        
        for( Chemin c : this.chemins ) {
            
            if( c.contient(x, y) ) {
                
                c.supprimer();
                
            }
            
        }
        
    }
    
}
