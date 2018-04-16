package model;

import java.util.*;

public class Chemin {

    /**************************************************************************/
    // VARIABLES MEMBRES
    /**************************************************************************/
    
    private ArrayList<Case> cases;

    /**************************************************************************/
    // FONCTIONS MEMBRES
    /**************************************************************************/
    
    /**************************************************************************/
    // CONSTRUCTEURS
    /**************************************************************************/
    
    /**
     * Initialise les paramètres à leurs valeurs par défaut
     */
    public Chemin() {

        this.cases = new ArrayList<>();

    }

    /**
     * Initialise les paramètres avec des valeurs
     *
     * @param c La case à ajouter dans le nouveau chemin
     */
    public Chemin(Case c) {

        this.cases = new ArrayList<>();

        this.cases.add(c);

    }

    /**************************************************************************/
    // ACCESSEURS & MUTATEURS
    /**************************************************************************/
    
    /**
     * Lit la variable cases
     *
     * @return L'attribut membre cases
     */
    public ArrayList<Case> getCases() {
        
        return cases;
        
    }

    /**
     * Ecrit la variable cases
     *
     * @param cases .
     */
    public void setCases(ArrayList<Case> cases) {
        
        this.cases = cases;
        
    }
    
    /**
     * Renvoie la première case d'un chemin
     * @return La première case
     */
    public Case getPremierElement() {
        
        return this.cases.get(0);
        
    }
    
    /**
     * Renvoie la dernière case d'un chemin
     *
     * @return la dernière case
     */
    public Case getDernierElement() {

        return this.cases.get(this.cases.size() - 1);

    }

    /**************************************************************************/
    // AUTRES FONCTIONS
    /**************************************************************************/
    
    /**
     * 
     * @param c La case à ajouter
     */
    public void ajouter(Case c) {

        if (cases.size() >= 2) {

            Case caseP1 = cases.get(cases.size() - 1);

            Case caseP2 = cases.get(cases.size() - 2);

            caseP1.setLien(calculer(c, caseP1, caseP2));
            
        }

        cases.add(c);

    }

    /**
     * Supprime le Chemin
     */
    public void supprimer() {

        this.invaliderLesCases();
        
        for (int i = 0; i < cases.size(); i++) {

            cases.get(i).setLien(Lien.VIDE);

        }

    }
    
    /**
     * Renvoie vrai si la case (x,y) est contenue dans un Chemin, faux sinon
     *
     * @param x L'abscisse de la case
     * @param y L'ordonnée de la case
     * @return Vrai ou faux
     */
    public boolean contient(int x, int y) {

        for (Case c : cases) {

            if (c.getX() == x && c.getY() == y) {

                return true;

            }

        }

        return false;

    }

    /**
     * Calcule le lien correspondant aux Cases utilisées
     * @param c1 Une Case
     * @param c2 Une Case voisine
     * @param c3 Une autre Case voisine
     * @return Le lien correspondant aux cases
     */
    private static Lien calculer(Case c1, Case c2, Case c3) {

        int diffX = c1.getX() - c3.getX();

        int diffY = c1.getY() - c3.getY();

        if (diffY == 2 || diffY == -2) {
            return Lien.VERTICAL;
        }

        if (diffX == 2 || diffX == -2) {
            return Lien.HORIZONTAL;
        }

        if (diffX == -1 && diffY == -1) {

            if (c2.getY() == c1.getY()) {

                return Lien.COUDE_BAS_GAUCHE;

            } else if (c2.getY() == c3.getY()) {

                return Lien.COUDE_HAUT_DROIT;

            }

        }

        if (diffX == 1 && diffY == -1) {

            if (c2.getY() == c1.getY()) {

                return Lien.COUDE_BAS_DROIT;

            } else if (c2.getY() == c3.getY()) {

                return Lien.COUDE_HAUT_GAUCHE;

            }

        }

        if (diffX == -1 && diffY == 1) {

            if (c2.getY() == c1.getY()) {

                return Lien.COUDE_HAUT_GAUCHE;

            } else if (c2.getY() == c3.getY()) {

                return Lien.COUDE_BAS_DROIT;

            }

        }

        if (diffX == 1 && diffY == 1) {

            if (c2.getY() == c1.getY()) {

                return Lien.COUDE_HAUT_DROIT;

            } else if (c2.getY() == c3.getY()) {

                return Lien.COUDE_BAS_GAUCHE;

            }

        }

        return Lien.VIDE;

    }

    /**
     * Valide le Chemin proposé par l'utilisateur
     */
    public void validerLesCases() {

        for (int i = 0; i < this.cases.size(); i++) {

            Case c = this.cases.get(i);

            if (c.getSymbole() != Symbole.VIDE) {

                c.setSymbole(Symbole.getSymboleValide(c.getSymbole()));

            } else if (c.getLien() != Lien.VIDE) {

                c.setLien(Lien.getLienValide(c.getLien()));

            }

        }

    }
    
    /**
     * Invalide le Chemin proposé par l'utilisateur
     */
    public void invaliderLesCases() {
        
        for (int i = 0; i < this.cases.size(); i++) {

            Case c = this.cases.get(i);

            if (c.getSymbole() != Symbole.VIDE) {

                c.setSymbole(Symbole.getSymboleInvalide(c.getSymbole()));

            } else if (c.getLien() != Lien.VIDE) {

                c.setLien(Lien.getLienInvalide(c.getLien()));

            }

        }
        
    }
    
    /**************************************************************************/
    // FONCTIONS OVERRIDE
    /**************************************************************************/
    
    /**
     * Construit une chaîne de caractère regroupant les propriétés d'un objet Chemin
     *
     * @return Les propriétés de Chemin
     */
    @Override
    public String toString() {
        return "Chemin{" + "cases=" + cases + '}';
    }
    
}
