package model;

import java.util.*;

public class Grille extends Observable {

    private ArrayList<Chemin> chemins;

    Chemin cheminActuel = new Chemin();

    private int largeur;

    private int longueur;

    private final int pairesSymboles;

    private int pairesCompletes = 0;

    private final Case[][] plateauOrigin;
    
    private Case[][] plateau;

    /**
     * Initialisation des paramètres à leurs valeurs par défaut
     */
    public Grille() {

        int taille = 4;

        this.chemins = new ArrayList<>();

        this.largeur = taille;

        this.longueur = taille;

        this.plateau = new Case[taille][taille];

        for (int i = 0; i < this.largeur; i++) {

            for (int j = 0; j < this.longueur; j++) {

                this.plateau[i][j] = new Case(j, i);

            }

        }

        this.plateau[0][taille - 1].setSymbole(Symbole.ROND);

        this.plateau[taille - 1][taille - 1].setSymbole(Symbole.ROND);

        this.plateauOrigin = this.clonePlateau(this.plateau);
        
        this.pairesSymboles = 1;

    }

    /**
     * Initialisation des paramètres avec des valeurs
     *
     * @param points .
     */
    public Grille(ArrayList<int[]> points) {

        int taille = 4;

        this.chemins = new ArrayList<>();

        this.largeur = taille;

        this.longueur = taille;

        this.plateau = new Case[taille][taille];

        int[] tuple = new int[2];

        for (int i = 0; i < this.largeur; i++) {

            for (int j = 0; j < this.longueur; j++) {

                this.plateau[i][j] = new Case(j, i);

                tuple[0] = i;
                tuple[1] = j;

                for (int k = 0; k < points.size(); k++) {

                    if (points.get(k)[0] == tuple[0] && points.get(k)[1] == tuple[1]) {

                        this.plateau[i][j].setSymbole(Symbole.ROND);

                        System.out.println("Trouvé à :" + i + " " + j);

                    }

                }

            }

        }

        this.pairesSymboles = points.size() / 2;

        this.plateauOrigin = this.clonePlateau(this.plateau);
        
    }

    /**
     * Initialisation des paramètres avec des valeurs
     *
     * @param largeur la largeur de la grille
     * @param longueur la longueur de la grille
     * @param pairesSymboles les paires de symboles à placer sur la grille
     */
    public Grille(int largeur, int longueur, int pairesSymboles) {

        this.chemins = new ArrayList<>();

        this.largeur = largeur;

        this.longueur = longueur;

        this.plateau = new Case[longueur][largeur];

        for (int i = 0; i < this.largeur; i++) {

            for (int j = 0; j < this.longueur; j++) {

                this.plateau[i][j] = new Case(j, i);

            }

        }

        this.pairesSymboles = pairesSymboles;

        this.plateauOrigin = this.clonePlateau(this.plateau);
        
        // Algo pour déterminer possibilité de placement de deux symboles TO DO
    }

    /**
     * Getter de chemins
     *
     * @return la liste des chemins
     */
    public ArrayList<Chemin> getChemins() {
        return chemins;
    }

    /**
     * Setter de chemins
     *
     * @param chemins .
     */
    public void setChemins(ArrayList<Chemin> chemins) {
        this.chemins = chemins;
    }

    /**
     * Getter de largeur
     *
     * @return la largeur de la grille
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Setter de largeur
     *
     * @param largeur .
     */
    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    /**
     * Getter de longueur
     *
     * @return la longueur de la grille
     */
    public int getLongueur() {
        return longueur;
    }

    /**
     * Setter de longueur
     *
     * @param longueur .
     */
    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    /**
     * Getter de plateau
     *
     * @return le plateau
     */
    public Case[][] getPlateau() {
        return plateau;
    }

    /**
     * Setter de plateau
     *
     * @param plateau .
     */
    public void setPlateau(Case[][] plateau) {
        this.plateau = plateau;
    }

    /**
     * Getter de cheminActuel
     *
     * @return le chemin en cours
     */
    public Chemin getCheminActuel() {
        return cheminActuel;
    }

    /**
     * Setter de cheminActuel
     *
     * @param cheminActuel .
     */
    public void setCheminActuel(Chemin cheminActuel) {
        this.cheminActuel = cheminActuel;
    }

    /**
     * Supprime le chemin quand on clique dessus
     *
     * @param x abscisse de la case
     * @param y ordonnée de la case
     */
    public void clic(int x, int y) {

        for (Chemin c : this.chemins) {

            if (c.contient(x, y)) {

                this.supprimerChemin(c);

            }

        }

        this.setChanged();

        this.notifyObservers();

    }
    
