/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

/**
 *
 * @author t.gronowski
 */
public abstract class Tree {
    private int relevanz;
    
    public int getRelevanz(){
      return relevanz;
    }
    
    public void setRelevanz(int relevanz){
        this.relevanz=relevanz;
    }
}
