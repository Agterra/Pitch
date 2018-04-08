package vue;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.*;

import model.*;

public class MainApplication extends Application {

    /**************************************************************************/
    // VARIABLES MEMBRES
    /**************************************************************************/
    
    public static boolean DEBUGAGE = true;

    // Propriétés de fenêtre
    private int hauteurFenetre;

    private int largeurFenetre;

    private double ratio;

    // Eléments du modele
    private int hauteur;
    
    private int largeur;

    private Grille grille;
    
    // Eléments graphiques du menu
    private Scene premiereScene;

    private BorderPane racine1;

    // Eléments graphiques du jeu
    private Stage deuxiemeStage;

    private Scene deuxiemeScene;

    private BorderPane racine2;

    private GridPane gridPaneJeu;

    /**************************************************************************/
    // FONCTIONS MEMBRES
    /**************************************************************************/
    
    public static void main(String[] args) {

        if (MainApplication.DEBUGAGE) {
            System.out.println("MainApplication.main");
        }

        launch(args);

    }

    /**
     * Initialise les composants et lance une partie
     * @param premierStage Fenêtre du menu
     */
    @Override
    public void start(Stage premierStage) throws Exception {

        if (MainApplication.DEBUGAGE) {
            System.out.println("MainApplication.start");
        }

        initialiserVariablesMembres();

        initialiserModele(premierStage);

        initialiserGraphiques(premierStage, this.deuxiemeStage);

    }

