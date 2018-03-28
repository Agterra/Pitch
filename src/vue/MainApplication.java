/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.util.Observable;
import java.util.Observer;
import javafx.application.*;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.*;

import model.*;

/**
 *
 * @author agterra
 */
public class MainApplication extends Application {

    // Propriétés de fenêtre
    private int hauteurFenetre;
    
    private int largeurFenetre;
    
    // Elements du modèle
    private Grille grille;

    // Elements graphiques    
    private BorderPane mainBorder;
    
    private GridPane gameGridPane;
    
    private Scene mainScene;
    
    public static void main(String[] args) {
        
        launch(args);
        
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        initialisationDuModele();
        
        initialisationGraphique();
        
        Scene scene = new Scene(this.mainBorder, this.hauteurFenetre, this.largeurFenetre, Color.WHITESMOKE);

        primaryStage.setScene( scene );
        
        primaryStage.setTitle("Jeu triple A");
        
        primaryStage.show();
        
    }
    
    // Initialisation des composantes graphiques
    public void initialisationGraphique () {
        
        this.mainBorder = new BorderPane();
        
        this.gameGridPane = new GridPane();
        
        this.gameGridPane.setGridLinesVisible(true);
        
        this.mainBorder.setCenter( this.gameGridPane );
        
        this.largeurFenetre = 500;
        
        this.hauteurFenetre = 500;
        
        for (int i = 0; i < this.grille.getLongueur(); i++) {
            
            for (int j = 0; j < this.grille.getLargeur(); j++) {
                
                final int y = i;
                
                final int x = j;
                
                Circle cercle = new Circle( (this.largeurFenetre / this.grille.getLargeur())/2 );
                 
                cercle.setOnDragDetected( new EventHandler<MouseEvent>() {
                    
                    @Override
                    public void handle(MouseEvent event) {

                        Dragboard db = cercle.startDragAndDrop(TransferMode.ANY);
                        
                        ClipboardContent content = new ClipboardContent();       
                        
                        content.putString(""); // non utilisé actuellement
                        
                        db.setContent(content);
                        
                        event.consume();
                        
                        grille.startDragAndDrop( x, y);
                    
                    }
                
                });

                cercle.setOnDragEntered(new EventHandler<DragEvent>() {
                    
                    public void handle(DragEvent event) {
                        
                       // grille.( y, x);
                        
                        
                        event.consume();
                    
                    }
                    
                });
                
                cercle.setOnDragDone(new EventHandler<DragEvent>() {
                    
                    public void handle(DragEvent event) {
                        
                        // attention, le setOnDragDone est déclenché par la source du Drag&Drop
                        
                        grille.startDragAndDrop( x, y);
                        
                    }
                    
                });
                
                this.gameGridPane.add(cercle, j, i);
                
            }
            
        }
        
    }
    
      
    // Initialisation du modèle    
    public void initialisationDuModele () {
        
        this.grille = new Grille();
        
        this.grille.addObserver( new Observer() {
            
            @Override
            public void update(Observable o, Object arg) {
                
                
                
            }
            
        });
        
    }
    
}
