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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.*;

import model.*;

public class MainApplication extends Application {

    ///////////////////////////////////////////
    // VARIABLES MEMBRES
    ///////////////////////////////////////////
    public static boolean DEBUGAGE = true;

    // Propriétés de fenêtre
    private int hauteurFenetre;

    private int largeurFenetre;

    private double ratio;

    // Eléments du modele
    private Grille grille;

    // Eléments graphiques du menu
    private Scene premiereScene;

    private BorderPane racine1;

    // Eléments graphiques du jeu
    private Stage deuxiemeStage;

    private Scene deuxiemeScene;

    private BorderPane racine2;

    private GridPane gridPaneJeu;

    ///////////////////////////////////////////
    // FONCTIONS MEMBRES
    ///////////////////////////////////////////
    public static void main(String[] args) {

        if (MainApplication.DEBUGAGE) {
            System.out.println("MainApplication.main");
        }

        launch(args);

    }

    /**
     *
     */
    @Override
    public void start(Stage premierStage) throws Exception {

        if (MainApplication.DEBUGAGE) {
            System.out.println("MainApplication.start");
        }

        initialiserVariablesMembres();

        initialiserModele();

        initialisationGraphique(premierStage, this.deuxiemeStage);

    }

    /**
     * Initialisation des variables membres (stages, scènes, panes...)
     */
    public void initialiserVariablesMembres() {

        this.hauteurFenetre = 600;

        this.largeurFenetre = 580;

        this.grille = new Grille();

        this.ratio = Math.floor(this.largeurFenetre / this.grille.getLargeur()) - 10;

        this.racine1 = new BorderPane();

        this.premiereScene = new Scene(this.racine1, this.largeurFenetre, this.hauteurFenetre);

        this.deuxiemeStage = new Stage();

        this.racine2 = new BorderPane();

        this.gridPaneJeu = new GridPane();

        this.gridPaneJeu.setGridLinesVisible(false);

        this.deuxiemeScene = new Scene(this.racine2, this.largeurFenetre, this.hauteurFenetre);

    }

    /**
     * Initialisation des composants graphiques : menu et jeu
     *
     * @param premierStage Fenêtre du menu
     * @param deuxiemeStage Fenêtre du jeu
     */
    public void initialisationGraphique(Stage premierStage, Stage deuxiemeStage) {

        if (MainApplication.DEBUGAGE) {
            System.out.println("MainApplication.initialisationGraphique");
        }

        initialiserMenu(premierStage, deuxiemeStage);

        initialiserJeu(premierStage, deuxiemeStage);

    }

