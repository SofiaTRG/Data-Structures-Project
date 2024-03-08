public class AVLTree<T> {

    private Node<T> root;

    public Node find(float key) {
        Node current = root;
        while (current != null) {
            if (current.key == key) {
                break;
            }
            current = current.key < key ? current.rightChild : current.leftChild;
        }
        return current;
    }

    public void insert(float key) {
        root = insert(root, key);
    }

    public void delete(float key) {
        root = delete(root, key);
    }

    public Node getRoot() {
        return root;
    }

    public int height() {
        return root == null ? -1 : root.height;
    }

    private Node<T> insert(Node<T> root, float key) {
        if (root == null) {
            return new Node(key);
        } else if (root.key > key) {
            root.leftChild = insert(root.leftChild, key);
        } else if (root.key < key) {
            root.rightChild = insert(root.rightChild, key);
        } else {
            throw new RuntimeException("duplicate Key!");
        }
        return rebalance(root);
    }

    private Node<T> delete(Node<T> node, float key) {
        if (node == null) {
            return node;
        } else if (node.key > key) {
            node.leftChild = delete(node.leftChild, key);
        } else if (node.key < key) {
            node.rightChild = delete(node.rightChild, key);
        } else {
            if (node.leftChild == null || node.rightChild == null) {
                node = (node.leftChild == null) ? node.rightChild : node.leftChild;
            } else {
                Node mostLeftChild = mostLeftChild(node.rightChild);
                node.key = mostLeftChild.key;
                node.rightChild = delete(node.rightChild, node.key);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    private Node<T> mostLeftChild(Node<T> node) {
        Node<T> current = node;
        /* loop down to find the leftmost leaf */
        while (current.leftChild != null) {
            current = current.leftChild;
        }
        return current;
    }

    private Node<T> rebalance(Node<T> z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.rightChild.rightChild) > height(z.rightChild.leftChild)) {
                z = rotateLeft(z);
            } else {
                z.rightChild = rotateRight(z.rightChild);
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (height(z.leftChild.leftChild) > height(z.leftChild.rightChild)) {
                z = rotateRight(z);
            } else {
                z.leftChild = rotateLeft(z.leftChild);
                z = rotateRight(z);
            }
        }
        return z;
    }

    private Node<T> rotateRight(Node<T> y) {
        Node<T> x = y.leftChild;
        Node<T> z = x.rightChild;
        x.rightChild = y;
        y.leftChild = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node<T> rotateLeft(Node<T> y) {
        Node<T> x = y.rightChild;
        Node<T> z = x.leftChild;
        x.leftChild = y;
        y.rightChild = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private void updateHeight(Node<T> n) {
        n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
    }

    private int height(Node<T> n) {
        return n == null ? -1 : n.height;
    }

    public int getBalance(Node<T> n) {
        return (n == null) ? 0 : height(n.rightChild) - height(n.leftChild);
    }
}