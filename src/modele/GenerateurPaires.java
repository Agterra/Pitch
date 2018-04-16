package modele;

import java.util.ArrayList;
import java.util.Random;


public class GenerateurPaires {

    /**************************************************************************/
    // VARIABLES MEMBRES
    /**************************************************************************/
    
    private ArrayList< int[]> paires;
    
    public ArrayList< Symbole > symbolesPaires;

    private int[][] grille;

    private int longueur;

    private int largeur;

    private int nombrePaires;

    /**************************************************************************/
    // FONCTIONS MEMBRES
    /**************************************************************************/
    
    /**************************************************************************/
    // CONSTRUCTEURS
    /**************************************************************************/
    
    /**
     * Initialise les paramètres à leurs valeurs par défaut
     */
    public GenerateurPaires() {

        this.longueur = 0;

        this.largeur = 0;

        this.paires = new ArrayList<>();

        this.symbolesPaires = new ArrayList<>();

        this.grille = new int[this.longueur][this.largeur];

        this.nombrePaires = 0;

    }

    /**
     * Initialise les paramètres avec des valeurs
     * 
     * @param longueur Le nombre de lignes
     * @param largeur Le nombre de colonnes
     * @param nombrePaires Le nombre de paires à générer
     */
    public GenerateurPaires(int longueur, int largeur, int nombrePaires) {

        this.longueur = longueur;

        this.largeur = largeur;

        this.paires = new ArrayList<>();

        this.symbolesPaires = new ArrayList<>();

        this.grille = new int[longueur][largeur];

        this.nombrePaires = nombrePaires;

        for (int i = 0; i < this.longueur; i++) {

            for (int j = 0; j < this.largeur; j++) {

                this.grille[i][j] = -1;

            }

        }

    }

    /**************************************************************************/
    // FONCTIONS GENERATEUR
    /**************************************************************************/
    
    /**
     * Génère un tableau contenant des paires
     * @return Une liste de paires
     */
    public ArrayList< int[]> genererPaires() {

        placerPointsAleatoirement();

        /*Random rand = new Random();
		
		int[][] gridCopy = this.grid;
		
		while (!this.estPlein(gridCopy)) {
		
			for (int i = 0; i < this.longueur; i++) {

				for (int j = 0; j < this.largeur; j++) {

					int value = gridCopy[i][j];

					if(value != -1) {

						int x = rand.nextInt(3) - 1;

						int y = rand.nextInt(3) - 1;

						while ((j + x == j && i + y == i) 
								|| 
								(j + x < 0)
								|| 
								(j + x >= this.largeur)
								|| 
								(i + y < 0)
								|| 
								(i + y >= this.longueur)
								|| 
								(gridCopy[i + y][j + x] != -1)) {

							x = rand.nextInt(3) - 1;

							y = rand.nextInt(3) - 1;
							
						}

						System.out.println("base : "+ j + "," + i + " new x = " + (j + x) + " new y = " + (i + y));
						
						gridCopy[i + y][j + x] = value;

					}

				}

			}
			
			this.afficherTableau(gridCopy);
			
			try{Thread.sleep(500);}catch(InterruptedException e) {}
			
		}*/
        return this.paires;

    }
    
    public ArrayList< int[]> genererPaires(int nombrePairs) {
        
        // Pas encore implémenté
        
        this.placerPointsAleatoirement();
      
        return this.paires;
        
    }


    /**
     * Place les paires aléatoirement sur la grille
     */
    private void placerPointsAleatoirement() {

        Random aleatoire = new Random();

        Symbole[] symboles = Symbole.getSymbolesNormaux();
        
        int[] points = new int[2];

        for (int i = 0; i < this.nombrePaires; i++) {

            int symboleAleatoire = aleatoire.nextInt(symboles.length);
            
            Symbole s = symboles[symboleAleatoire];
            
            int x = aleatoire.nextInt(this.largeur);

            int y = aleatoire.nextInt(this.longueur);

            while (this.grille[y][x] != -1) {

                x = aleatoire.nextInt(this.largeur);

                y = aleatoire.nextInt(this.longueur);

            }

            this.grille[y][x] = i;

            points = new int[2];

            points[0] = y;

            points[1] = x;

            this.paires.add(points);
            
            this.symbolesPaires.add(s);

            int nX = aleatoire.nextInt(this.largeur);

            int nY = aleatoire.nextInt(this.longueur);

            while ((nX == x && nY == y) || this.grille[nY][nX] != -1 && (Math.abs(nX - x) == 0 && Math.abs(nY - y) == 0)) {

                nX = aleatoire.nextInt(this.largeur);

                nY = aleatoire.nextInt(this.longueur);

            }

            this.grille[nY][nX] = i;

            points = new int[2];

            points[0] = nY;

            points[1] = nX;

            this.paires.add(points);

            this.symbolesPaires.add(s);

        }
        /*  
		for (int i = 0; i < this.pairs.size(); i++) {
			
			System.out.println(this.pairs.get(i)[0] +" " + this.pairs.get(i)[1]);
			
		}
		
		this.afficherTableau(this.grid);
		
		try{Thread.sleep(5000);}catch(InterruptedException e) {}
         */
    }

    /**
     * Affiche le tableau des paires générées
     * @param tableau Le tableau de paires à afficher
     */
    public void afficherTableau(int[][] tableau) {

        for (int i = 0; i < tableau.length; i++) {

            for (int j = 0; j < tableau[i].length; j++) {

                System.out.print("[" + tableau[i][j] + "]");

            }

            System.out.println();

        }

    }
    
    /**************************************************************************/
    // FONCTIONS BOOLEENNES
    /**************************************************************************/
    
    /**
     * Renvoie vrai si le tableau est plein, faux sinon
     * @return Vrai ou faux
     */
    private boolean estPlein(int[][] tableau) {

        for (int i = 0; i < tableau.length; i++) {

            for (int j = 0; j < tableau[i].length; j++) {

                if (tableau[i][j] == -1) {

                    return false;

                }

            }

        }

        return true;

    }

}
