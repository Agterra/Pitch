package controleur;

import controleur.IDActions;
import static controleur.IDActions.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import modele.Case;
import modele.Grille;
import vue.ApplicationPrincipale;


public class Controleur implements EventHandler {
    
    /**************************************************************************/
    // VARIABLES MEMBRES
    /**************************************************************************/
    
    private Grille grille;
    
    private ApplicationPrincipale application;
    
    /**************************************************************************/
    // FONCTIONS MEMBRES
    /**************************************************************************/
    
    /**************************************************************************/
    // CONSTRUCTEURS
    /**************************************************************************/
    
    /**
     * Initialise les paramètres avec des valeurs
     * 
     * @param application L'application
     * @param grille La grille du jeu en cours
     */
    public Controleur (ApplicationPrincipale application, Grille grille) {
        
        this.application = application;
        
        this.grille = grille;
        
    }
    
    /**
     * Gère les actions utilisateurs
     * 
     * @param event Un évènement utilisateur
     */
    @Override
    public void handle(Event event) {
        
        event.consume();
        
        Object source = event.getSource();
        
        IDActions ID = NONE;
        
        int x = -1, y = -1;
        
        if(event instanceof ActionEvent) {
            
            if(source instanceof Button) {
                
                Button bouton = (Button)source;
            
                String stringID = bouton.getId();

                ID = IDActions.valueOf(stringID);
            
            } else if (source instanceof MenuItem) {
                
                MenuItem item = (MenuItem) source;
                
                String stringID = item.getId();

                ID = IDActions.valueOf(stringID);
             
            }
            
        } else if (event instanceof MouseEvent) {
            
            MouseEvent mouseEvent = (MouseEvent) event;
            
            EventType type = mouseEvent.getEventType();
            
            ID = IDActions.valueOf(type.getName());
            
            String[] stringID = ((ImageView)source).getId().split(",");
            
            x = Integer.parseInt(stringID[0]);
            
            y = Integer.parseInt(stringID[1]);
            
        } else if (event instanceof DragEvent) {
            
            DragEvent dragEvent = (DragEvent) event;
            
            EventType type = dragEvent.getEventType();
            
            ID = IDActions.valueOf(type.getName());
            
            String[] stringID = ((ImageView)source).getId().split(",");
            
            x = Integer.parseInt(stringID[0]);
            
            y = Integer.parseInt(stringID[1]);
            
        }
        
        switch(ID) {

            case MENU_ACCUEIL_NOUVELLE_PARTIE :

                this.application.actionBoutonMenuPrincipalNouvellePartie();

                break;

            case MENU_ACCUEIL_MODIFIER_TAILLE :

                this.application.actionBoutonMenuPrincipalModifierTaille();
                
                break;

            case MENU_ACCUEIL_REGLES : 

                this.application.actionBoutonMenuPrincipalRegles();

                break;

            case MENU_ACCUEIL_QUITTER :

                this.application.actionBoutonMenuPrincipalQuitter();

                break;
                
            case MENU_JEU_ANNULER_DERNIER_COUP :
                
                this.grille.annulerDernierCoup();

                break;

            case MENU_JEU_RECOMMENCER :
                
                this.grille.reinitialiser();

                break;
                
            case MENU_JEU_RECOMMENCER_NOUVELLE_PARTIE :

                this.grille.formaterGrille();

                this.application.initialiserJeu();

                break;
                
            case MENU_JEU_QUITTER : 
                
                this.grille.formaterGrille();
                
                this.application.initialiserJeu();
                
                this.application.actionBoutonMenuJeuQuitter();
                
                break;
                
            case DRAG_DETECTED :
                
                this.application.startDragAndDrop((ImageView)source);
                
                this.grille.commencerDragAndDrop(x, y);

                break;
                
            case MOUSE_CLICKED :
                
                this.grille.clic(x, y);
                
                break;
                
            case DRAG_ENTERED :
                
                this.grille.majDragAndDrop(x, y);
                
                break;
                
            case DRAG_DONE : 
                
                this.grille.finirDragAndDrop(x, y);
                
            default : 

                break;

        }
        
    }
    
    /**
     * Met à jour la taille de la grille à partir de la saisie utilisateur
     * @param nouvelleTaille La taille souhaitée par l'utilisateur
     */
    public void mettreAJourTailleGrille(int nouvelleTaille) {
        
        this.grille = new Grille(nouvelleTaille, nouvelleTaille, (int)Math.floor(nouvelleTaille/2));
        
        this.application.grille = this.grille;
        
        this.application.grille.addObserver( this.application );
        
    }
    
    /**
     * Gère la fin de partie quand l'utilisateur veut en commencer une nouvelle
     */
    public void messageFinOk() {
        
        this.grille.formaterGrille();
        
        this.application.grille = this.grille;
            
        this.application.grille.addObserver( this.application );
        
        this.application.initialisationModele();
        
        this.application.initialiserJeu();
              
    }

    /**
     * Gère la fin de partie quand l'utilisateur ne veut pas en commencer une nouvelle
     */
    public void messageFinCancel() {
        
        this.application.retourMenuPrincipal();
        
    }
    
}
