package com.company.Dehghanipour.Hossein;

public class Following {
    Node node ;
    ListenerThread thread ;

    public Following(Node node , ListenerThread thread){
        this.thread = thread ;
        this.node = node ;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public ListenerThread getThread() {
        return thread;
    }

    public void setThread(ListenerThread thread) {
        this.thread = thread;
    }
}
