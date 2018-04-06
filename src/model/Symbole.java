package model;

import java.util.ArrayList;
import javafx.scene.image.Image;


public enum Symbole {

    ROND("file:src/assets/circle.png"),
    TRIANGLE("file:src/assets/triangle.png"),
    ETOILE("file:src/assets/star.png"),
    CARRE("file:src/assets/square.png"),
    LOSANGE("file:src/assets/losange.png"),
    
    ROND_VALIDE("file:src/assets/circle_valide.png"),
    TRIANGLE_VALIDE("file:src/assets/triangle_valide.png"),
    ETOILE_VALIDE("file:src/assets/star_valide.png"),
    CARRE_VALIDE("file:src/assets/square_valide.png"),
    LOSANGE_VALIDE("file:src/assets/losange_valide.png"),
    VIDE("file:src/assets/vide.png");

    private final Image image;

    private Symbole(String imagePath) {

        this.image = new Image(imagePath);

    }

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
    public static Symbole getOpposite(Symbole symbole) {

        Symbole s = VIDE;

        switch (symbole) {

            case ETOILE:

                s = ETOILE_VALIDE;

                break;

            case ETOILE_VALIDE:

                s = ETOILE;

                break;

            case ROND:

                s = ROND_VALIDE;

                break;

            case ROND_VALIDE:

                s = ROND;

                break;

            case TRIANGLE:

                s = TRIANGLE_VALIDE;

                break;

            case TRIANGLE_VALIDE:

                s = TRIANGLE;

                break;

            case CARRE:

                s = CARRE_VALIDE;

                break;

            case CARRE_VALIDE:

                s = CARRE;

                break;

            case LOSANGE:

                s = LOSANGE_VALIDE;

                break;

            case LOSANGE_VALIDE:

                s = LOSANGE;

                break;

            default:

                break;

        }

        return s;

    }

    /**
     *
     * @return .
     */
    public static Symbole getRandomSymbole() {

        return Symbole.VIDE;

    }
    
    public static Symbole[] getSymbolesNormaux () {
        
        Symbole[] symboles = new Symbole[5];
        
        for ( int i = 0; i < 5 ; i++ ){
            
            symboles[i] = Symbole.values()[i];
            
        }
        
        return symboles;
        
    }
    
}