    /**
     * Supprime tous les chemins d'une grille
     */
    public void supprimerTousLesChemins() {
        
        for(Chemin c : this.chemins) {
            
            c.supprimer();
            
        }
        
        this.setChanged();
        
        this.notifyObservers();
    }

    

    /**
     * Commencer le drag and drop sur un symbole
     *
     * @param x abscisse de la case
     * @param y ordonnée de la case
     */
    public void commencerDragAndDrop(int x, int y) {

        System.out.println("x: " + x + " y: " + y);

        Case c = this.getCase(y, x);

        if (c.getSymbole() != Symbole.VIDE) {

            Chemin chemin = new Chemin(c);

            this.cheminActuel = chemin;

        }

        this.setChanged();

        this.notifyObservers();

    }

    /**
     * Avancer dans le drag and drop pour dessiner un chemin
     *
     * @param x abscisse de la case
     * @param y ordonnée de la case
     */
    public void majDragAndDrop(int x, int y) {

        System.out.println("x: " + x + " y: " + y);

        Case c = this.getCase(y, x);

        //System.out.println(this.cheminActuel.getCases().toString());
        Case casePrecedente = this.getDerniereCaseCheminActuel();

        int trouver = this.cheminActuel.getCases().indexOf(c);

        if (trouver == -1 && sontVoisines(casePrecedente, c)) {

            if (caseEstLibre(c)) {

                this.cheminActuel.ajouter(c);

                System.out.println("Ajout");

            } else if (c.getLien() == Lien.VIDE && c.getSymbole() != Symbole.VIDE) {

                if (c.getSymbole() == this.cheminActuel.getCases().get(0).getSymbole()) {

                    this.cheminActuel.ajouter(c);

                    System.out.println(this.cheminActuel.getCases().toString());
                    
                    this.finirDragAndDrop();

                    System.out.println("Ajout");

                }

            }

        }

        //System.out.println("y: "+ c.getY() +" x: " + c.getX());
        this.setChanged();

        this.notifyObservers();

    }

    /**
     * Arrêter le drag and drop quad on arrive sur le deuxieme symbole
     */
    public void finirDragAndDrop() {

        if( this.cheminActuel.getCases().size() > 0 ){
        
            if (this.cheminEstValide(this.cheminActuel)) {

                this.cheminActuel.validerLesCases();

                this.chemins.add(this.cheminActuel);

                this.majGrilleAvecChemin(this.cheminActuel);

                this.pairesCompletes++;

                System.out.println("Chemin valide");

                if (this.jeuTermine()) {

                    System.out.println("Jeu terminé");

                } else {

                    System.out.println("Jeu perdu");

                }

            } else {

                supprimerChemin(this.cheminActuel);

                System.out.println("Chemin invalide");

            }
        
        }

        this.setChanged();

        this.notifyObservers();

    }
    
    /**
     * Reinitialiser la grille à son état de départ
     */
    
    public void reinitialiser(){
        
        this.chemins = new ArrayList<>();
        
        this.supprimerChemin(cheminActuel);
        
        this.pairesCompletes = 0;
        
        this.plateau = this.clonePlateau(this.plateauOrigin);
        
        this.setChanged();
        
        this.notifyObservers();
        
    }
    
    /**
     * Annuler le dernier coup
     */
    
    public void annulerDernierCoup() {
        
        this.afficherPlateau();
        
        if (this.chemins.size() > 0){
            
            Chemin chemin = this.chemins.get(this.chemins.size() - 1);
            
            this.supprimerChemin(chemin);
            
            this.majGrilleAvecChemin(chemin);
            
            this.chemins.remove(this.chemins.size() - 1);
            
        } else {
            
            this.supprimerChemin(this.cheminActuel);
            
        }
        
        this.afficherPlateau();
        
        this.setChanged();
        
        this.notifyObservers();
        
    }

    /**
     * Renvoie vrai si la case est libre (sans symbole ni lien), faux sinon
     *
     * @param c une case
     * @return vrai ou faux
     */
    private boolean caseEstLibre(Case c) {

        return c.getLien() == Lien.VIDE && c.getSymbole() == Symbole.VIDE;

    }

    /**
     * Renvoie vrai si le tableau est plein, faux sinon
     *
     * @return vrai ou faux
     */
    private boolean plateauEstPlein() {

        boolean plein = true;

        for (Case[] ligne : this.plateau) {

            for (Case c : ligne) {

                if (caseEstLibre(c)) {

                    return false;

                }

            }

        }

        return plein;

    }

