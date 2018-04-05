package model;

import java.util.Objects;

public class Case {

    private Symbole symbole;

    private Lien lien;

    private int x;

    private int y;

    /**
     * Initialisation des paramètres à leurs valeurs par défaut
     */
    public Case() {

        this.x = -1;

        this.y = -1;

        this.symbole = Symbole.VIDE;

        this.lien = Lien.VIDE;

    }

    /**
     * Initialisation des paramètres avec des valeurs
     *
     * @param symbole Un symbole
     * @param lien Un lien
     * @param x Abscisse de la case
     * @param y Ordonnée de la case
     */
    public Case(Symbole symbole, Lien lien, int x, int y) {

        this.symbole = symbole;

        this.lien = lien;

        this.x = x;

        this.y = y;

    }

    /**
     * Initialisation des paramètres avec des valeurs
     *
     * @param x Abscisse de la case
     * @param y Ordonnée de la case
     */
    public Case(int x, int y) {

        this.symbole = Symbole.VIDE;

        this.lien = Lien.VIDE;

        this.x = x;

        this.y = y;

    }

    /**
     * Getter de symbole
     *
     * @return Le symbole de la case
     */
    public Symbole getSymbole() {
        return symbole;
    }

    /**
     * Setter de symbole
     *
     * @param symbole un symbole
     */
    public void setSymbole(Symbole symbole) {
        this.symbole = symbole;
    }

    /**
     * Getter de lien
     *
     * @return Le lien de la case
     */
    public Lien getLien() {
        return lien;
    }

    /**
     * Setter de lien
     *
     * @param lien Un lien
     */
    public void setLien(Lien lien) {
        this.lien = lien;
    }

    /**
     * Getter de x
     *
     * @return L'abscisse de la case
     */
    public int getX() {
        return x;
    }

    /**
     * Setter de x
     *
     * @param x L'abscisse de la case
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter de y
     *
     * @return L'ordonnée de la case
     */
    public int getY() {
        return y;
    }

    /**
     * Setter de y
     *
     * @param y L'ordonnée de la case
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Retourne vrai si les deux cases sont identiques, faux sinon
     *
     * @param o un objet (une Case)
     * @return vrai ou faux
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
