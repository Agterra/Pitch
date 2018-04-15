package model;

import java.util.ArrayList;
import javafx.scene.image.Image;


public enum Symbole {

    /**************************************************************************/
    // VARIABLES MEMBRES
    /**************************************************************************/
    
    ROND("file:src/assets/circle.png"),
    TRIANGLE("file:src/assets/triangle.png"),
    ETOILE("file:src/assets/star.png"),
    CARRE("file:src/assets/carre.png"),
    LOSANGE("file:src/assets/losange.png"),
    
    ROND_VALIDE("file:src/assets/circle_valide.png"),
    TRIANGLE_VALIDE("file:src/assets/triangle_valide.png"),
    ETOILE_VALIDE("file:src/assets/star_valide.png"),
    CARRE_VALIDE("file:src/assets/carre_valide.png"),
    LOSANGE_VALIDE("file:src/assets/losange_valide.png"),
    VIDE("file:src/assets/vide.png");

    private final Image image;

    /**************************************************************************/
    // FONCTIONS MEMBRES
    /**************************************************************************/
    
    /**************************************************************************/
    // CONSTRUCTEURS
    /**************************************************************************/
    
    private Symbole(String cheminImage) {

        this.image = new Image(cheminImage);

    }

    /**************************************************************************/
    // ACCESSEURS & MUTATEURS
    /**************************************************************************/
    
    /**
     * Lit la variable image
     * @return La variable membre image
     */
    public Image getImage() {

        return this.image;

    }

    /**
     * Renvoie l'opposé du lien (symbole en fond vert)
     * @param symbole Un Symbole
     * @return L'opposé du symbole
     */
    public static Symbole getSymboleValide(Symbole symbole) {

        Symbole s = VIDE;

        switch (symbole) {

            case ETOILE:

                s = ETOILE_VALIDE;

                break;

            case ROND:

                s = ROND_VALIDE;

                break;

            case TRIANGLE:

                s = TRIANGLE_VALIDE;

                break;

            case CARRE:

                s = CARRE_VALIDE;

                break;

            case LOSANGE:

                s = LOSANGE_VALIDE;

                break;

            default:
                
                s = symbole;

                break;

        }

        return s;

    }

    /**
     * Renvoie l'opposé du lien (symbole en fond vert)
     * @param symbole Un Symbole
     * @return L'opposé du symbole
     */
    public static Symbole getSymboleInvalide(Symbole symbole) {

        Symbole s = VIDE;

        switch (symbole) {

            case ETOILE_VALIDE:

                s = ETOILE;

                break;

            case ROND_VALIDE:

                s = ROND;

                break;

            case TRIANGLE_VALIDE:

                s = TRIANGLE;

                break;

            case CARRE_VALIDE:

                s = CARRE;

                break;
                
            case LOSANGE_VALIDE:

                s = LOSANGE;

                break;

            default:

                s = symbole;
                
                break;

        }

        return s;

    }
    
    /**
     * Retourne un symbole aléatoire
     * @return Un symbole aléatoire
     */
    public static Symbole getRandomSymbole() {

        return Symbole.VIDE;

    }
    
    /**
     * 
     * @return .
     */
    public static Symbole[] getSymbolesNormaux () {
        
        Symbole[] symboles = new Symbole[5];
        
        for (int i = 0; i < 5 ; i++) {
            
            symboles[i] = Symbole.values()[i];
            
        }
        
        return symboles;
        
    }
    
}
