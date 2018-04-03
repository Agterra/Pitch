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
        
        int taille = 4;
        
        this.chemins = new ArrayList<>();
        
        this.largeur = taille;
        
        this.longueur = taille;
        
        this.plateau = new Case[taille][taille];
        
        for (int i = 0; i < this.largeur; i++){
            
            for (int j = 0; j < this.longueur; j++){
                
                this.plateau[i][j] = new Case(j, i);
                
            }
            
        }
        
        this.plateau[0][0].setSymbole(Symbole.ROND);
                
        this.plateau[taille-1][taille-1].setSymbole(Symbole.ROND);
                
    }

    public Grille(int largeur, int longueur) {
        
        this.chemins = new ArrayList<>();
        
        this.largeur = largeur;
        
        this.longueur = longueur;
        
        this.plateau = new Case[longueur][largeur];
        
        for (int i = 0; i < this.largeur; i++){
            
            for (int j = 0; j < this.longueur; j++){
                
                this.plateau[i][j] = new Case(j, i);
                
            }
            
        }
    
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

    public Chemin getCheminActuel() {
        return cheminActuel;
    }

    public void setCheminActuel(Chemin cheminActuel) {
        this.cheminActuel = cheminActuel;
    }
    
    public void clic ( int x, int y ) {
        
        for( Chemin c : this.chemins ) {
            
            if( c.contient(x, y) ) {
                
                this.supprimerChemin(c);
                
            }
            
        }
        
    }
    
    public void startDragAndDrop ( int x , int y ) {
        
        Case c = this.getCase(y, x);
        
        if ( c.getSymbole() != Symbole.VIDE ){
            
            Chemin chemin = new Chemin( c );
            
            this.cheminActuel = chemin;
            
        } 
        
        this.setChanged();
        
        this.notifyObservers();
        
    }
    
    public void updateDragAndDrop( int x, int y ) {
        
        Case c = this.getCase(y, x);
        
        if( caseEstLibre( c ) && sontVoisines( c ) ) {
            
            this.cheminActuel.ajouter( c );
            
            //System.out.println("Type : " + c.toString());
            
        }
        
        System.out.println("y: "+ c.getY() +" x: " + c.getX());
                            
        this.setChanged();
        
        this.notifyObservers();
        
    }
    
    public void stopDragAndDrop ( int x , int y ) {
        
        Case c = this.getCase(y, x);
        
        if ( c.getSymbole() != Symbole.VIDE && this.cheminEstValide( this.cheminActuel ) ) {
            
            this.cheminActuel.ajouter(c);
            
            this.chemins.add( this.cheminActuel );
            
            System.out.println("Chemin valide");
            
        } else {
            
            this.supprimerChemin(cheminActuel);
            
            System.out.println("Chemin invalide");

        }
          
        this.setChanged();
        
        this.notifyObservers();
        
    }
    
    private boolean caseEstLibre ( Case c ) {
        
        return c.getLien() == Lien.VIDE && c.getSymbole() == Symbole.VIDE;
        
    }

    private boolean plateauEstPlein () {
        
        boolean plein = true;
        
        for ( Case[] ligne : this.plateau ) {
            
            for ( Case c : ligne ) {
                
                if ( caseEstLibre ( c ) ) {
                    
                    return false;
                    
                }
                
            }
            
        }
        
        return plein;
        
    }
    
    private boolean cheminEstValide( Chemin chemin ) {
        
        if ( chemin.getCases().get(0).getSymbole() != chemin.getLastElement().getSymbole() ) {
            
            return false;
            
        }
        
        return true;
        
    }
    
    private void supprimerChemin ( Chemin chemin ) {
        
        chemin.supprimer();
        
        //this.cheminActuel = new Chemin();
            
    }
    
    private boolean sontVoisines( Case c ) {
        
        System.out.println( this.cheminActuel.toString() );
        
        Case casePrecedente = this.getDerniereCaseCheminActuel();
        
        boolean estVoisine = false;
        
        ArrayList<Case> voisins = new ArrayList<>();
        
        //Case [] plateauActuel = new Case [this.largeur * this.longueur];
        
        // plateauActuel[this.largeur * i + j];
        
        int casePrecedenteY = casePrecedente.getY();
        
        int casePrecedenteX = casePrecedente.getX();
        
        if ( casePrecedenteY == 0 && casePrecedenteX == 0 ) {
            
            voisins.add( this.plateau[casePrecedenteY + 1][casePrecedenteX] );
            
            voisins.add( this.plateau[casePrecedenteY][casePrecedenteX + 1] );
            
        } else if ( casePrecedenteY == 0 && casePrecedenteX == this.largeur - 1 ) {
            
            voisins.add( this.plateau[casePrecedenteY + 1][casePrecedenteX] );
            
            voisins.add( this.plateau[casePrecedenteY][casePrecedenteX - 1] );
            
        } else if ( casePrecedenteY == this.longueur -1 && casePrecedenteX == 0 ) {
            
            voisins.add( this.plateau[casePrecedenteY - 1][casePrecedenteX] );
            
            voisins.add( this.plateau[casePrecedenteY][casePrecedenteX + 1] );
            
        } else if ( casePrecedenteY == this.longueur -1 && casePrecedenteX == this.largeur -1 ) {
            
            voisins.add( this.plateau[ casePrecedenteY - 1][casePrecedenteX] );
            
            voisins.add( this.plateau[casePrecedenteY][casePrecedenteX - 1]);            
            
        } else if (casePrecedenteY == 0) {
            
            voisins.add( this.plateau[casePrecedenteY][casePrecedenteX - 1] );
            
            voisins.add( this.plateau[casePrecedenteY][casePrecedenteX + 1] );
            
            voisins.add( this.plateau[casePrecedenteY + 1][casePrecedenteX] );

        } else if (casePrecedenteY == this.longueur - 1) {
            
            voisins.add( this.plateau[casePrecedenteY][casePrecedenteX - 1] );
                     
            voisins.add( this.plateau[casePrecedenteY][casePrecedenteX + 1] );
                     
            voisins.add( this.plateau[casePrecedenteY - 1][casePrecedenteX] );
                     
        } else if (casePrecedenteX == 0) {
                       
            voisins.add( this.plateau[casePrecedenteY - 1][casePrecedenteX] );
                                                        
            voisins.add( this.plateau[casePrecedenteY + 1][casePrecedenteX] );
                                                        
            voisins.add( this.plateau[casePrecedenteY][casePrecedenteX + 1] );
                                 
        } else if ( casePrecedenteX == this.largeur - 1){
                                   
            voisins.add( this.plateau[casePrecedenteY - 1][casePrecedenteX] );
                                                        
            voisins.add( this.plateau[casePrecedenteY + 1][casePrecedenteX] );
                                                                    
            voisins.add( this.plateau[casePrecedenteY][casePrecedenteX - 1] );
                                 
        } else {
                                   
            voisins.add( this.plateau[casePrecedenteY - 1][casePrecedenteX] );
                                                        
            voisins.add( this.plateau[casePrecedenteY + 1][casePrecedenteX] );
                                                        
            voisins.add( this.plateau[casePrecedenteY][casePrecedenteX - 1] );
                                                        
            voisins.add( this.plateau[casePrecedenteY][casePrecedenteX + 1] );
                                 
        }
        
        for (int i = 0 ; i < voisins.size(); i++){
            
            if ( c.equals( voisins.get(i) ) ) {
                
                return true;
                
            }
            
        }
        
        return estVoisine;
        
    }
    
    public Case getDerniereCaseCheminActuel() {
        
        return this.cheminActuel.getLastElement();
        
    }
    
    public Case getCase( int y, int x ) {
        
        return (Case)this.plateau[y][x].clone();
        
    }
    
    @Override
    public String toString() {
        return "Grille{" + "chemins=" + chemins + ", cheminActuel=" + cheminActuel + ", largeur=" + largeur + ", longueur=" + longueur + ", plateau=" + plateau + '}';
    }
    
}
