package Minesweeper.server;

import java.io.Serializable;
/**
 * 
 * @author filipe
 */
public class DoublyLinkedListPlayers implements Serializable {

    private Node firstNode;
    private Node lastNode;

    /**
     * Insert a node at the tail of the linked-list.
     *
     * @param e
     */
    public void attach(Node n) {
        if (this.firstNode == null) {
            this.firstNode = n;
            this.lastNode = n;
            this.firstNode.setNextNode(n);
        } else {
            Node last = this.lastNode;
            last.setNextNode(n);
            n.setNextNode(this.firstNode);
            this.lastNode = n;
        }
    }

    public Node getNode(Player p) {
        Node aux = this.firstNode;
        while (aux.getPlayer().compareTo(p) != 0 && aux != this.lastNode) {
            aux = aux.getNextNode();
        }
        return aux;
    }

    public Node getCurrentPlayer() {
        Node aux = this.firstNode;
        while (aux.getYourTurn() == false) {
            aux = aux.getNextNode();
        }
        return aux;
    }

    public void passingTheTurnToAnotherPlayer() {
        Node aux = this.firstNode;
        while (aux.getYourTurn() == false) {
            aux = aux.getNextNode();
        }
        aux.setIsYourTurn(false);
        aux.getNextNode().setIsYourTurn(true);
    }

}

class Node implements Serializable{

    private Node nextNode;
    private boolean isYourTurn;
    private Player player;


    public Node(Player p, boolean isYourTurn) {
        this.isYourTurn = isYourTurn;
        this.player = p;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Node getNextNode() {
        return this.nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public void setIsYourTurn(boolean isYourTurn) {
        this.isYourTurn = isYourTurn;
    }

    public boolean getYourTurn() {
        return this.isYourTurn;
    }

    @Override
    public String toString() {
        return "Node{" + "ID: " + this.player + " " + "IsYourTurn: " + this.isYourTurn + "}";
    }

}
