package model;

import javafx.scene.image.Image;

public enum Lien {

    VERTICAL("file:src/assets/vertical.png"),
    VERTICAL_VALIDE("file:src/assets/vertical_valide.png"),
    HORIZONTAL("file:src/assets/horizontal.png"),
    HORIZONTAL_VALIDE("file:src/assets/horizontal_valide.png"),
    COUDE_HAUT_DROIT("file:src/assets/coude_haut_droit.png"),
    COUDE_HAUT_DROIT_VALIDE("file:src/assets/coude_haut_droit_valide.png"),
    COUDE_HAUT_GAUCHE("file:src/assets/coude_haut_gauche.png"),
    COUDE_HAUT_GAUCHE_VALIDE("file:src/assets/coude_haut_gauche_valide.png"),
    COUDE_BAS_DROIT("file:src/assets/coude_bas_droit.png"),
    COUDE_BAS_DROIT_VALIDE("file:src/assets/coude_bas_droit_valide.png"),
    COUDE_BAS_GAUCHE("file:src/assets/coude_bas_gauche.png"),
    COUDE_BAS_GAUCHE_VALIDE("file:src/assets/coude_bas_gauche_valide.png"),
    VIDE("file:src/assets/vide.png"),
    VIDE_INVALIDE("file:src/assets/vide_invalide.png");

    private final Image image;

    private Lien(String chemin) {

        this.image = new Image(chemin);

    }

    /**
     * Lit la variable image
     * @return La variable membre image
     */
    public Image getImage() {

        return this.image;

    }

    /**
     * Renvoie l'opposé du lien (lien en fond vert)
     *
     * @param lien Un Lien
     * @return L'opposé du lien
     */
    public static Lien getLienValide(Lien lien) {

        Lien l = VIDE;

        switch (lien) {

            case VERTICAL:

                l = VERTICAL_VALIDE;

                break;

            case HORIZONTAL:

                l = HORIZONTAL_VALIDE;

                break;

            case COUDE_HAUT_DROIT:

                l = COUDE_HAUT_DROIT_VALIDE;

                break;

            case COUDE_HAUT_GAUCHE:

                l = COUDE_HAUT_GAUCHE_VALIDE;

                break;

            case COUDE_BAS_DROIT:

                l = COUDE_BAS_DROIT_VALIDE;

                break;

            case COUDE_BAS_GAUCHE:

                l = COUDE_BAS_GAUCHE_VALIDE;

                break;

            case VIDE_INVALIDE:

                l = VIDE;

                break;

            default:
                
                l = lien;

                break;

        }

        return l;

    }
    
    public static Lien getLienInvalide( Lien lien ) {
        
        Lien l = VIDE;

        switch (lien) {

            case VERTICAL_VALIDE:

                l = VERTICAL;

                break;

            case HORIZONTAL_VALIDE:

                l = HORIZONTAL;

                break;

            case COUDE_HAUT_DROIT_VALIDE:

                l = COUDE_HAUT_DROIT;

                break;

            case COUDE_HAUT_GAUCHE_VALIDE:

                l = COUDE_HAUT_GAUCHE;

                break;

            case COUDE_BAS_DROIT_VALIDE:

                l = COUDE_BAS_DROIT;

                break;
                
            case COUDE_BAS_GAUCHE_VALIDE:

                l = COUDE_BAS_GAUCHE;

                break;

            case VIDE:

                l = VIDE_INVALIDE;

                break;

            default:

                l = lien;
                
                break;

        }

        return l;

        
    }

}