    /**
     * Renvoie vrai si le jeu est terminé (toutes las cases sont occupées et
     * tous les symboles sont reliés), faux sinon
     *
     * @return vrai ou faux
     */
    private boolean jeuTermine() {

        boolean termine = true;

        for (Chemin chemin : this.chemins) {

            if (!cheminEstValide(chemin)) {

                System.out.println("Partie non terminée : un des chemins est invalide.");

                return false;

            }

        }

        if (!plateauEstPlein()) {

            System.out.println("Partie non terminée : le plateau n'est pas plein.");

            return false;

        }

        if (this.pairesCompletes != this.pairesSymboles) {

            System.out.println("Partie non terminée : toutes les paires ne sont pas reliées.");

            return false;

        }

        return termine;

    }

    /**
     * Renvoie vrai si le chemin est valide, faux sinon
     *
     * @return vrai ou faux
     */
    private boolean cheminEstValide(Chemin chemin) {

        if (cheminMemePaire(chemin)) {

            return false;

        } else {

            for (Case ca : chemin.getCases()) {

                for (Chemin ch : this.getChemins()) {

                    if (ch != chemin && ch.getCases().indexOf(ca) != -1) {

                        return false;

                    }

                }

            }

        }

        return true;

    }

    /**
     *
     * @param chemin .
     * @return .
     */
    public boolean cheminMemePaire(Chemin chemin) {

        return chemin.getCases().get(0).getSymbole() != chemin.getDernierElement().getSymbole();

    }

    /**
     * Supprime un chemin
     *
     * @param chemin le chemin à supprimer
     */
    private void supprimerChemin(Chemin chemin) {

        chemin.supprimer();

        majGrilleAvecChemin(chemin);
        
    }

    /**
     *
     * @param chemin
     */
    private void majGrilleAvecChemin(Chemin chemin) {

        for (Case c : chemin.getCases()) {

            if (c.getSymbole() == Symbole.VIDE) {
                
                this.plateau[c.getY()][c.getX()] = (Case) c.clone();
                
            }

        }

    }

    /**
     * Renvoie vrai si c1 et c2 sont voisine, faux sinon
     *
     * @param c1 une case
     * @param c2 une autre case
     * @return vrai ou faux
     */
    private boolean sontVoisines(Case c1, Case c2) {

        //System.out.println(this.cheminActuel.toString());
        if ((Math.abs(c1.getX() - c2.getX()) == 1 && Math.abs(c1.getY() - c2.getY()) == 0)
                || (Math.abs(c1.getX() - c2.getX()) == 0 && Math.abs(c1.getY() - c2.getY()) == 1)) {

            return true;

        }

        return false;

    }

    /**
     *
     * @return la dernière cas du chemin actuel
     */
    public Case getDerniereCaseCheminActuel() {

        return this.cheminActuel.getDernierElement();

    }

    /**
     * 
     * @param y
     * @param x
     * @return un clone d'une case du tableau en y,x
     */
    
    public Case getCase(int y, int x) {

        return (Case) this.plateau[y][x].clone();

    }

    /**
     * Clone le plateau
     * @param cases
     * @return un clone du plateau
     */
    
    public Case[][] clonePlateau( Case[][] cases ){
        
        Case[][] copieCases = new Case[this.longueur][this.largeur];
        
        for(int i = 0; i < this.longueur; i++){
            
            for(int j = 0; j < this.largeur; j++){
                
                copieCases[i][j] = (Case)cases[i][j].clone();
                
            }
            
        }
        
        return copieCases;
        
    }
    
    /**
     * Afficher le plateau actuel
     */
    
    public void afficherPlateau() {
        
        System.out.println();
        
        for(int i = 0; i < this.longueur; i ++){
            
            for(int j = 0; j < this.largeur; j++){
                
                System.out.print("["+this.plateau[i][j]+"]");
                
            }
            
            System.out.println();
            
        }
        
        System.out.println();
        
    }
    
    /**
     * Construit une chaîne de caractère regroupant les propriétés d'un objet
     * Grille
     *
     * @return les propriétés de Grille
     */
    @Override
    public String toString() {
        return "Grille{" + "chemins=" + chemins + ", cheminActuel=" + cheminActuel + ", largeur=" + largeur + ", longueur=" + longueur + ", plateau=" + plateau + '}';
    }

}