    /**
     * Initialise les variables membres (stages, scènes, panes...)
     */
    public void initialiserVariablesMembres() {

        this.hauteurFenetre = 600;

        this.largeurFenetre = 580;

        this.grille = new Grille();
        
        this.hauteur = 4;
        
        this.largeur = 4;

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
     * Initialise les composants graphiques : menu et jeu
     *
     * @param premierStage Fenêtre du menu
     * @param deuxiemeStage Fenêtre du jeu
     */
    public void initialiserGraphiques(Stage premierStage, Stage deuxiemeStage) {

        if (MainApplication.DEBUGAGE) {
            System.out.println("MainApplication.initialisationGraphique");
        }

        initialiserMenuAccueil(premierStage, deuxiemeStage);

        initialiserJeu(premierStage, deuxiemeStage);

    }
    
    /**
     * Construit le menu avec le titre et les boutons
     *
     * @param premierStage Fenêtre du menu
     * @param deuxiemeStage Fenêtre du jeu
     */
    public void initialiserMenuAccueil(Stage premierStage, Stage deuxiemeStage) {

        premierStage.setTitle("Javaline");

        // On positionne la fenetre
        premierStage.setX(Screen.getPrimary().getBounds().getMaxX()/2 - this.largeurFenetre/2);
        premierStage.setY(Screen.getPrimary().getBounds().getMaxY()/2 - this.hauteurFenetre/2);
        premierStage.setWidth(this.largeurFenetre);
        premierStage.setHeight(this.hauteurFenetre);

        // On ajoute un titre...
        Label titre = new Label("Javaline");

        titre.setFont(Font.font("Verdana", 42));

        // ... et trois boutons
        Button boutonNouvellePartie = new Button();

        boutonNouvellePartie.setText("Nouvelle partie (4*4)");

        boutonNouvellePartie.setMaxWidth(200);

        boutonNouvellePartie.setFont(Font.font("Verdana", 16));
        
        
        Button boutonChangerTaille = new Button();
        
        boutonChangerTaille.setText("Modifier la taille");
        
        boutonChangerTaille.setMaxWidth(200);
        
        boutonChangerTaille.setFont(Font.font("Verdana", 16));

        
        Button boutonRegles = new Button();

        boutonRegles.setText("Règles");

        boutonRegles.setMaxWidth(200);

        boutonRegles.setFont(Font.font("Verdana", 16));
        
        
        Button boutonQuitter = new Button();

        boutonQuitter.setText("Quitter");

        boutonQuitter.setMaxWidth(200);

        boutonQuitter.setFont(Font.font("Verdana", 16));
        
        initialiserActionsAccueil(boutonNouvellePartie, boutonChangerTaille, boutonRegles, boutonQuitter, premierStage);

        VBox menu = new VBox(titre, boutonNouvellePartie, boutonChangerTaille, boutonRegles, boutonQuitter); // On crée un menu vertical contenant les boutons

        menu.setSpacing(20);

        menu.setAlignment(Pos.CENTER); // on centre les boutons

        this.racine1.setCenter(menu); // on les ajoute au centre du BorderPane

        premierStage.setScene(this.premiereScene); // la scene contient la racine qui est un BorderPane

        this.racine1.setStyle("-fx-background: #55ff66;");

        premierStage.show();

    }
    
    /**
     * Ajoute des évènements sur les boutons du menu d'accueil
     *
     * @param boutonNouvellePartie Bouton pour lancer une nouvelle partie
     * @param boutonChangerTaille Bouton pour changer la taille de la grille
     * @param boutonRegles Bouton pour afficher les règles du jeu
     * @param boutonQuitter Bouton pour quitter le jeu
     * @param premierStage Fenêtre du menu
     */
    public void initialiserActionsAccueil(Button boutonNouvellePartie, Button boutonChangerTaille, Button boutonRegles, Button boutonQuitter, Stage premierStage) {
        
        boutonNouvellePartie.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evenement) {
                
                premierStage.close(); // on ferme la fenêtre de menu

                deuxiemeStage.show(); // on affiche la nouvelle partie

            }

        });
        
        boutonChangerTaille.setOnAction(new EventHandler<ActionEvent>() {
        
            @Override
            public void handle(ActionEvent evenement) {
                
                TextInputDialog demanderTaille = new TextInputDialog("");

                demanderTaille.initOwner(premierStage);
                
                demanderTaille.setTitle("Changement de taille");

                demanderTaille.setHeaderText("Change la taille de la grille");

                demanderTaille.setContentText("Taille ");

                Optional<String> result = demanderTaille.showAndWait();
                
                if (result.isPresent()) {
                    
                    int longueur = Integer.valueOf(result.get()); //on convertit la chaîne de caractères en entier
                    
                    hauteur = longueur;
                    
                    largeur = longueur;
                    
                    initialiserModele(premierStage);
                    
                    grille.formaterGrille(hauteur, largeur);
                    
                    initialiserJeu(premierStage, deuxiemeStage);
               
                    premierStage.close();
                    
                    deuxiemeStage.show();
                    
                }

            }
            
        });
        
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
        
        boutonQuitter.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent evenement) {
                
                premierStage.close(); // on ferme la fenêtre de menu

            }

        });
        
    }

    /**
     * Initialise le jeu avec le modèle (grille) et ajoute le menu
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

        MenuItem itemNouvellePartie = new MenuItem("Recommencer une nouvelle partie");

        MenuItem itemPartieQuitter = new MenuItem("Quitter la partie");

        initialiserActionsMenu(itemPartieAnnulerCoup, itemPartieRecommencer, itemNouvellePartie ,itemPartieQuitter, premierStage);
        
        // Ajout des items dans le menu
        menu.getItems().addAll(itemPartieAnnulerCoup, itemPartieRecommencer, itemNouvellePartie, itemPartieQuitter);

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

                        grille.finirDragAndDrop( x, y );

                    }

                });

                pane.getChildren().add(image);

                this.gridPaneJeu.add(pane, j, i);

            }

        }

    }

    /**
     * Initialise les actions du menu
     * @param annulerCoupItem Item pour annuler le dernier coup
     * @param recommencerItem Item pour commencer la partie en cours
     * @param nouvellePartieItem Item pour jouer une nouvelle partie
     * @param quitterItem Item pour revenir à l'écran d'accueil
     * @param premierStage Fenêtre du menu
     */
    public void initialiserActionsMenu( MenuItem annulerCoupItem, MenuItem recommencerItem, MenuItem nouvellePartieItem, MenuItem quitterItem, Stage premierStage ) {
        
        
        // Ajout des interactions sur le menu
        annulerCoupItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evenement) {

                grille.annulerDernierCoup();

            }

        });

        recommencerItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evenement) {

                grille.reinitialiser();

            }

        });
        
        nouvellePartieItem.setOnAction( new EventHandler<ActionEvent>() {
            
           @Override
           public void handle(ActionEvent evenement) {
               
               grille.formaterGrille();
               
               initialiserJeu(premierStage, deuxiemeStage);
               
           }
            
        });

        quitterItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evenement) {
                
                grille.formaterGrille();
                
                initialiserJeu(premierStage, deuxiemeStage);
               
                deuxiemeStage.close();
                
                premierStage.show();

            }

        });
        
    }
    
    /**
     * Initialise le modèle avec la génération des paires
     * @param premierStage Fenêtre du menu
     */
    public void initialiserModele(Stage premierStage) {

        if (MainApplication.DEBUGAGE) {
            System.out.println("MainApplication.initialiserModele");
        }
        
        System.out.println("MAIN hauteur = " + this.hauteur);
        
        System.out.println("MAIN largeur = " + this.largeur);

        this.grille = new Grille(this.hauteur, this.largeur, 2);

        this.grille.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object arg) {

                if (o instanceof Grille) {

                    Grille grille = (Grille) o;
                    
                    System.out.println("GRILLE hauteur = " + grille.getLongueur());
                    
                    System.out.println("GRILLE largeur = " + grille.getLargeur());

                    ArrayList<Case> cheminActuel = grille.getCheminActuel().getCases();
                    
                    System.out.println(cheminActuel.toString());
                    
                    
                    //Dessiner chemin actuel
                    for ( Case c : cheminActuel ) {
                        
                        Object grilleCases = gridPaneJeu.getChildren().get(c.getY() * grille.getLargeur() + c.getX());

                        colorierCase(grilleCases, c);

                    }
                    
                    System.out.println("OK1");
                    
                    for (int i = 0; i < grille.getLongueur(); i++) {

                        for (int j = 0; j < grille.getLargeur(); j++) {

                            Object grilleCases = gridPaneJeu.getChildren().get(i * grille.getLargeur() + j);

                            System.out.println("grilleCases num " + i * grille.getLargeur() + j);
                            
                            Case c = grille.getCase(i, j);
                            
                            colorierCase(grilleCases, c);

                        }
                        
                    }
                    
                    System.out.println("OK2");
                    
                    if(grille.getPartieTerminee() == 0 || grille.getPartieTerminee() == 1) {
                        
                        messageFin(premierStage, deuxiemeStage);
                        
                        grille.formaterGrille();
                        
                        initialiserJeu(premierStage, deuxiemeStage);
                        
                    }
                    
                    System.out.println("OK3");

                }
                       
            }

        });

    }

    /**
     * Ajoute les images en fonction des actions de l'utilisateur
     * @param grilleCases .
     * @param c .
     */
    public void colorierCase(Object grilleCases, Case c) {

        if (c.getSymbole() != Symbole.VIDE) {

            if (grilleCases instanceof Pane) {

                Pane pane = (Pane) grilleCases;

                ImageView image = (ImageView) pane.getChildren().get(0);

                image.setImage(c.getSymbole().getImage());

            }

        } else {

            //Modify rectangles with Correct lien
            
            if (grilleCases instanceof Pane) {

                Pane pane = (Pane) grilleCases;

                ImageView image = (ImageView) pane.getChildren().get(0);

                image.setImage(c.getLien().getImage());

            }

        }

    }
    
    /**
     * Affiche une popup quand la partie est terminée
     * @param premierStage Fenêtre du menu
     * @param deuxiemeStage  Fenêtre du jeu
     */
    public void messageFin(Stage premierStage, Stage deuxiemeStage) {
        
        Alert messageFin = new Alert(AlertType.CONFIRMATION);
        
        messageFin.initOwner(deuxiemeStage);
        
        messageFin.setTitle("Partie finie");
        
        if(grille.getPartieTerminee() == 1) {
            
            messageFin.setContentText("Partie gagnée ! Voulez-vous rejouer ?");
            
        } else if(grille.getPartieTerminee() == 0) {
            
            messageFin.setContentText("Partie perdue ! Voulez-vous rejouer ?");
            
        }

        Optional<ButtonType> result = messageFin.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            
            grille.formaterGrille();
            
            initialiserJeu(premierStage, deuxiemeStage);
               
        } else {
            
            premierStage.show();
            
            deuxiemeStage.close();
            
        }
        
    }

}
