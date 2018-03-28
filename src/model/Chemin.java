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

      caseP1.setLien(calculer(caseP2, c));

    }

    cases.add(c);

  }
  
  public void supprimer() {
	
    for( Case c : cases ) {

      if(c.getSymbole() != null) {

            c.setLien(Lien.VIDE);

      }

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
  
  private static Lien calculer( Case c1, Case c2 ) {

    int dx = c1.getX() - c2.getX();
    
    int dy = c1.getY() - c2.getY();

    if(dy == 2 || dy == -2) return Lien.VERTICAL;

    if(dx == 2 || dx == -2) return Lien.HORIZONTAL;

    return Lien.VIDE;
	
  }

    public ArrayList<Case> getCases() {
        return cases;
    }

    public void setCases(ArrayList<Case> cases) {
        this.cases = cases;
    }
  
}
