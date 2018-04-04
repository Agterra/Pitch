package model;


import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nelly
 */
public class Chemin {
  
  private ArrayList<Case> cases;
  
  public Chemin() {
      
      this.cases = new ArrayList<>();
      
  }
  
  public Chemin( Case c ) {
      
      this.cases = new ArrayList<>();
      
      this.cases.add( c );
      
  }
  
  public void ajouter( Case c ) {

    if( cases.size() >= 2 ) {

      Case caseP1 = cases.get(cases.size()-1);

      Case caseP2 = cases.get(cases.size()-2);

      caseP1.setLien(calculer( c, caseP1 ,caseP2));

      //System.out.println(caseP1.getLien());
      
    }

    cases.add(c);

  }
  
  public void supprimer() {
	
    for( int i = 0; i < cases.size(); i ++ ) {

        cases.get(i).setLien(Lien.VIDE);

    }
	
  }
  
  public boolean contient( int x, int y ) {

    for( Case c : cases ) {

      if( c.getX() == x && c.getY() == y ) {

            return true;

      } 

    }

    return false;

  }
  
  private static Lien calculer( Case c1, Case c2, Case c3 ) {

    int diffx = c1.getX() - c3.getX();
    
    int diffy = c1.getY() - c3.getY();

    if(diffy == 2 || diffy == -2) return Lien.VERTICAL;

    if(diffx == 2 || diffx == -2) return Lien.HORIZONTAL;
    
    if(diffx == -1 && diffy == -1){
        
        if (c2.getY() == c1.getY()){
            
            return Lien.COUDE_BAS_GAUCHE;
            
        } else if (c2.getY() == c3.getY()) {
            
            return Lien.COUDE_HAUT_DROIT;

        } 

    }
    
    if(diffx == 1 && diffy == -1){
       
        if (c2.getY() == c1.getY()){
            
            return Lien.COUDE_BAS_DROIT;
            
        } else if (c2.getY() == c3.getY()) {
            
            return Lien.COUDE_HAUT_GAUCHE;

        }
        
    }
    
    if(diffx == -1 && diffy == 1) {
        
        if (c2.getY() == c1.getY()){
            
            return Lien.COUDE_HAUT_GAUCHE;
            
        } else if (c2.getY() == c3.getY()) {
            
            return Lien.COUDE_BAS_DROIT;

        }
        
    }
    
    if(diffx == 1 && diffy == 1) {
        
        if (c2.getY() == c1.getY()){
            
            return Lien.COUDE_HAUT_DROIT;
            
        } else if (c2.getY() == c3.getY()) {
            
            return Lien.COUDE_BAS_GAUCHE;

        }
        
    }
    
    return Lien.VIDE;
	
  }

    public ArrayList<Case> getCases() {
        return cases;
    }

    public void setCases(ArrayList<Case> cases) {
        this.cases = cases;
    }

    @Override
    public String toString() {
        return "Chemin{" + "cases=" + cases + '}';
    }
    
    public Case getLastElement() {
        
        return this.cases.get(this.cases.size() - 1);
        
    }
    
    public void validerLesCase() {
        
        for ( int i = 0; i < this.cases.size() ; i++ ) {
            
            Case c = this.cases.get(i);
            
            if ( c.getSymbole() != Symbole.VIDE ) {
                
                c.setSymbole(Symbole.getOpposite(c.getSymbole()));
            
            } else if ( c.getLien() != Lien.VIDE ) {
                
                c.setLien(Lien.getOpposite(c.getLien()));
            
            }
            
        }
        
    }
  
}
