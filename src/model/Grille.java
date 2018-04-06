package model;

import java.util.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Grille extends Observable {

    private ArrayList<Chemin> chemins;

    Chemin cheminActuel = new Chemin();

    private int largeur;

    private int longueur;

    private int pairesSymboles;

    private int pairesCompletes;
    
    private int[] derniereCaseConnue = new int[2];

    private Case[][] plateauOrigine;
    
    private Case[][] plateau;
    
    private boolean cheminNonValide = false;
    
    private boolean partieTerminee;

    /**
     * Initialise les paramètres à leurs valeurs par défaut
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

        this.plateauOrigine = this.clonePlateau(this.plateau);
        
        this.pairesSymboles = 1;
        
        this.partieTerminee = false;

    }

    /**
     * Initialise les paramètres avec des valeurs
     *
     * @param largeur la largeur de la grille
     * @param longueur la longueur de la grille
     * @param pairesSymboles les paires de symboles à placer sur la grille
     */
    public Grille(int largeur, int longueur, int pairesSymboles) {

        GenerateurPaires p = new GenerateurPaires(4, 4, 2);
        
        ArrayList<int[]> points = p.genererPaires();
        
        ArrayList<Symbole> symboles = p.symbolesPaires;
                
        this.chemins = new ArrayList<>();

        this.largeur = largeur;

        this.longueur = longueur;

        this.plateau = new Case[longueur][largeur];

        int[] tuple = new int[2];

        for (int i = 0; i < this.largeur; i++) {

            for (int j = 0; j < this.longueur; j++) {

                this.plateau[i][j] = new Case(j, i);

                tuple[0] = i;
                tuple[1] = j;

                for (int k = 0; k < points.size(); k++) {

                    if (points.get(k)[0] == tuple[0] && points.get(k)[1] == tuple[1]) {

                        this.plateau[i][j].setSymbole(symboles.get(k));

                    }

                }

            }

        }

        this.pairesSymboles = points.size() / 2;

        this.plateauOrigine = this.clonePlateau(this.plateau);
        
        this.partieTerminee = false;
        
        // Algo pour déterminer possibilité de placement de deux symboles TO DO
    }

    /**
     * Lit la variable chemins
     *
     * @return la liste des chemins
     */
    public ArrayList<Chemin> getChemins() {
        return chemins;
    }

    /**
     * Ecrit a variable chemins
     *
     * @param chemins La variable membre chemins
     */
    public void setChemins(ArrayList<Chemin> chemins) {
        this.chemins = chemins;
    }

    /**
     * Lit la variable largeur
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Ecrit la variable largeur
     *
     * @param largeur  La variable membre largeur
     */
    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    /**
     * Lit la variable longueur
     *
     * @return la longueur de la grille
     */
    public int getLongueur() {
        return longueur;
    }

    /**
     * Ecrit la variable longueur
     *
     * @param longueur La variable membre longueur
     */
    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    /**
     * Lit la variable plateau
     *
     * @return le plateau
     */
    public Case[][] getPlateau() {
        return plateau;
    }

    /**
     * Ecrit la variable plateau
     *
     * @param plateau La variable membre plateau
     */
    public void setPlateau(Case[][] plateau) {
        this.plateau = plateau;
    }

    /**
     * Lit la variable cheminActuel
     *
     * @return Le chemin en cours
     */
    public Chemin getCheminActuel() {
        return cheminActuel;
    }

    /**
     * Ecrit la variable cheminActuel
     *
     * @param cheminActuel La variable membre cheminActuel
     */
    public void setCheminActuel(Chemin cheminActuel) {
        this.cheminActuel = cheminActuel;
    }

    /**
     * Lit la dernière case du chemin que l'utilisateur trace
     * @return La dernière case du chemin actuel
     */
    public Case getDerniereCaseCheminActuel() {

        return this.cheminActuel.getDernierElement();

    }
    
    /**
     * Lit la première case du chemin que l'ulisateur trace
     * @return La premiere case du chemin actuel
     */
    public Case getPremiereCaseCheminActuel() {
        
        return this.cheminActuel.getPremierElement();
        
    }

    /**
     * 
     * @param y
     * @param x
     * @return Un clone d'une case du tableau en y,x
     */
    public Case getCase(int y, int x) {

        return this.plateau[y][x];

    }
    
    /**
     * Lit la variable partieTerminee
     * @return La variable membre partieTerminee
     */
    public boolean getPartieTerminee() {
        
        return this.partieTerminee;
        
    }
    
    /**
     * Ecrit la variable partieTerminee
     * @param partieTerminee La variable membre partieTerminee
     */
    public void setPartieTerminee(boolean partieTerminee) {
        
        this.partieTerminee = partieTerminee;
        
    }

    /**
     * Supprime le chemin quand on clique dessus
     *
     * @param x L'abscisse de la Case
     * @param y L'ordonnée de la Case
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
     * Commence le drag and drop sur un symbole
     *
     * @param x L'abscisse de la Case
     * @param y L'ordonnée de la Case
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
     * Avance dans le drag and drop pour dessiner un chemin
     *
     * @param x L'abscisse de la Case
     * @param y L'ordonnée de la Case
     */
    public void majDragAndDrop(int x, int y) {

        this.derniereCaseConnue[0] = x;
        
        this.derniereCaseConnue[1] = y;
        
        System.out.println("x: " + x + " y: " + y);

        Case c = this.getCase(y, x);

        //System.out.println(this.cheminActuel.getCases().toString());
        Case casePrecedente = this.getDerniereCaseCheminActuel();

        if ( caseEstLibre(c) && sontVoisines(casePrecedente, c) ) {

            this.cheminActuel.ajouter(c);
            
            this.cheminNonValide = false;
            
            System.out.println("Ajout");

        } else {
            
            this.cheminNonValide = true;
            
        }

        //System.out.println("y: "+ c.getY() +" x: " + c.getX());
        this.setChanged();

        this.notifyObservers();

    }

    /**
     * Arrête le drag and drop quad on arrive sur le deuxieme symbole
     * @param x L'abscisse de la Case
     * @param y L'ordonnée de la Case
     */
    public void finirDragAndDrop( int x, int y ) {

        Case c = this.getCase(this.derniereCaseConnue[1], this.derniereCaseConnue[0]);
        
        if ( c.getSymbole() == this.cheminActuel.getCases().get(0).getSymbole() ){
            
            this.cheminActuel.ajouter(c);
            
            System.out.println(this.cheminActuel.toString());
            
            if (this.cheminEstValide(this.cheminActuel) && this.cheminNonValide == false ){
                
                System.out.println("Chemin Valide");
                
                this.cheminActuel.validerLesCases();
                
                this.chemins.add(this.cheminActuel);
                
                this.cheminActuel = new Chemin();
                
                this.mettreAJourPlateau();
                
                this.pairesCompletes ++;
                
                if ( this.jeuTermine() ){
                    
                    this.partieTerminee = true;
                    
                    System.out.println("Partie finie");
                    
                }
                
            } else {
                
                this.supprimerChemin(this.cheminActuel);
            
                this.cheminActuel = new Chemin();
                
            }
            
        } else {
            
            this.supprimerChemin(this.cheminActuel);
            
            this.cheminActuel = new Chemin();
                
        }
        
        
        this.mettreAJourPlateau();
                
        this.setChanged();

        this.notifyObservers();

    }
    
    /**
     * Réinitialise la grille à son état de départ (mêmes symboles mais tous les liens osnt supprimés)
     */
    public void reinitialiser(){
        
        this.chemins = new ArrayList<>();
        
        this.supprimerChemin(cheminActuel);
        
        this.pairesCompletes = 0;
        
        this.plateau = this.clonePlateau(this.plateauOrigine);
        
        this.partieTerminee = false;
        
        this.setChanged();
        
        this.notifyObservers();
        
    }
    
    /**
     * Annule le dernier coup
     */
    public void annulerDernierCoup() {
        
        this.afficherPlateau();
        
        if (this.chemins.size() > 0){
            
            Chemin chemin = this.chemins.get(this.chemins.size() - 1);
            
            chemin.invaliderLesCases();
            
            this.supprimerChemin(chemin);
            
            //this.majGrilleAvecChemin();
            
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
     * @param c Une Case
     * @return Vrai ou faux
     */
    private boolean caseEstLibre(Case c) {

        return c.getLien() == Lien.VIDE && c.getSymbole() == Symbole.VIDE;

    }

    /**
     * Renvoie vrai si le chemin est valide, faux sinon
     *
     * @return Vrai ou faux
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
     * Renvoie vrai si le tableau est plein, faux sinon
     *
     * @return Vrai ou faux
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
     * @return Vrai ou faux
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
            
            System.out.println("this.pairescompletes = " + this.pairesCompletes);
            
            System.out.println("this.pairesSymboles = " + this.pairesSymboles);

            return false;

        }

        return termine;

    }

    /**
     *
     * @param chemin .
     * @return Vrai ou faux
     */
    public boolean cheminMemePaire(Chemin chemin) {

        return chemin.getCases().get(0).getSymbole() == chemin.getDernierElement().getSymbole();

    }

    /**
     * Supprime un chemin
     *
     * @param chemin le chemin à supprimer
     */
    private void supprimerChemin(Chemin chemin) {

        chemin.supprimer();

    }

    /**
     *
     * @param chemin
     */
    private void mettreAJourPlateau() {

        for ( Chemin chemin : this.chemins ) {
            
            for ( Case c : chemin.getCases() ) {
                
                Case casePlateau = this.plateau[c.getY()][c.getX()];
                
                if ( casePlateau.getSymbole() == Symbole.VIDE ) {
                    
                    casePlateau.setLien( c.getLien() );
                
                }
                
            }
            
        }

    }

    /**
     * Renvoie vrai si c1 et c2 sont voisines, faux sinon
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

    
    public void formaterGrille() {
        
        GenerateurPaires p = new GenerateurPaires(this.longueur, this.largeur, this.pairesSymboles);
        
        ArrayList<int[]> points = p.genererPaires();
        
        ArrayList<Symbole> symboles = p.symbolesPaires;
                
        this.chemins = new ArrayList<>();

        this.plateau = new Case[this.longueur][this.largeur];

        int[] tuple = new int[2];

        for (int i = 0; i < this.largeur; i++) {

            for (int j = 0; j < this.longueur; j++) {

                this.plateau[i][j] = new Case(j, i);

                tuple[0] = i;
                tuple[1] = j;

                for (int k = 0; k < points.size(); k++) {

                    if (points.get(k)[0] == tuple[0] && points.get(k)[1] == tuple[1]) {

                        this.plateau[i][j].setSymbole(symboles.get(k));

                    }

                }

            }

        }

        this.pairesSymboles = points.size() / 2;

        this.plateauOrigine = this.clonePlateau(this.plateau);
        
        this.pairesCompletes = 0;
        
        this.partieTerminee = false;

        this.supprimerChemin(cheminActuel);
        
        this.cheminActuel = new Chemin();
        
        this.setChanged();
        
        this.notifyObservers();
        
    }
    
    /**
     * Clone le plateau
     * @param cases
     * @return Un clone du plateau
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
