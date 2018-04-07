package model;

import java.util.Objects;

public class Case {

    /**************************************************************************/
    // VARIABLES MEMBRES
    /**************************************************************************/
    
    private int x;

    private int y;
    
    private Symbole symbole;

    private Lien lien;

    /**************************************************************************/
    // FONCTIONS MEMBRES
    /**************************************************************************/
    
    /**************************************************************************/
    // CONSTRUCTEURS
    /**************************************************************************/
    
    /**
     * Initialise les paramètres à leurs valeurs par défaut
     */
    public Case() {

        this.x = -1;

        this.y = -1;

        this.symbole = Symbole.VIDE;

        this.lien = Lien.VIDE;

    }

    /**
     * Initialise les paramètres avec des valeurs
     *
     * @param x Abscisse de la Case
     * @param y Ordonnée de la Case
     */
    public Case(int x, int y) {

        this.x = x;

        this.y = y;
        
        this.symbole = Symbole.VIDE;

        this.lien = Lien.VIDE;

    }
    
    /**
     * Initialise les paramètres avec des valeurs
     *
     * @param symbole Un symbole
     * @param lien Un lien
     * @param x Abscisse de la Case
     * @param y Ordonnée de la Case
     */
    public Case(int x, int y, Symbole symbole, Lien lien) {

        this.x = x;

        this.y = y;
        
        this.symbole = symbole;

        this.lien = lien;

    }

    /**************************************************************************/
    // ACCESSEURS & MUTATEURS
    /**************************************************************************/
    
    /**
     * Lit la variable x
     *
     * @return L'attribut membre x (abscisse)
     */
    public int getX() {
        
        return x;
        
    }

    /**
     * Ecrit la variable x
     *
     * @param x L'abscisse de la Case
     */
    public void setX(int x) {
        
        this.x = x;
        
    }

    /**
     * Lit la variable y
     *
     * @return L'attribut membre y (ordonnée)
     */
    public int getY() {
        
        return y;
        
    }

    /**
     * Ecrit la variable y
     *
     * @param y L'ordonnée de la Case
     */
    public void setY(int y) {
        
        this.y = y;
        
    }

    /**
     * Lit la variable symbole
     *
     * @return L'attribut membre symbole
     */
    public Symbole getSymbole() {
        
        return symbole;
        
    }

    /**
     * Ecrit la variable symbole
     *
     * @param symbole Un symbole
     */
    public void setSymbole(Symbole symbole) {
        
        this.symbole = symbole;
        
    }

    /**
     * Lit la variable lien
     *
     * @return L'attribut membre lien
     */
    public Lien getLien() {
        
        return lien;
        
    }

    /**
     * Ecrit la variable lien
     *
     * @param lien Un lien
     */
    public void setLien(Lien lien) {
        
        this.lien = lien;
        
    }
    
    /**************************************************************************/
    // FONCTIONS OVERRIDE
    /**************************************************************************/
    
    /**
     * Retourne vrai si les deux cases sont identiques, faux sinon
     *
     * @param o Un objet (une Case)
     * @return Vrai ou faux
     */
    @Override
    public boolean equals(Object o) {

        if (o instanceof Case) {

            Case c = (Case) o;

            if (this.x == c.x && this.y == c.y) {

                return true;

            }

        }

        return false;

    }

    /**
     * Construit une chaîne de caractère regroupant les propriétés d'un objet Case
     *
     * @return Les propriétés de Case
     */
    @Override
    public String toString() {

        return (this.lien != Lien.VIDE) ? this.lien.toString() : this.symbole.toString();

    }

    /**
     * Clone une case avec ses propriétés
     *
     * @return La nouvelle case
     */
    @Override
    protected Object clone() {

        Case c = new Case();

        c.setX(this.x);

        c.setY(this.y);

        c.setLien(this.lien);

        c.setSymbole(this.symbole);

        return c;

    }

}
