package modele;


import java.util.Observable;


public class Chronometre extends Observable implements Runnable {

    /**************************************************************************/
    // VARIABLES MEMBRES
    /**************************************************************************/
    
    private int tempsActuel = 0;
    
    /**************************************************************************/
    // FONCTIONS MEMBRES
    /**************************************************************************/
    
    /**
     * Lance un chronomètre
     */
    @Override
    public void run() {
        
        System.out.println("Run");
                
        this.reinitialiserTemps();
        
        this.lancerChronometre( 5 );
        
    }
    
    /**
     * Réinitialise le temps du chronomètre à 0
     */
    public void reinitialiserTemps() {
        
        System.out.println("Reinitialiser le temps");
                
        this.tempsActuel = 0;
        
    }
    
    /**
     * Lance le chronomètre pour un temps donné
     * 
     * @param temps La durée
     */
    public void lancerChronometre(int temps) {
        
        while(tempsActuel < temps) {
            
            try{
                
                System.out.println("time : " + tempsActuel);
                
                Thread.sleep(1000);
                
                tempsActuel++;
                
            }catch (InterruptedException e){
                
                System.err.println("Interruption: " + e.getMessage());
                
            }
            
        }
        
        System.out.println("Observers notified");
        
        super.setChanged();
        
        super.notifyObservers();
        
    }
    
}
