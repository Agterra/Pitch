/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import model.*;

/**
 *
 * @author agterra
 */
public class MainApplication extends Application {

    ///////////////////////////////////////////
    // VARIABLES MEMBRES
    ///////////////////////////////////////////
    
    public static boolean DISPLAY_DEBUG = true;
    
    // Propriétés de fenêtre
    private int hauteurFenetre;
    
    private int largeurFenetre;
    
    private int ratio;
    
    // Elements du modele
    private Grille grille;

    // Elements graphiques du menu
    private BorderPane secondBorderPane;
    
    private Scene secondScene;
    
    // Elements graphiques du jeu 
    private BorderPane mainBorder;
    
    private GridPane gameGridPane;
    
    private Scene mainScene;
    
    ///////////////////////////////////////////
    // FONCTIONS MEMBRES
    ///////////////////////////////////////////
    
    public static void main(String[] args) {
        
        if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.main");
        
        launch(args);
        
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.start");
        
        initialisationDuModele();
        
        initialisationGraphique(primaryStage);
        
        Scene scene = new Scene(this.mainBorder, this.hauteurFenetre, this.largeurFenetre, Color.WHITESMOKE);

        primaryStage.setScene( scene );
        
        primaryStage.setTitle("Jeu triple A");
        
        primaryStage.show();
        
    }
    
    // Initialisation des composantes graphiques
    public void initialisationGraphique (Stage primaryStage) {
        
        if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.initialisationGraphique");
        
        this.mainBorder = new BorderPane();
        
        this.gameGridPane = new GridPane();
        
        this.gameGridPane.setGridLinesVisible(false);
        
        this.mainBorder.setCenter( this.gameGridPane );
        
        this.largeurFenetre = 500;
        
        this.hauteurFenetre = 500;
        
        this.ratio = this.largeurFenetre / this.grille.getLargeur();
        
        initialiserMenu(primaryStage);
        
        
        //ajouter le menu
        Menu menu1 = new Menu("Rejouer");
        
        Menu menu2 = new Menu("Règles");

        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().add(menu1);
        
        menuBar.getMenus().add(menu2);
        
        VBox vBox = new VBox(menuBar);

        Scene scene = new Scene(vBox, 100, 100);
        
        this.mainBorder.setTop(menuBar);
 
        
        for (int i = 0; i < this.grille.getLongueur(); i++) {
            
            for (int j = 0; j < this.grille.getLargeur(); j++) {
                
                final int y = i;
                
                final int x = j;
                
                
                Pane pane = new Pane();
                
                pane.setPrefSize(this.ratio, this.ratio);
                
                Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

                pane.setBorder(border);
                    
                
                ImageView image = new ImageView();
                
                image.setFitHeight(ratio - 1);
                
                image.setFitWidth(ratio - 1);
                
                if(this.grille.getCase(y, x).getSymbole() != Symbole.VIDE) {
                    
                    image.setImage(this.grille.getCase(y, x).getSymbole().getImage());
                    
                } else {
                    
                    image.setImage(this.grille.getCase(y, x).getLien().getImage());
                    
                }

                image.setOnDragDetected( new EventHandler<MouseEvent>() {
                    
                    @Override
                    public void handle(MouseEvent event) {

                        if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.image.onDragDetected");

                        Dragboard db = image.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();       

                        content.putString(""); // non utilisé actuellement

                        db.setContent(content);

                        event.consume();

                        grille.startDragAndDrop( x, y );

                    }

                });

                image.setOnDragEntered(new EventHandler<DragEvent>() {

                    public void handle(DragEvent event) {

                        if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.image.onDragEntered");

                        event.consume();

                        grille.updateDragAndDrop( x, y );

                    }

                });

                image.setOnDragDone(new EventHandler<DragEvent>() {

                    public void handle(DragEvent event) {

                        // attention, le setOnDragDone est déclenché par la source du Drag&Drop
                        if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.image.onDragDone");

                        grille.stopDragAndDrop( x, y );

                    }

                });
                    
                pane.getChildren().add(image);
               
                this.gameGridPane.add(pane, j, i);
                
            }
            
        }
        
    }
    
    public void initialiserMenu(Stage primaryStage) {
        
        if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.initialiserMenu");
        
        Stage secondStage = new Stage();
        
        secondStage.setTitle("Second Stage");

        // Set position of second window, related to primary window.
        secondStage.setX(primaryStage.getX());
        secondStage.setY(primaryStage.getY());
        secondStage.setWidth(this.largeurFenetre);
        secondStage.setHeight(this.hauteurFenetre);

        Button boutonNouvellePartie = new Button();
        boutonNouvellePartie.setText("Nouvelle partie");

        Button boutonRegles = new Button();
        boutonRegles.setText("Règles");

        VBox buttons = new VBox(boutonNouvellePartie, boutonRegles);

        secondStage.show();

    }
    
    // Initialisation du modèle    
    public void initialisationDuModele () {
                
        if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.initialisationDuModele");

        this.grille = new Grille();
        
        this.grille.addObserver( new Observer() {
            
            @Override
            public void update(Observable o, Object arg) {
                
                //System.out.println("update: " + arg );
                
                if (o instanceof Grille){
                                        
                    Grille grille = (Grille) o;
                    
                    ArrayList<Case> cheminActuel = grille.getCheminActuel().getCases();
                    
                    //System.out.println(cheminActuel.toString());
                    
                    for (int i = 0; i < grille.getLongueur() ; i++) {
                        
                        for (int j = 0 ; j < grille.getLargeur() ; j++) {
                            
                            Object gridCase = gameGridPane.getChildren().get( i * grille.getLargeur() + j);
                            
                            Case c = grille.getCase(i,j);
                            
                            int found = cheminActuel.indexOf( c );
                            
                            // Coloriage spécifique du chemin
                            if( found != -1 ) { 
                                
                                colorCell(gridCase, cheminActuel.get( found ));
                                
                            } else {
                                
                                // Coloriage spécifique du plateau
                                colorCell(gridCase, c);
                            
                            }
                            
                        }
                        
                        //System.out.println();
                        
                    }
                    
                }
                
            }
            
        });
        
    }
    
    public void colorCell(Object gridCase, Case c) {
                
        //if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.colorCell");

        if ( c.getSymbole() != Symbole.VIDE ) {
                                
            if ( gridCase instanceof Pane ) {

                Pane pane = (Pane)gridCase;

                ImageView image = (ImageView) pane.getChildren().get(0);

                image.setImage( c.getSymbole().getImage() );
                
            }
            
        } else {

            // Modify rectangles with Correct lien

            //System.out.println(updatedCell.getLien().toString());

            if ( gridCase instanceof Pane ) {

                Pane pane = (Pane)gridCase;

                ImageView image = (ImageView) pane.getChildren().get(0);

                image.setImage( c.getLien().getImage() );
                
            }

        }
        
    }
    
}
