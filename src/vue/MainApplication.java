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
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    
    private double ratio;
    
    // Elements du modele
    private Grille grille;

    // Element principal
    private BorderPane racine1;

    // Elements graphiques du menu
    private Scene primaryScene;
        
    // Elements graphiques du jeu 
    
    private Stage secondStage;
    
    private BorderPane racine2;
    
    private GridPane gameGridPane;
    
    private Scene secondScene;
    
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
        
        initialiserVariablesMembres();
        
        initialisationDuModele();
        
        initialisationGraphique(primaryStage, this.secondStage);
        
    }
    
    // Initialisation des variables membres
    public void initialiserVariablesMembres() {
        
        this.hauteurFenetre = 525;

        this.largeurFenetre = 500;
        
        this.grille = new Grille();

        this.ratio = Math.floor(this.largeurFenetre / this.grille.getLargeur());
        
        this.racine1 = new BorderPane();

        this.primaryScene = new Scene(this.racine1, this.largeurFenetre, this.hauteurFenetre);
        
        this.secondStage = new Stage();

        this.racine2 = new BorderPane();

        this.gameGridPane = new GridPane();
        
        this.gameGridPane.setGridLinesVisible(false);
        
        this.secondScene = new Scene(this.racine2, this.largeurFenetre, this.hauteurFenetre);
        
    }

    // Initialisation des composantes graphiques
    public void initialisationGraphique (Stage primaryStage, Stage secondStage) {
        
        if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.initialisationGraphique");
        
        initialiserMenu(primaryStage, secondStage);
        
        initialiserJeu(primaryStage, secondStage);
        
    }
    
    public void initialiserMenu(Stage primaryStage, Stage secondStage) {
                
        primaryStage.setTitle("Jeu triple A");
        
        // on positionne la fenetre
        primaryStage.setX(200);
        primaryStage.setY(200);
        primaryStage.setWidth(this.largeurFenetre);
        primaryStage.setHeight(this.hauteurFenetre);
        
        Label titre = new Label("Javaline");

        titre.setFont(Font.font("Verdana", 42));

        Button boutonNouvellePartie = new Button();
        
        boutonNouvellePartie.setText("Nouvelle partie");
        
        boutonNouvellePartie.setMaxWidth(200);
        
        boutonNouvellePartie.setFont(Font.font("Verdana", 16));
        
        boutonNouvellePartie.setOnAction(new EventHandler<ActionEvent>() {
        
            @Override
            public void handle(ActionEvent event) {
                
                primaryStage.close(); // on ferme la fenetre de menu
                
                secondStage.show(); // on affiche la nouvelle partie
                
            }
        
        });

        Button boutonRegles = new Button();
        
        boutonRegles.setText("Règles");
        
        boutonRegles.setMaxWidth(200);
        
        boutonRegles.setFont(Font.font("Verdana", 16));
        
        boutonRegles.setOnAction(new EventHandler<ActionEvent>() {
            
            int nombreClics = 0;
            
            @Override
            public void handle(ActionEvent event) {
                
                nombreClics = nombreClics + 1;
                
                TextArea regles = new TextArea("Glissez votre souris pour connecter deux symboles identiques (créant ainsi un tuyau). Le but est de connecter toutes les paires et d'utiliser toutes les cases du tableau. Mais attention, les tuyaux se briseront s'ils se croisent ou se chevauchent!");
                
                regles.setFont(Font.font("Verdana", 16));

                regles.setWrapText(true);

                racine1.setBottom(regles); // /!\ ne pas mettre this.racine1 sinon on fait reference a la fonction anonyme
                    
                if(nombreClics%2 == 0) { // on a re-clique sur le bouton donc on enleve les regles
                    
                    racine1.getChildren().remove(regles);
                    
                }
                
            }
        
        });
        
        
        VBox menu = new VBox(titre, boutonNouvellePartie, boutonRegles); // on cree un menu vertical contenant les boutons
        
        menu.setSpacing(20);
        
        menu.setAlignment(Pos.CENTER); // on centre les boutons
        
        this.racine1.setCenter(menu); // on les ajoute au centre du BorderPane
        
        primaryStage.setScene(this.primaryScene); // la scene contient la racine qui est un BorderPane
        
        this.racine1.setStyle("-fx-background: #55ff66; -fx-background-size: 500, 500;");

        primaryStage.show();
        
    }
    
    public void initialiserJeu(Stage primaryStage, Stage secondStage) {
        
        if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.dessinerCasesGrille");
        
        secondStage.setTitle("Partie");
        
        // on positionne la fenetre
        secondStage.setX(200);
        
        secondStage.setY(200);
        
        // Creation du menu interne du jeu
        MenuBar menuBar = new MenuBar();
        
        Menu menu = new Menu("Partie");
        
        MenuItem itemPartieAnnulerCoup = new MenuItem("Annuler le dernier coup");
        
        itemPartieAnnulerCoup.setOnAction(new EventHandler<ActionEvent>() {
        
            @Override
            public void handle(ActionEvent event) {
                
                //todo
                System.out.println("annuler dernier coup");
                
            }
        
        });
        
        MenuItem itemPartieRecommencer = new MenuItem("Recommencer");
        
        itemPartieRecommencer.setOnAction(new EventHandler<ActionEvent>() {
        
            @Override
            public void handle(ActionEvent event) {
                
                //todo
                System.out.println("recommencer");
                
            }
        
        });
        
        MenuItem itemPartieQuitter = new MenuItem("Quitter la partie");
        
        itemPartieQuitter.setOnAction(new EventHandler<ActionEvent>() {
        
            @Override
            public void handle(ActionEvent event) {
                
                //todo
                System.out.println("quitter");
                
            }
        
        });
        
        menu.getItems().addAll(itemPartieAnnulerCoup, itemPartieRecommencer, itemPartieQuitter);
        
        menuBar.getMenus().addAll(menu);
        
        this.racine2.setTop(menuBar); // on les ajoute au centre du BorderPane
        
        this.gameGridPane = new GridPane();
        
        this.gameGridPane.setGridLinesVisible(false);
        
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

                } else {
                    
                    image.setImage(this.grille.getCase(y, x).getLien().getImage());
                    
                    image.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    
                        public void handle(MouseEvent event) {

                            // attention, le setOnDragDone est déclenché par la source du Drag&Drop
                            if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.image.onDragDone");

                            grille.clic(x, y);

                        }
                    
                    });
                    
                }
                
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

                        grille.stopDragAndDrop();

                    }

                });

                pane.getChildren().add(image);
               
                this.gameGridPane.add(pane, j, i);
                
            }
            
        }
        
        this.racine2.setCenter(this.gameGridPane); // on les ajoute au centre du BorderPane
        
        secondStage.setScene(this.secondScene); // la scene contient la racine qui est un BorderPane

    }
    
    // Initialisation du modèle    
    public void initialisationDuModele () {
                
        if( MainApplication.DISPLAY_DEBUG ) System.out.println("MainApplication.initialisationDuModele");

        PairsGenerator p = new PairsGenerator(4, 4, 3);
        
        this.grille = new Grille( p.generatePairs() );
        
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
                                
                                colorierCase(gridCase, cheminActuel.get( found ));
                                
                            } else {
                                
                                // Coloriage spécifique du plateau
                                colorierCase(gridCase, c);
                            
                            }
                            
                        }
                        
                        //System.out.println();
                        
                    }
                    
                }
                
            }
            
        });
        
    }
    
    public void colorierCase(Object gridCase, Case c) {
                
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
