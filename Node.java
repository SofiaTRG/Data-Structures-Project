public class Node<T> {

    //needed attributes:
    // ID, MINTime, AVGTime, tree2-3
    Node<T> parent;
    Node<T> leftChild;
    Node<T> middleChild;
    Node<T> rightChild;
    int key;
    int secondKey;
    T value; // Player or Faculty
    Node<T> linkedNode;
    Node<T> prevLinked;
    Node<Player>[] playersArray = null;

    // Constructors
    public Node(Node<T> parent,Node<T> leftChild,Node<T> middleChild,Node<T> rightChild, T value, int key, int secondKey){
        this.parent = parent;
        this.leftChild = leftChild;
        this.middleChild = middleChild;
        this.rightChild = rightChild; //In TwoThreeTree always null
        this.value = value;
        this.key = key;
        this.secondKey = secondKey;
        Node<T> linkedNode;
    }

    public Node(Node<T> parent, Node<T> leftChild,Node<T> middleChild,T value, int key, int secondKey){
        this(parent,leftChild,middleChild,null, value, key,secondKey);
    }
    public Node(Node<T> parent,Node<T> leftChild,T value, int key, int secondKey){
        this(null,parent,leftChild,null,value,key, secondKey);
    }
    public Node(Node<T> parent,T value, int key, int secondKey){
        this(parent,null,null,null,value,key,secondKey);
    }
    public Node(T value, int key, int secondKey){
        this(null,null,null,null,value,key,secondKey);
    }
    public Node(int key, int secondKey){
        this(null,null,null,null,null,key,secondKey);
    }
    public Node() {
        this(null,null, null, null,null,Integer.MIN_VALUE,Integer.MIN_VALUE);
    }


    // Getters and Setters
    public Node<T> getParent(){
        return parent;
    }

    public void setParent(Node<T> parent){
        this.parent=parent;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<T> getMiddleChild() {
        return middleChild;
    }

    public void setMiddleChild(Node<T> middleChild) {
        this.middleChild = middleChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public Node<Player>[] getPlayersArray(){
        return this.playersArray;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }

    public int getKey(){return this.key;}

    public void setKey(int key){this.key=key;}

    public int getSecondKey(){return this.secondKey;}

    public void setSecondKey(int key){this.secondKey=key;}

    public T getValue(){return this.value;}

    public void setValue(T key){this.value=key;}

    public Node<T> getLinked(){return this.linkedNode;}

    public void setLinked(Node<T> newLink){this.linkedNode = newLink;}

    public Node<T> getPrevLinked(){return this.prevLinked;}

    public void setPrevLinked(Node<T> node){this.prevLinked=node;}



    public void addPlayer(Node<Player> playerNode){   // it will always be O(11)
        if(this.playersArray == null){
            this.playersArray = new Node[11];
        }
        for(int i = 0; i<11; i++){
            if(this.playersArray[i] == null){
                playersArray[i] = playerNode;
                break;
            }
        }
    }

    public void removePlayer(int playerID){ // it will always be O(11)
        for(int i = 0; i < 11; i++){
            if(this.playersArray[i] != null) {
                if (this.playersArray[i].getSecondKey() == playerID) {
                    playersArray[i] = null;
                }
            }
        }
    }



}