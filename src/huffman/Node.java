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


public class Node extends Tree {
    public Tree left;
    public Tree right;
    
    public Node(Tree l, Tree r, int relevanz){
        left=l;
        right=r;
        setRelevanz(relevanz);
    }
    
    
}
