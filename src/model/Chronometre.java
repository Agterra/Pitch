package model;


import java.util.Observable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author agterra
 */
public class Chronometre extends Observable implements Runnable {

    private int tempsActuel = 0;
    
    @Override
    public void run() {
        
        System.out.println("Run");
                
        this.reinitialiserTemps();
        
        this.lancerChronometre( 5 );
        
    }
    
    public void reinitialiserTemps() {
        
        System.out.println("Reinitialiser le temps");
                
        this.tempsActuel = 0;
        
    }
    
    public synchronized void lancerChronometre( int temps ) {
        
        while(tempsActuel < temps) {
            
            try{
                
                System.out.println("time : " + tempsActuel);
                
                Thread.sleep(1000);
                
                tempsActuel++;
                
            }catch (InterruptedException e){
                
                System.err.println("Interruption: " + e.getMessage());
                
            }
            
        }
        
        this.hasChanged();
        
        this.notifyObservers();
        
    }
    
}
