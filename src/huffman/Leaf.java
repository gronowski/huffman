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
public class Leaf extends Tree {
    public char leaf;
    
    public Leaf(char e, int relevanz){
        this.leaf=e;
        setRelevanz(relevanz); //Elternmethode
    }

    public char getLeaf() {
        return leaf;
    }

    public void setLeaf(char leaf) {
        this.leaf = leaf;
    }
    
    
}
