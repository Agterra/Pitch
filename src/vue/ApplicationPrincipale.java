package vue;

import controleur.IDActions;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import modele.Case;
import modele.Controleur;
import modele.Grille;
import modele.Symbole;


public class ApplicationPrincipale extends Application {
    
    /**************************************************************************/
    // VARIABLES MEMBRES
    /**************************************************************************/
    
    public static final boolean DEBUGAGE = false;

    // Propriété des fenêtres
    private int hauteurFenetre;
    
    private int largeurFenetre;
    
    private double ratio;
    
    private int nombreClics;
    
    
    // Conteneurs de vue
    private BorderPane racine1;
    
    private BorderPane racine2;
    
    // Scenes
    private Scene premiereScene;
    
    private Scene secondeScene;
    
    // Stages
    private Stage premierStage;
    
    private Stage secondStage;
    
    // Plateau de jeu
    private GridPane gridPaneJeu;
    
    // Controleur
    private Controleur controleur;
    
    // Modele
    public Grille grille;
    
    /**************************************************************************/
    // FONCTIONS MEMBRES
    /**************************************************************************/
    
    /**
     * Fonction principale
     * @param args Les paramètres d'exécution
     */
    public static void main(String[] args) {
        
        launch(args);
        
    }
    
    /**
     * Lance l'initialisation d'un nouveau jeu
     * @param premierStage Le menu d'accueil
     * @throws Exception .
     */
    @Override
    public void start(Stage premierStage) throws Exception {
    
        this.premierStage = premierStage;
        
        this.secondStage = new Stage();
        
        this.initialisation();
        
    }
    
    /**
     * Lance l'initialisation d'un nouveau jeu, du modèle et des fenêtres
     */
    public void initialisation() {
        
        this.grille = new Grille(4, 4, 2);
        
        this.controleur = new Controleur(this, this.grille);
        
        initialisationModele();
        
        initialisationFenetres();
        
    }
    
    /**
     * Lance l'initialisation du modèle
     */
    public void initialisationModele() {
        
        this.grille.addObserver(this.controleur);
        
    }
    
    /**
     * Lance l'initialisation des fenêtres
     */
    public void initialisationFenetres() {
        
        // Propriétés de la fenêtre
        this.hauteurFenetre = 600;

        this.largeurFenetre = 580;

        this.ratio = Math.floor(this.largeurFenetre / this.grille.getLargeur()) - 10;

        
        // Premier conteneur de vue et initialisation du menu d'accueil
        
        premierStage.setTitle("Javaline");

        // On positionne la fenetre
        premierStage.setX(Screen.getPrimary().getBounds().getMaxX()/2 - this.largeurFenetre/2);
        
        premierStage.setY(Screen.getPrimary().getBounds().getMaxY()/2 - this.hauteurFenetre/2);
        
        premierStage.setWidth(this.largeurFenetre);
        
        premierStage.setHeight(this.hauteurFenetre);

        this.racine1 = new BorderPane();

        this.premiereScene = new Scene(this.racine1, this.largeurFenetre, this.hauteurFenetre);
        
        this.initialisationMenuAccueil();

        this.premierStage.setScene(this.premiereScene); // la scene contient la racine qui est un BorderPane

        this.racine1.setStyle("-fx-background: #55ff66;");

        premierStage.show();
        
        
        // Second conteneur de vue
        this.racine2 = new BorderPane();

        this.gridPaneJeu = new GridPane();

        this.secondeScene = new Scene(this.racine2, this.largeurFenetre, this.hauteurFenetre);
        
        this.initialiserJeu();
        
    }
    
