/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author agterra
 */
public class PairsGenerator {
    
    private ArrayList< int[] > pairs;
    
    private int[][] grid;
    
    private int longueur;
    
    private int largeur;
    
    private int pairsNumber;
    
    public PairsGenerator() {
        
        this.longueur = 0;
        
        this.largeur = 0;
        
        this.pairs = new ArrayList<>();
        
        this.grid = new int[this.longueur][this.largeur];
        
        this.pairsNumber = 0;
        
    }
    
    public PairsGenerator( int longueur, int largeur, int pairsNumber ) {
        
        this.longueur = longueur;
        
        this.largeur = largeur;
        
        this.pairs = new ArrayList<>();
        
        this.grid = new int[longueur][largeur];
        
        this.pairsNumber = pairsNumber;
        
        for (int i = 0; i < this.longueur ; i++) {
            
            for (int j = 0; j < this.largeur ; j++) {
                
                this.grid[i][j] = -1;
                
            }
            
        }
        
    }
    
    public ArrayList< int[] > generatePairs() {
        
        placePointsRandomly();
        
        /*Random rand = new Random();
        
        int[][] gridCopy = this.grid;
        
        while ( !this.estPlein(gridCopy) ) {
        
            for (int i = 0; i < this.longueur; i++) {

                for (int j = 0; j < this.largeur; j++) {

                    int value = gridCopy[i][j];

                    if( value != -1 ) {

                        int x = rand.nextInt(3) - 1;

                        int y = rand.nextInt(3) - 1;

                        while ( (j + x == j && i + y == i) 
                                || 
                                (j + x < 0)
                                || 
                                (j + x >= this.largeur)
                                || 
                                (i + y < 0 )
                                || 
                                (i + y >= this.longueur)
                                || 
                                (gridCopy[i + y][j + x] != -1)) {

                            x = rand.nextInt(3) - 1;

                            y = rand.nextInt(3) - 1;
                            
                        }

                        System.out.println("base : "+ j + "," + i + " new x = " + (j + x) + " new y = " + (i + y));
                        
                        gridCopy[i + y][j + x] = value;

                    }

                }

            }
            
            this.afficherTableau(gridCopy);
            
            try{Thread.sleep(500);}catch(InterruptedException e){}
            
        }*/
        
        return this.pairs;
        
    }
    
    private void placePointsRandomly() {
        
        Random rand = new Random();
        
        int [] points = new int[2];
        
        for (int i = 0; i < this.pairsNumber ; i ++) {
            
            int x = rand.nextInt(this.largeur);
            
            int y = rand.nextInt(this.longueur);
            
            while ( this.grid[y][x] != -1 ){
                
                x = rand.nextInt(this.largeur);
            
                y = rand.nextInt(this.longueur);
             
            }
            
            this.grid[y][x] = i;
            
            points = new int[2];
            
            points[0] = y;
            
            points[1] = x;
            
            this.pairs.add(points);
            
            int nx = rand.nextInt(this.largeur);
            
            int ny = rand.nextInt(this.longueur);
            
            while( (nx == x && ny == y) || this.grid[ny][nx] != -1 ) {
                
                nx = rand.nextInt(this.largeur);
            
                ny = rand.nextInt(this.longueur);
                            
            }
            
            this.grid[ny][nx] = i;
            
            points = new int[2];
            
            points[0] = ny;
            
            points[1] = nx;
            
            this.pairs.add(points);
            
        }
      /*  
        for (int i = 0; i < this.pairs.size(); i++){
            
            System.out.println(this.pairs.get(i)[0] +" " + this.pairs.get(i)[1]);
            
        }
        
        this.afficherTableau(this.grid);
        
        try{Thread.sleep(5000);}catch(InterruptedException e){}
            */
    }
    
    private boolean estPlein (int[][] tableau) {
        
        for(int i = 0; i < tableau.length ; i++ ){
            
            for (int j = 0; j < tableau[i].length ; j ++) {
                
                if (tableau[i][j] == -1) {
                    
                    return false;
                    
                }
                
            }
            
        }
        
        return true;
        
    }
    
    public void afficherTableau(int[][] tableau){
        
        for(int i = 0; i < tableau.length ; i++ ){
            
            for (int j = 0; j < tableau[i].length ; j ++) {
                
                System.out.print("[" + tableau[i][j] + "]");
                
            }
            
            System.out.println();
            
        }
        
    }
    
}
