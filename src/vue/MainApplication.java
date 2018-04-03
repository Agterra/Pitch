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
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.stage.*;

import model.*;

/**
 *
 * @author agterra
 */
public class MainApplication extends Application {

    public static boolean DISPLAY_DEBUG = true;
    
    // Propriétés de fenêtre
    private int hauteurFenetre;
    
    private int largeurFenetre;
    
    private int ratio;
    
    // Elements du modèle
    private Grille grille;

    // Elements graphiques    
    private BorderPane mainBorder;
    
    private GridPane gameGridPane;
    
    private Scene mainScene;
    
    public static void main(String[] args) {
        
        if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.main");
        
        launch(args);
        
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.start");
        
        initialisationDuModele();
        
        initialisationGraphique();
        
        Scene scene = new Scene(this.mainBorder, this.hauteurFenetre, this.largeurFenetre, Color.WHITESMOKE);

        primaryStage.setScene( scene );
        
        primaryStage.setTitle("Jeu triple A");
        
        primaryStage.show();
        
    }
    
    // Initialisation des composantes graphiques
    public void initialisationGraphique () {
        
        if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.initialisationGraphique");
        
        this.mainBorder = new BorderPane();
        
        this.gameGridPane = new GridPane();
        
        this.gameGridPane.setGridLinesVisible(false);
        
        this.mainBorder.setCenter( this.gameGridPane );
        
        this.largeurFenetre = 500;
        
        this.hauteurFenetre = 500;
        
        this.ratio = this.largeurFenetre / this.grille.getLargeur();
        
        for (int i = 0; i < this.grille.getLongueur(); i++) {
            
            for (int j = 0; j < this.grille.getLargeur(); j++) {
                
                final int y = i;
                
                final int x = j;
                
                Pane pane = new Pane();
                
                pane.setPrefSize(this.ratio, this.ratio);
                
                if(this.grille.getPlateau()[i][j].getSymbole() != Symbole.VIDE) {
                    
                    Circle cercle = new Circle( this.ratio/2-1 , Color.rgb(200,20,20) );
                    
                    cercle.setCenterX(ratio / 2);
                    
                    cercle.setCenterY(ratio / 2);
                    
                    Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
                    
                    pane.setBorder(border);
                    
                    cercle.setOnDragDetected( new EventHandler<MouseEvent>() {
                    
                        @Override
                        public void handle(MouseEvent event) {
        
                            if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.cercle.onDragDetected");

                            Dragboard db = cercle.startDragAndDrop(TransferMode.ANY);

                            ClipboardContent content = new ClipboardContent();       

                            content.putString(""); // non utilisé actuellement

                            db.setContent(content);

                            event.consume();

                            grille.startDragAndDrop( x, y );

                        }

                    });

                    cercle.setOnDragEntered(new EventHandler<DragEvent>() {

                        public void handle(DragEvent event) {
                            
                            if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.cercle.onDragEntered");

                            grille.updateDragAndDropSymbol( x, y );

                            event.consume();

                        }

                    });

                    cercle.setOnDragDone(new EventHandler<DragEvent>() {

                        public void handle(DragEvent event) {

                            // attention, le setOnDragDone est déclenché par la source du Drag&Drop
                            if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.cercle.onDragDone");

                           // grille.stopDragAndDrop( x, y );

                        }

                    });
                
                    pane.getChildren().add(cercle);
                    
                } else {
                    
                    Rectangle rectangle = new Rectangle(this.ratio -1, this.ratio -1, Color.WHITESMOKE);
                    
                    rectangle.setStroke(Color.BLACK);
                    
                    rectangle.setStrokeWidth(1);
                    
                    //evenement quand la souris entre dans le rectangle
                    rectangle.setOnDragEntered(new EventHandler<DragEvent>() {

                        public void handle(DragEvent event) {
                                                        
                            if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.rectangle.onDragEntered");

                            grille.updateDragAndDrop( x, y );
    
                            event.consume();

                            //System.out.println("drague evente enteuraide rectangle");

                        }

                    });
                    
                    rectangle.setOnDragDone(new EventHandler<DragEvent>() {

                        public void handle(DragEvent event) {
                            
                            if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.rectangle.onDragDone");

                            // attention, le setOnDragDone est déclenché par la source du Drag&Drop

                            grille.stopDragAndDrop( x, y );

                            //System.out.println("drague evente donne");

                        }

                    });
                    
                    pane.getChildren().clear();
                    
                    pane.getChildren().add(rectangle);
                    
                }
                
                this.gameGridPane.add(pane, j, i);
                
            }
            
        }
        
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
                                
                                colorCell(gridCase, cheminActuel.get( found ), Color.rgb(200,20,20));
                                
                            } else {
                                
                                // Coloriage spécifique du plateau
                                colorCell(gridCase, c, Color.rgb(20,20,200));
                            
                            }
                            
                        }
                        
                        //System.out.println();
                        
                    }
                    
                }
                
            }
            
        });
        
    }
    
    public void colorCell(Object gridCase, Case c, Paint color) {
                
        //if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.colorCell");

        if ( c.getSymbole() != Symbole.VIDE ) {
                                
            // Modify Circles

        } else if ( c.getLien() != Lien.VIDE ) {

            // Modify rectangles with Correct lien

            //System.out.println(updatedCell.getLien().toString());

            if ( gridCase instanceof Pane ) {

                Pane pane = (Pane)gridCase;

                Rectangle rectangle = (Rectangle) pane.getChildren().get(0);

                rectangle.setFill(color);

                rectangle.setStroke(Color.BLACK);

                rectangle.setStrokeWidth(1);

            }

        } else {  // Coloriage par défaut

            if ( gridCase instanceof Pane ) {

                Pane pane = (Pane)gridCase;

                Rectangle rectangle = (Rectangle) pane.getChildren().get(0);

                rectangle.setFill(Color.WHITE);

                rectangle.setStroke(Color.BLACK);

                rectangle.setStrokeWidth(1);

            }

        }
        
    }
    
}