    /**
     * Lance l'initailisation d'un nouveau jeu
     */
    public void initialiserJeu() {
        
        this.secondStage.setTitle("Partie");

        // On positionne la fenetre
        this.secondStage.setX(Screen.getPrimary().getBounds().getMaxX()/2 - this.largeurFenetre/2);
        
        this.secondStage.setY(Screen.getPrimary().getBounds().getMaxY()/2 - this.hauteurFenetre/2);
        
        // Creation du menu interne du jeu
        MenuBar menuBar = new MenuBar();

        Menu menu = new Menu("Partie");

        MenuItem itemPartieAnnulerCoup = new MenuItem("Annuler le dernier coup");

        MenuItem itemPartieRecommencer = new MenuItem("Recommencer");

        MenuItem itemNouvellePartie = new MenuItem("Recommencer une nouvelle partie");

        MenuItem itemPartieQuitter = new MenuItem("Quitter la partie");

        initialiserActionsMenuJeu(itemPartieAnnulerCoup, itemPartieRecommencer, itemNouvellePartie ,itemPartieQuitter);
        
        // Ajout des items dans le menu
        menu.getItems().addAll(itemPartieAnnulerCoup, itemPartieRecommencer, itemNouvellePartie, itemPartieQuitter);

        menuBar.getMenus().addAll(menu);

        
        this.racine2.setTop(menuBar); // on les ajoute en haut du BorderPane

        this.gridPaneJeu = new GridPane();

        this.racine2.setCenter(this.gridPaneJeu);
        
        this.gridPaneJeu.setAlignment(Pos.CENTER);
        
        this.secondStage.setScene(this.secondeScene); // la scene contient la racine qui est un BorderPane

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

                image.setId(x + "," + y);
                
                if (this.grille.getCase(y, x).getSymbole() != Symbole.VIDE) {

                    image.setImage(this.grille.getCase(y, x).getSymbole().getImage());

                    image.setOnDragDetected(this.controleur);
                    
                } else {

                    image.setImage(this.grille.getCase(y, x).getLien().getImage());

                    image.setOnMouseClicked(this.controleur);
                    
                }
                
                image.setOnDragEntered(this.controleur);

                image.setOnDragDone(this.controleur);
                
                pane.getChildren().add(image);

                this.gridPaneJeu.add(pane, j, i);

            }

        }
        
    }
    
    /**
     * Initialise le menu interne au jeu
     * 
     * @param annulerCoupItem L'item "annuler le dernier coup" du menu
     * @param recommencerItem L'item "recommencer la partie" du menu
     * @param nouvellePartieItem L'item "nouvelle partie" du menu
     * @param quitterItem L'item "quitter" du menu
     */
    public void initialiserActionsMenuJeu(MenuItem annulerCoupItem, MenuItem recommencerItem, MenuItem nouvellePartieItem, MenuItem quitterItem) {
        
        // Ajout des interactions sur le menu
        annulerCoupItem.setId(IDActions.MENU_JEU_ANNULER_DERNIER_COUP.toString());
        
        annulerCoupItem.setOnAction(this.controleur);
        
        
        recommencerItem.setId(IDActions.MENU_JEU_RECOMMENCER.toString());
        
        recommencerItem.setOnAction(this.controleur);
        
        
        nouvellePartieItem.setId(IDActions.MENU_JEU_RECOMMENCER_NOUVELLE_PARTIE.toString());
        
        nouvellePartieItem.setOnAction(this.controleur);
        
        
        quitterItem.setId(IDActions.MENU_JEU_QUITTER.toString());
        
        quitterItem.setOnAction(this.controleur);
        
    }
    
    /**
     * Initialise le menu d'accueil
     */
    public void initialisationMenuAccueil() {
        
        // On ajoute un titre...
        Label titre = new Label("Javaline");

        titre.setFont(Font.font("Verdana", 42));

        // ... et quatre boutons
        Button boutonNouvellePartie = new Button();

        boutonNouvellePartie.setText("Nouvelle partie");

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
        
        VBox menu = new VBox(titre, boutonNouvellePartie, boutonChangerTaille, boutonRegles, boutonQuitter); // On crée un menu vertical contenant les boutons

        menu.setSpacing(20);

        menu.setAlignment(Pos.CENTER); // on centre les boutons

        this.racine1.setCenter(menu); // on les ajoute au centre du BorderPane

        initialiserActionsMenuAccueil(boutonNouvellePartie, boutonChangerTaille, boutonRegles, boutonQuitter);

    }
    
    /**
     * Initialise les boutons du menu d'accueil
     * 
     * @param boutonNouvellePartie Le bouton "Nouvelle partie" du menu
     * @param boutonChangerTaille Le bouton "Modifier la taille" du menu
     * @param boutonRegles Le bouton "Règles" du menu
     * @param boutonQuitter Le bouton "Quitter" du menu
     */
    public void initialiserActionsMenuAccueil(Button boutonNouvellePartie, Button boutonChangerTaille, Button boutonRegles, Button boutonQuitter) {
        
        boutonNouvellePartie.setId(IDActions.MENU_ACCUEIL_NOUVELLE_PARTIE.toString());
        
        boutonNouvellePartie.setOnAction(this.controleur);
        
        
        boutonChangerTaille.setId(IDActions.MENU_ACCUEIL_MODIFIER_TAILLE.toString());
        
        boutonChangerTaille.setOnAction(this.controleur);
        
        
        boutonRegles.setId(IDActions.MENU_ACCUEIL_REGLES.toString());
        
        this.nombreClics = 0;
        
        boutonRegles.setOnAction(this.controleur);
             
        
        boutonQuitter.setId(IDActions.MENU_ACCUEIL_QUITTER.toString());
        
        boutonQuitter.setOnAction(this.controleur);
        
    }
    
    
    /**
     * Affiche la fenêtre de jeu
     */
    public void actionBoutonMenuPrincipalNouvellePartie() {
        
        premierStage.close(); // on ferme la fenêtre de menu

        secondStage.show(); // on affiche la nouvelle partie
        
    }
    
    /**
     * Demande à l'utilisateur la taille qu'il veut pour la grille
     */
    public void actionBoutonMenuPrincipalModifierTaille() {
        
        TextInputDialog demanderTaille = new TextInputDialog("");

        demanderTaille.initOwner(premierStage);

        demanderTaille.setTitle("Changement de taille");

        demanderTaille.setHeaderText("Changer la taille de la grille");

        demanderTaille.setContentText("Taille ");

        Optional<String> result = demanderTaille.showAndWait();

        if (result.isPresent()) {

            int longueur = Integer.valueOf(result.get()); //on convertit la chaîne de caractères en entier

            this.ratio = Math.floor(this.largeurFenetre / longueur) - 10;
            
            this.controleur.mettreAJourTailleGrille(longueur);
            
            initialiserJeu();

            initialisationModele();

            premierStage.close();

            secondStage.show();

        }

        
    }
    
    /**
     * Affiche les règles du jeu
     */
    public void actionBoutonMenuPrincipalRegles() {
        
        this.nombreClics = this.nombreClics + 1;

        TextArea regles = new TextArea("Glissez votre souris pour connecter deux symboles identiques (créant ainsi un tuyau). Le but est de connecter toutes les paires et d'utiliser toutes les cases du tableau. Mais attention, les tuyaux se briseront s'ils se croisent ou se chevauchent!");

        regles.setFont(Font.font("Verdana", 16));

        regles.setWrapText(true);

        this.racine1.setBottom(regles); // /!\ ne pas mettre this.racine1 sinon on fait référence à la fonction anonyme

        if (this.nombreClics % 2 == 0) { // on a re-cliqué sur le bouton donc on enlève les règles

            this.racine1.getChildren().remove(regles);

        }

    }
    
    /**
     * Quitte le jeu
     */
    public void actionBoutonMenuPrincipalQuitter() {
        
        this.premierStage.close(); // on ferme la fenêtre de menu
       
    }
    
    /**
     * Revient au menu d'accueil
     */
    public void actionBoutonMenuJeuQuitter() {
        
        this.secondStage.close();

        this.premierStage.show();

    }
    
    /**
     * Commence le drag and drop graphique
     * @param image .
     */
    public void startDragAndDrop(ImageView image) {
        
        Dragboard db = image.startDragAndDrop(TransferMode.ANY);

        ClipboardContent contenu = new ClipboardContent();

        contenu.putString(""); // non utilisé actuellement

        db.setContent(contenu);

    }
    
    /**
     * Ajoute les images en fonction des actions de l'utilisateur
     * @param c La case à colorier
     */
    public void colorierCase(Case c) {

        Object grilleCases = gridPaneJeu.getChildren().get(c.getY() * grille.getLargeur() + c.getX());

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
     * Affiche le messgae de fin de partie en fonction de l'issue du jeu
     */
    public void afficherMessageDeFin() {
        
        this.messageFin();

    }
    
    /**
     * Affiche une popup quand la partie est terminée
     * 
     * @param premierStage Fenêtre du menu
     * @param deuxiemeStage Fenêtre du jeu
     */
    private void messageFin() {
        
        Alert messageFin = new Alert(Alert.AlertType.CONFIRMATION);
        
        messageFin.initOwner(this.secondStage);
        
        messageFin.setTitle("Partie finie");
        
        if(grille.getPartieTerminee() == 1) {
            
            messageFin.setContentText("Partie gagnée ! Voulez-vous rejouer ?");
            
        } else if(grille.getPartieTerminee() == 0) {
            
            messageFin.setContentText("Partie perdue ! Voulez-vous rejouer ?");
            
        }

        Optional<ButtonType> result = messageFin.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            
            this.controleur.messageFinOk();
            
        } else {
         
            this.controleur.messageFinCancel();
            
        }
        
    }
    
    /**
     * Retourne au menu principal
     */
    public void retourMenuPrincipal() {
            
        premierStage.show();

        secondStage.close();
    
    }
    
}
