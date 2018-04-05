package model;

import java.util.*;

public class Chemin {

    private ArrayList<Case> cases;

    /**
     * Construit un nouveau chemin de cases
     */
    public Chemin() {

        this.cases = new ArrayList<>();

    }

    /**
     * Construit un nouveau chemin avec une case
     *
     * @param c La case à ajouter dans le nouveau chemin
     */
    public Chemin(Case c) {

        this.cases = new ArrayList<>();

        this.cases.add(c);

    }

    /**
     *
     * @param c .
     */
    public void ajouter(Case c) {

        if (cases.size() >= 2) {

            Case caseP1 = cases.get(cases.size() - 1);

            Case caseP2 = cases.get(cases.size() - 2);

            caseP1.setLien(calculer(c, caseP1, caseP2));

            //System.out.println(caseP1.getLien());
        }

        cases.add(c);

    }

    /**
     * Supprime le chemin
     */
    public void supprimer() {

        for (int i = 0; i < cases.size(); i++) {

            cases.get(i).setLien(Lien.VIDE);

        }

    }
    
    /**
     * Renvoie vrai si la case (x,y) est contenue dans un chemin, faux sinon
     *
     * @param x l'abscisse de la case
     * @param y l'ordonnée de la case
     * @return vrai ou faux
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
     *
     * @param c1 une case
     * @param c2 une case voisine
     * @param c3 une autre case voisine
     * @return le lien correspondant aux cases
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
     * Getter de cases
     *
     * @return l'attribut membre cases
     */
    public ArrayList<Case> getCases() {
        return cases;
    }

    /**
     * Setter de cases
     *
     * @param cases .
     */
    public void setCases(ArrayList<Case> cases) {
        this.cases = cases;
    }

    /**
     * Construit une chaîne de caractère regroupant les propriétés d'un objet
     * Chemin
     *
     * @return les propriétés de Chemin
     */
    @Override
    public String toString() {
        return "Chemin{" + "cases=" + cases + '}';
    }

    /**
     * Renvoie la dernière case d'un chemin
     *
     * @return la dernière case
     */
    public Case getDernierElement() {

        return this.cases.get(this.cases.size() - 1);

    }

    /**
     *
     */
    public void validerLesCases() {

        for (int i = 0; i < this.cases.size(); i++) {

            Case c = this.cases.get(i);

            if (c.getSymbole() != Symbole.VIDE) {

                c.setSymbole(Symbole.getOpposite(c.getSymbole()));

            } else if (c.getLien() != Lien.VIDE) {

                c.setLien(Lien.getOppose(c.getLien()));

            }

        }

    }

}
