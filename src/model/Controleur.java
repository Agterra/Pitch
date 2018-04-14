/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import vue.ApplicationPrincipale;
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
import vue.MainApplication;

/**
 *
 * @author agterra
 */
public class Controleur implements Observer, EventHandler {
    
    private Grille grille;
    
    private ApplicationPrincipale application;

    public Controleur ( ApplicationPrincipale application, Grille grille ){
        
        this.application = application;
        
        this.grille = grille;
        
    }
    
    @Override
    public void handle(Event event) {
        
        event.consume();
        
        Object source = event.getSource();
        
        IDActions ID = NONE;
        
        int x = -1, y = -1;
        
        if( event instanceof ActionEvent ) {
            
            if( source instanceof Button ) {
                
                Button bouton = (Button)source;
            
                String stringID = bouton.getId();

                ID = IDActions.valueOf(stringID);
            
            } else if ( source instanceof MenuItem ) {
                
                MenuItem item = (MenuItem) source;
                
                String stringID = item.getId();

                ID = IDActions.valueOf(stringID);
             
            }
            
        } else if ( event instanceof MouseEvent ) {
            
            MouseEvent mouseEvent = (MouseEvent) event;
            
            EventType type = mouseEvent.getEventType();
            
            ID = IDActions.valueOf( type.getName() );
            
            String[] stringID = ((ImageView)source).getId().split(",");
            
            x = Integer.parseInt(stringID[0]);
            
            y = Integer.parseInt(stringID[1]);
            
        } else if ( event instanceof DragEvent ) {
            
            DragEvent dragEvent = (DragEvent) event;
            
            EventType type = dragEvent.getEventType();
            
            ID = IDActions.valueOf( type.getName() );
            
            String[] stringID = ((ImageView)source).getId().split(",");
            
            x = Integer.parseInt(stringID[0]);
            
            y = Integer.parseInt(stringID[1]);
            
        }
        
        switch( ID ) {

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
    
    public void mettreAJourTailleGrille( int nouvelleTaille ){
        
        this.grille = new Grille( nouvelleTaille, nouvelleTaille, (int)Math.floor(nouvelleTaille/2));
        
        this.application.grille = this.grille;
        
    }
    
    public void messageFinOk() {
        
        this.grille.formaterGrille();
        
        this.application.grille = this.grille;
            
        this.application.initialisationModele();
        
        this.application.initialiserJeu();
              
    }

    public void messageFinCancel() {
        
        this.application.retourMenuPrincipal();
        
    }
    
    @Override
    public void update(Observable o, Object arg) {
        
        if (o instanceof Grille) {

            Grille grille = (Grille) o;

            ArrayList<Case> cheminActuel = grille.getCheminActuel().getCases();

            if (MainApplication.DEBUGAGE) {

                System.out.println(cheminActuel.toString());

            }


            //Dessiner chemin actuel
            for (Case c : cheminActuel) {

                this.application.colorierCase( c );

            }

            for (int i = 0; i < grille.getLongueur(); i++) {

                for (int j = 0; j < grille.getLargeur(); j++) {

                    Case c = grille.getCase(i, j);

                    this.application.colorierCase( c );

                }

            }

            if(grille.getPartieTerminee() == 0 || grille.getPartieTerminee() == 1) {

                this.application.afficherMessageDeFin();
                
            }

        }

    }
    
}