    /**
     * Construction du menu avec le titre et les boutons
     *
     * @param premierStage Fenêtre du menu
     * @param deuxiemeStage Fenêtre du jeu
     */
    public void initialiserMenu(Stage premierStage, Stage deuxiemeStage) {

        premierStage.setTitle("Javaline");

        // On positionne la fenetre
        premierStage.setX(Screen.getPrimary().getBounds().getMaxX()/2 - this.largeurFenetre/2);
        premierStage.setY(Screen.getPrimary().getBounds().getMaxY()/2 - this.hauteurFenetre/2);
        premierStage.setWidth(this.largeurFenetre);
        premierStage.setHeight(this.hauteurFenetre);

        // On ajoute un titre...
        Label titre = new Label("Javaline");

        titre.setFont(Font.font("Verdana", 42));

        // ... et deux boutons
        Button boutonNouvellePartie = new Button();

        boutonNouvellePartie.setText("Nouvelle partie");

        boutonNouvellePartie.setMaxWidth(200);

        boutonNouvellePartie.setFont(Font.font("Verdana", 16));

        //on ajoute un évènement sur le bouton de nouvelle partie
        boutonNouvellePartie.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evenement) {

                premierStage.close(); // on ferme la fenêtre de menu

                deuxiemeStage.show(); // on affiche la nouvelle partie

            }

        });

        Button boutonRegles = new Button();

        boutonRegles.setText("Règles");

        boutonRegles.setMaxWidth(200);

        boutonRegles.setFont(Font.font("Verdana", 16));

        boutonRegles.setOnAction(new EventHandler<ActionEvent>() {

            int nombreClics = 0;

            @Override
            public void handle(ActionEvent evenement) {

                nombreClics = nombreClics + 1;

                TextArea regles = new TextArea("Glissez votre souris pour connecter deux symboles identiques (créant ainsi un tuyau). Le but est de connecter toutes les paires et d'utiliser toutes les cases du tableau. Mais attention, les tuyaux se briseront s'ils se croisent ou se chevauchent!");

                regles.setFont(Font.font("Verdana", 16));

                regles.setWrapText(true);

                racine1.setBottom(regles); // /!\ ne pas mettre this.racine1 sinon on fait référence à la fonction anonyme

                if (nombreClics % 2 == 0) { // on a re-cliqué sur le bouton donc on enlève les règles

                    racine1.getChildren().remove(regles);

                }

            }

        });

        VBox menu = new VBox(titre, boutonNouvellePartie, boutonRegles); // On crée un menu vertical contenant les boutons

        menu.setSpacing(20);

        menu.setAlignment(Pos.CENTER); // on centre les boutons

        this.racine1.setCenter(menu); // on les ajoute au centre du BorderPane

        premierStage.setScene(this.premiereScene); // la scene contient la racine qui est un BorderPane

        this.racine1.setStyle("-fx-background: #55ff66;");

        premierStage.show();

    }

    /**
     * Initialisation du jeu avec le modèle (grille) et ajout d'un menu
     *
     * @param premierStage Fenêtre du menu
     * @param deuxiemeStage Fenêtre du jeu
     */
    public void initialiserJeu(Stage premierStage, Stage deuxiemeStage) {

        if (MainApplication.DEBUGAGE) {
            
            System.out.println("MainApplication.initialiserJeu");
            
        }

        deuxiemeStage.setTitle("Partie");

        // On positionne la fenetre
        deuxiemeStage.setX(Screen.getPrimary().getBounds().getMaxX()/2 - this.largeurFenetre/2);
        
        deuxiemeStage.setY(Screen.getPrimary().getBounds().getMaxY()/2 - this.hauteurFenetre/2);
        
        // Creation du menu interne du jeu
        MenuBar menuBar = new MenuBar();

        Menu menu = new Menu("Partie");

        MenuItem itemPartieAnnulerCoup = new MenuItem("Annuler le dernier coup");

        MenuItem itemPartieRecommencer = new MenuItem("Recommencer");

        MenuItem itemPartieQuitter = new MenuItem("Quitter la partie");

        initialiserActionsMenu(itemPartieAnnulerCoup, itemPartieRecommencer, itemPartieQuitter);
        
        // Ajout des items dans le menu
        menu.getItems().addAll(itemPartieAnnulerCoup, itemPartieRecommencer, itemPartieQuitter);

        menuBar.getMenus().addAll(menu);

        this.racine2.setTop(menuBar); // on les ajoute en haut du BorderPane

        this.gridPaneJeu = new GridPane();

        this.racine2.setCenter(this.gridPaneJeu);
        
        this.gridPaneJeu.setAlignment(Pos.CENTER);
        
        deuxiemeStage.setScene(this.deuxiemeScene); // la scene contient la racine qui est un BorderPane

        for (int i = 0; i < this.grille.getLongueur(); i++) {

            for (int j = 0; j < this.grille.getLargeur(); j++) {

                final int y = i;

                final int x = j;

                Pane pane = new Pane();

                pane.setPrefSize(this.ratio, this.ratio);

                Border bordure = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

                pane.setBorder(bordure);

                ImageView image = new ImageView();

                image.setFitHeight(ratio - 1);

                image.setFitWidth(ratio - 1);

                if (this.grille.getCase(y, x).getSymbole() != Symbole.VIDE) {

                    image.setImage(this.grille.getCase(y, x).getSymbole().getImage());

                    image.setOnDragDetected(new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent evenement) {

                            if (MainApplication.DEBUGAGE) {
                                System.out.println("MainApplication.image.onDragDetected");
                            }

                            Dragboard db = image.startDragAndDrop(TransferMode.ANY);

                            ClipboardContent contenu = new ClipboardContent();

                            contenu.putString(""); // non utilisé actuellement

                            db.setContent(contenu);

                            evenement.consume();

                            grille.commencerDragAndDrop(x, y);

                        }

                    });

                } else {

                    image.setImage(this.grille.getCase(y, x).getLien().getImage());

                    image.setOnMouseClicked(new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent evenement) {

                            // attention, le setOnDragDone est déclenché par la source du Drag&Drop
                            if (MainApplication.DEBUGAGE) {
                                System.out.println("MainApplication.image.onDragDone");
                            }

                            grille.clic(x, y);

                        }

                    });

                }

                image.setOnDragEntered(new EventHandler<DragEvent>() {

                    public void handle(DragEvent evenement) {

                        if (MainApplication.DEBUGAGE) {
                            System.out.println("MainApplication.image.onDragEntered");
                        }

                        evenement.consume();

                        grille.majDragAndDrop(x, y);

                    }

                });

                image.setOnDragDone(new EventHandler<DragEvent>() {

                    public void handle(DragEvent evenement) {

                        // attention, le setOnDragDone est déclenché par la source du Drag&Drop
                        if (MainApplication.DEBUGAGE) {
                            System.out.println("MainApplication.image.onDragDone");
                        }

                        grille.finirDragAndDrop();

                    }

                });

                pane.getChildren().add(image);

                this.gridPaneJeu.add(pane, j, i);

            }

        }

    }

    /**
     * Initialisation des actions du menu
     * @param annulerCoupItem
     * @param recommencerItem
     * @param quitterItem
     */
    public void initialiserActionsMenu( MenuItem annulerCoupItem, MenuItem recommencerItem, MenuItem quitterItem ) {
        
        
        // Ajout des interactions sur le menu
        annulerCoupItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evenement) {

                //todo
                System.out.println("annuler dernier coup");

            }

        });

        recommencerItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evenement) {

                grille.reinitialiser();
                
                System.out.println("recommencer");

            }

        });

        quitterItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evenement) {

                //todo
                System.out.println("quitter");

            }

        });
        
    }
    
    
    /**
     * Initialisation du modèle avec la generation des paires
     */
    public void initialiserModele() {

        if (MainApplication.DEBUGAGE) {
            System.out.println("MainApplication.initialiserModele");
        }

        GenerateurPaires p = new GenerateurPaires(4, 4, 3);

        this.grille = new Grille(p.genererPaires());

        this.grille.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object arg) {

                //System.out.println("update: " + arg);
                if (o instanceof Grille) {

                    Grille grille = (Grille) o;

                    ArrayList<Case> cheminActuel = grille.getCheminActuel().getCases();

                    //System.out.println(cheminActuel.toString());
                    for (int i = 0; i < grille.getLongueur(); i++) {

                        for (int j = 0; j < grille.getLargeur(); j++) {

                            Object grilleCases = gridPaneJeu.getChildren().get(i * grille.getLargeur() + j);

                            Case c = grille.getCase(i, j);

                            int chercher = cheminActuel.indexOf(c);

                            // Coloriage spécifique du chemin
                            if (chercher != -1) {

                                colorierCase(grilleCases, cheminActuel.get(chercher));

                            } else {

                                // Coloriage spécifique du plateau
                                colorierCase(grilleCases, c);

                            }

                        }

                        //System.out.println();
                    }

                }

            }

        });

    }

    /**
     *
     * @param grilleCases .
     * @param c .
     */
    public void colorierCase(Object grilleCases, Case c) {

        //if(MainApplication.DISPLAY_DEBUG) System.out.println("MainApplication.colorCell");
        if (c.getSymbole() != Symbole.VIDE) {

            if (grilleCases instanceof Pane) {

                Pane pane = (Pane) grilleCases;

                ImageView image = (ImageView) pane.getChildren().get(0);

                image.setImage(c.getSymbole().getImage());

            }

        } else {

            // Modify rectangles with Correct lien
            //System.out.println(updatedCell.getLien().toString());
            if (grilleCases instanceof Pane) {

                Pane pane = (Pane) grilleCases;

                ImageView image = (ImageView) pane.getChildren().get(0);

                image.setImage(c.getLien().getImage());

            }

        }

    }

}
