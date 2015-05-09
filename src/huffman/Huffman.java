/*
 *von: https://youtu.be/z6cCwiP_yjw
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author t.gronowski
 */
public class Huffman {

    private ArrayList<Tree> list;
    private HashMap<String, String> charMap = new HashMap<String, String>();

    //Erzeugen der ArrayList
    public Huffman() {
        list = new ArrayList<Tree>();
    }

    //Einzelne Buchstaben und Relevanz in Leafs füllen
    public void add(char c) {
        Boolean found = false;
        //Herausfinden, ob es Buchstaben schon gibt
        for (int i = 0; i < list.size(); i++) {
            if (((Leaf) list.get(i)).getLeaf() == c) {
                list.get(i).setRelevanz(list.get(i).getRelevanz() + 1);
                found = true;
                System.out.println("Char " + c + " existierte. Neue Relevanz: " + list.get(i).getRelevanz());
                break;
            }
        }

        if (!found) {
            //neues Blatt erzeugen
            Leaf leaf = new Leaf(c, 1);
            list.add(leaf);
            System.out.println("Zu Leaf hinzugefügt " + c);

        }
    }

    private void printtree() {
        System.out.println("Aktueller Baum (Grösse: " + list.size() + ");");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getRelevanz());
        }
        System.out.println("-----------------------------------------------------");
    }

    public Tree createtree() {
        if (list.size() <= 0) {
            System.out.println("Fehler: Liste hat keine Elemente");
            printtree();
            return null;
        }

        if (list.size() > 2) {
            //die Indexe der kleinsten beiden Relevanzen herussauchen
            int lowrel_1 = 0;
            int lowrel_2;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getRelevanz() < list.get(lowrel_1).getRelevanz()) {
                    lowrel_1 = i;
                }
            }

            if (lowrel_1 == 0) {
                lowrel_2 = 1;
            } else {
                lowrel_2 = 0;
            }

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getRelevanz() < list.get(lowrel_2).getRelevanz() && i != lowrel_1) {
                    lowrel_2 = i;
                }
            }

            if (lowrel_1 == lowrel_2) {
                System.out.println("Tödlicher Fehler, zwei gleiche Indizes berechnet!");
                return null;
            }

            System.out.println("Die niedrigen Relevanzen sind: " + lowrel_1 + " und " + lowrel_2);
            //neue Relevanz aus Summe der beiden vorherigen Bäume berechnen

            int relevanz = list.get(lowrel_1).getRelevanz() + list.get(lowrel_2).getRelevanz();

            //Neuen Koten kreieren der auf die beiden Bäume zeigt
            Node node = new Node(
                    list.get(lowrel_1),
                    list.get(lowrel_2),
                    relevanz);

            System.out.println("Relevanz des neuen Knotens_ " + relevanz);
            //neune Knoten in die Liste einfügen
            list.set(lowrel_1, node);
            list.remove(lowrel_2);

            printtree();
            return createtree();

        }
        //Hier wenn Liste nur 2 oder 1 Element enthält
        if (list.size() == 2) {
            int lowrel_1 = 0;
            int lowrel_2 = 1;

            int relevanz = list.get(lowrel_1).getRelevanz() + list.get(lowrel_2).getRelevanz();

            Node node = new Node(
                    list.get(lowrel_1),
                    list.get(lowrel_2),
                    relevanz);

            System.out.println("Relevanz des neuen Knotens_ " + relevanz);

            list.set(lowrel_1, node);
            list.remove(lowrel_2);

            printtree();
            return createtree();

        }
        //Wenn hier angekommen, ist die Grösse der Liste 1   
        printtree();  //rekursiver Aufruf
        System.out.println("Created tree!");
        return list.get(0);

    }

    //was ist path
    public Tree getPos(Tree root, String path) {//gibt Teilbaum nach dem Code aus        
        Tree result = root;
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) != '1' && path.charAt(i) != '0') {
                throw new IllegalArgumentException("Im tree path sind nur ein element");
            }
            if (result instanceof Node) {
                if (path.charAt(i) == '0') {
                    result = ((Node) result).left;
                }
                if (path.charAt(i) == '1') {
                    result = ((Node) result).right;
                }

            } else {
                throw new IllegalArgumentException("Der Pfad kann nicht zum Baum aufgelöst werden");
            }
        }//ende for

        return result;

    }

    //??? löscht nur charMap und erzeugt neue mit setcharMap()
    public HashMap<String, String> getCharMap(Tree root) {
        charMap.clear();
        setCharMap(root, "");
        return charMap;
    }

    // Baum durchlaufen und eine HasMap erzeugen mit Assoziationen Buchstabe = Code
    //tree=> aktueller Teilbaum; path=> Code zum aktuellen Teilbaum
    //füllt Assoziative Map mit buchstabe => code
    //muss irgendwo in Schleife aufgerufen werden wo der Tree travrsiert wird
    //Sonderbarerweise enthält der Wert den Key und den Wert p=001, ist in Variable path
    //wird bei getCharMap() aufgerufen
    private void setCharMap(Tree tree, String path) {
        if (tree instanceof Leaf) {
            charMap.put(String.valueOf(((Leaf) tree).getLeaf()), path);//put= hinzufügen, hier läuft etwas falsch
        }
        if (tree instanceof Node) {
            setCharMap(((Node) tree).left, path + "0");
            setCharMap(((Node) tree).right, path + "1");

        }
    }

    //tree = Baum, input=zu kodierender Text
    public String getCompressedCode(Tree tree, String input) {
        String output = "";
        HashMap<String, String> lib = getCharMap(tree);
        for (int i = 0; i < input.length(); i++) {
            output = output + lib.get(String.valueOf(input.charAt(i)));
            //Debug
            System.out.println("getCompressedCode: " + output);
        }
        return output;
    }

    public void clear() {
        list.clear();
        System.out.println("Cleared list.");
    }

    public String getPlainText(String chiffre) {
        String dechiffre = "";
       //Schlüssel ist Buchstabe
       //Wert wäre Code, ist aber so gespeichert s=000

      //Einzelne Buchstaben von chiffre werden aneinandergehängt, bis ein Code entsteht, der in HashMap vorhanden ist
        StringBuffer teilString = new StringBuffer();
        StringBuffer decrypt = new StringBuffer();

        for (int i = 0; i < chiffre.length(); i++) {
            teilString.append(chiffre.charAt(i));
            //System.out.println(chiffre.charAt(i));
            //falls sich der teilString in der HashMap findet, Buchstabe ausgeben
            System.out.println("TeilSting: " + teilString);
            String tsr = teilString.toString();
            System.out.println("tsr :" + tsr);
          
            //Durch Werte der HashMap durchiterieren
            Iterator ite = charMap.entrySet().iterator();
            while (ite.hasNext()) {      
               
                String tt = ite.next().toString();
                System.out.println("tt " + tt);
                char schluessel = tt.charAt(0);
                String wert = tt.substring(2);

                System.out.println("Teilstring: " + teilString + " Schluessel: " + schluessel + " Wert: " + wert);

                if (tsr.equals(wert)) {
                    dechiffre += schluessel;
                    teilString.delete(0, teilString.length());  //Teilstring für den nächsten Durchgang löschen
                }
            }
        }
        return dechiffre;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Huffman h = new Huffman();
        String satz = "missisippi ist ein fluss";

        //Baum erzeugen
        for (int i = 0; i < satz.length(); i++) {
            h.add(satz.charAt(i));
        }

        //Baum ausgeben
        System.out.println("-----h.createtree() direktaufruf------");
        Tree t = h.createtree();

        System.out.println("-----h.printtree() direktaufruf------");
        h.printtree();

        //Baum in die CharMap kopieren, Assoziation Buchstaben und Code
        //wird schon bei getCharMap aufgerufen
        h.setCharMap(t, "");

        System.out.println("-----Ausdrucken der charMap ------");

        System.out.println("Keys");
        Iterator it = h.charMap.keySet().iterator();
        while (it.hasNext()) {
            System.out.println(it.next());

        }

        System.out.println("Values");
        Iterator ite = h.charMap.entrySet().iterator();
        while (ite.hasNext()) {
            System.out.println(ite.next().toString());

        }

        System.out.println("-----h.getCompressedCode direktaufruf------");
        String chiffre = h.getCompressedCode(t, satz);

        System.out.println("Chiffre: " + chiffre);
        System.out.println("Dechiffre: " + h.getPlainText(chiffre));

    }

}
