package model;


import java.util.List;

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
  
  private List<Case> lst;
  
  public void add(Case c) {

	if( lst.size() >= 2 ) {

	  Case caseP1 = lst.get(lst.size()-1);
	  Case caseP2 = lst.get(lst.size()-2);

	  caseP1.lien = calculer(caseP2, c);

	}

	lst.add(c);

  }
  
  public void supprimer() {
	
	for( Case c : lst ) {

	  if(c.symbole != null) {

		c.lien = caseVide;

	  }

	}
	
  }
  
  public boolean contient( int i, int j ) {

	for( Case c : lst ) {

	  if( c.i == i && c.j == j) {

		return true;

	  } 

	  return false;

	}

  }
  
  private static Lien calculer( Case c1, Case c2 ) {

	int dx = c1.x - c2.x;
	int dy = c1.y - c2.y;

	if(dy == 2 || dy == -2) return Symbole.vertical;

	if(dx == 2 || dx == -2) return Symbole.horizontal;

	}

	
}
