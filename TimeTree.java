public class TimeTree extends AVLTree{
    //TREE FOR EACH RUNNER FOR THEIR TIMES
    //THE KEY IS TIME
    //EACH NODE HAS THE MIN TIME OF THEIR CHILDREN
    private Run root;

    public Run find(float key) {
        Run current = root;
        while (current != null) {
            if (current.key == key) {
                break;
            }
            current = current.key < key ? current.rightChild : current.leftChild;
        }
        return current;
    }

    @Override
    private Run insert(Run root, float key) {
        if (root == null) {
            return new Run(key);
        } else if (root.key > key) {
            root.leftChild = insert(root.leftChild, key);
        } else if (root.key < key) {
            root.rightChild = insert(root.rightChild, key);
        } else if (root.key == key) {
            root.addNum();
        }
        return rebalance(root);
    }
}
