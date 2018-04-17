package modele;

import java.util.ArrayList;
import javafx.scene.image.Image;


public enum Symbole {

    /**************************************************************************/
    // VARIABLES MEMBRES
    /**************************************************************************/
    
    ROND("file:src/ressources/circle.png"),
    TRIANGLE("file:src/ressources/triangle.png"),
    ETOILE("file:src/ressources/star.png"),
    CARRE("file:src/ressources/carre.png"),
    LOSANGE("file:src/ressources/losange.png"),
    
    ROND_VALIDE("file:src/ressources/circle_valide.png"),
    TRIANGLE_VALIDE("file:src/ressources/triangle_valide.png"),
    ETOILE_VALIDE("file:src/ressources/star_valide.png"),
    CARRE_VALIDE("file:src/ressources/carre_valide.png"),
    LOSANGE_VALIDE("file:src/ressources/losange_valide.png"),
    VIDE("file:src/ressources/vide.png");

    private final Image image;

    /**************************************************************************/
    // FONCTIONS MEMBRES
    /**************************************************************************/
    
    /**************************************************************************/
    // CONSTRUCTEURS
    /**************************************************************************/
    
    /**
     * Initialise les paramètres avec des valeurs
     * 
     * @param cheminImage Le chemin de symbole à afficher
     */
    private Symbole(String cheminImage) {

        this.image = new Image(cheminImage);

    }

    /**************************************************************************/
    // ACCESSEURS & MUTATEURS
    /**************************************************************************/
    
    /**
     * Lit la variable image
     * 
     * @return La variable membre image
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Renvoie le symbole valide (symbole en fond blanc)
     * 
     * @param symbole Un Symbole
     * 
     * @return Le symbole
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
     * Renvoie l'opposé du symbole (symbole en fond vert)
     * 
     * @param symbole Un Symbole
     * 
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
     * 
     * @return Un symbole aléatoire
     */
    public static Symbole getRandomSymbole() {
        return Symbole.VIDE;
    }
    
    /**
     * Retourne tous les symboles valides.
     * 
     * @return Un tableau de Symboles
     */
    public static ArrayList< Symbole > getSymbolesNormaux () {
        
        ArrayList< Symbole >  symboles = new ArrayList<>(5);
        
        for (int i = 0; i < 5 ; i++) {
            
            symboles.add( Symbole.values()[i] );
            
        }
        
        return symboles;
        
    }
    
}
