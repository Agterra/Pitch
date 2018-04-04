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
    
    private int symbolPairs;
    
    private int completePairs = 0;
    
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
        
        this.plateau[0][taille-1].setSymbole(Symbole.ROND);
                
        this.plateau[taille-1][taille-1].setSymbole(Symbole.ROND);
        
        this.symbolPairs = 1;
                
    }

    public Grille(int largeur, int longueur, int symbolPairs ) {
        
        this.chemins = new ArrayList<>();
        
        this.largeur = largeur;
        
        this.longueur = longueur;
        
        this.plateau = new Case[longueur][largeur];
        
        for (int i = 0; i < this.largeur; i++){
            
            for (int j = 0; j < this.longueur; j++){
                
                this.plateau[i][j] = new Case(j, i);
                
            }
            
        }
        
        this.symbolPairs = symbolPairs;
        
        // Algo pour déterminer possibilité de placement de deux symboles TO DO
    
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
        
        System.out.println("x: " + x + " y: " + y);
        
        Case c = this.getCase(y, x);
        
        if ( c.getSymbole() != Symbole.VIDE ){
            
            Chemin chemin = new Chemin( c );
            
            this.cheminActuel = chemin;
            
        } 
        
        this.setChanged();
        
        this.notifyObservers();
        
    }
    
    public void updateDragAndDrop( int x, int y ) {
        
        System.out.println("x: " + x + " y: " + y);
        
        Case c = this.getCase(y, x);
        
        System.out.println(this.cheminActuel.getCases().toString());
        
        Case casePrecedente = this.getDerniereCaseCheminActuel();
        
        int found = this.cheminActuel.getCases().indexOf(c);
        
        if( found != -1 ) {
            
            Case foundCase = this.cheminActuel.getCases().get(found);

            if( !foundCase.equals(c) && caseEstLibre( c ) && sontVoisines( casePrecedente, c ) ) {

                this.cheminActuel.ajouter( c );

                //System.out.println("Ajout");

            }
        
        } else {
            
            if( caseEstLibre( c ) && sontVoisines( casePrecedente, c ) ) {

                this.cheminActuel.ajouter( c );

                //System.out.println("Ajout");

            }
            
        }
        
        //System.out.println("y: "+ c.getY() +" x: " + c.getX());
                            
        this.setChanged();
        
        this.notifyObservers();
        
    }
    
    public void stopDragAndDrop( int x, int y ) {
        
        if (this.cheminEstValide( this.cheminActuel )){

            this.chemins.add( this.cheminActuel );

            this.updateGrilleAvecChemin( this.cheminActuel );

            this.completePairs ++;

            System.out.println("Chemin valide");

            if ( this.jeuTermine() ) {

                System.out.println("Jeu Termine");

            } else {

                System.out.println("Jeu Perdu");

            }

        } else {

            System.out.println("Chemin invalide");

        }
        
    }
    
    /*public void stopDragAndDrop( int x, int y ) {
        
        System.out.println("x: " + x + " y: " + y);
        
        // Test afin de vérifier que ce n'est pas la première fois que l'on rentre dans un cercle
        if( this.cheminActuel.getCases().size() > 1 ) {
            
            Case c = this.getCase(y, x);
        
            System.out.println(c.toString() + " x : " + x + " y : " + y);
            
            int found = this.cheminActuel.getCases().indexOf(c);

            if ( found != -1 ) {
                
                Case foundCase = this.cheminActuel.getCases().get(found);

                if ( !foundCase.equals(c) && c.getSymbole() != Symbole.VIDE ) {

                    this.cheminActuel.ajouter(c);

                    if (this.cheminEstValide( this.cheminActuel )){

                        this.chemins.add( this.cheminActuel );

                        this.updateGrilleAvecChemin( this.cheminActuel );

                        this.completePairs ++;

                        System.out.println("Chemin valide");

                        if ( this.jeuTermine() ) {

                            System.out.println("Jeu Termine");

                        } else {

                            System.out.println("Jeu Perdu");

                        }

                    } else {

                        System.out.println("Chemin invalide");

                    }

                } else {

                    System.out.println("Chemin impossible : index found = " + found + " actual symbole = " + c.getSymbole() + " equals = " + c.equals(foundCase));

                }
            
            } else {
                
                if ( c.getSymbole() != Symbole.VIDE ) {

                    this.cheminActuel.ajouter(c);

                    if (this.cheminEstValide( this.cheminActuel )){

                        this.chemins.add( this.cheminActuel );

                        this.updateGrilleAvecChemin( this.cheminActuel );

                        this.completePairs ++;

                        System.out.println("Chemin valide");

                        if ( this.jeuTermine() ) {

                            System.out.println("Jeu Termine");

                        } else {

                            System.out.println("Jeu Perdu");

                        }

                    } else {

                        System.out.println("Chemin invalide");

                    }

                } else {

                    System.out.println("Chemin impossible : " + found + c.getSymbole());

                }
                
            }
                       
              
            this.supprimerChemin(cheminActuel);

            this.setChanged();

            this.notifyObservers();
                    
        }
        
    }*/
    
   /* public void stopDragAndDrop ( int x , int y ) {
        
        System.out.println("x: " + x + " y: " + y);
        
        Case c = this.getCase(y, x);
        
        if( this.cheminActuel.getCases().size() > 1 ) {

            int found = this.cheminActuel.getCases().indexOf(c);

            if ( found == -1 && c.getSymbole() != Symbole.VIDE && this.cheminEstValide( this.cheminActuel ) ) {

                this.cheminActuel.ajouter(c);

                this.chemins.add( this.cheminActuel );
                
                this.completePairs ++;
                
                this.supprimerChemin(cheminActuel);

                //System.out.println("Chemin valide");

            } else {

                this.supprimerChemin(cheminActuel);

                //System.out.println("Chemin invalide");

            }

        }
        
        this.setChanged();
        
        this.notifyObservers();
        
    }*/
    
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
    
    private boolean jeuTermine () {
        
        boolean termine = true;
        
        for ( Chemin chemin : this.chemins ) {
            
            if ( !cheminEstValide(chemin) ) {
                
                System.out.println("Partie non terminée : un des chemins est invalide.");
                
                return false;
                
            }
            
        }
        
        if ( !plateauEstPlein() ) {
            
            System.out.println("Partie non terminée : le plateau n'est pas plein.");
            
            return false;
            
        }
        
        if ( this.completePairs != this.symbolPairs ) {
            
            System.out.println("Partie non terminée : toutes les pairs ne sont pas reliées.");
            
            return false;
            
        }
        
        return termine;
        
    }
    
    private boolean cheminEstValide( Chemin chemin ) {
        
        if ( chemin.getCases().get(0).getSymbole() != chemin.getLastElement().getSymbole() ) {
            
            return false;
            
        } else {
            
            for ( Case ca : chemin.getCases() ) {
                
                for ( Chemin ch : this.getChemins() ) {

                    if( ch != chemin && ch.getCases().indexOf( ca ) != -1 ){
                        
                        return false;
                        
                    }

                }
            
            }
            
        }
        
        return true;
        
    }
    
    private void supprimerChemin ( Chemin chemin ) {
        
        chemin.supprimer();
        
        this.cheminActuel = new Chemin();
            
    }
    
    private void updateGrilleAvecChemin ( Chemin chemin ) {
        
        for (Case c : chemin.getCases()) {
             
            if ( c.getSymbole() == Symbole.VIDE ) this.plateau[c.getY()][c.getX()] = (Case)c.clone();
                
        }
        
    }
    
    private boolean sontVoisines( Case c1, Case c2 ) {
        
        //System.out.println( this.cheminActuel.toString() );
        
        boolean estVoisine = false;
        
        if( (Math.abs(c1.getX() - c2.getX()) == 1 && Math.abs(c1.getY() - c2.getY()) == 0) 
                || 
            (Math.abs(c1.getX() - c2.getX()) == 0 && Math.abs(c1.getY() - c2.getY()) == 1) ) {
            
            estVoisine = true;
            
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
