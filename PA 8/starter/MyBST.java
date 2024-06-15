import java.util.ArrayList; 

public class MyBST<K extends Comparable<K>, V> {
    MyBSTNode<K, V> root = null;
    int size = 0;

    public int size() {
        return size;
    }

    public V insert(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }

        MyBSTNode<K, V> newNode = new MyBSTNode<>(key, value, null);
        if (root == null) {
            root = newNode;
            size++;
            return null;
        } else {
            MyBSTNode<K, V> current = root;
            MyBSTNode<K, V> parent = null;
            while (current != null) {
                parent = current;
                if (key.compareTo(current.getKey()) < 0) {
                    current = current.getLeft();
                } else if (key.compareTo(current.getKey()) > 0) {
                    current = current.getRight();
                } else {
                    V oldValue = current.getValue();
                    current.setValue(value);
                    return oldValue;
                }
            }
            newNode.setParent(parent);
            if (key.compareTo(parent.getKey()) < 0) {
                parent.setLeft(newNode);
            } else {
                parent.setRight(newNode);
            }
            size++;
            return null;
        }
    }

    public V search(K key) {
        MyBSTNode<K, V> current = root;
        while(current != null) {
            if (key.compareTo(current.getKey()) < 0) {
                current = current.getLeft();
            } else if (key.compareTo(current.getKey()) > 0) {
                current = current.getRight();
            } else {
                return current.getValue();
            }
        }
        return null;
    }
   
    public V remove(K key) {
         MyBSTNode<K, V> nodeToRemove = root;

        while (nodeToRemove != null && !nodeToRemove.getKey().equals(key)) {
            if (key.compareTo(nodeToRemove.getKey()) < 0) {
                nodeToRemove = nodeToRemove.getLeft();
            } else {
                nodeToRemove = nodeToRemove.getRight();
            }
        }

        if (nodeToRemove == null) {
            return null;
        }

        V oldValue = nodeToRemove.getValue();

        if (nodeToRemove.getLeft() == null && nodeToRemove.getRight() == null) {
            if (nodeToRemove == root) {
                root = null;
            } else if (nodeToRemove == nodeToRemove.getParent().getLeft()) {
                nodeToRemove.getParent().setLeft(null);
            } else {
                nodeToRemove.getParent().setRight(null);
            }
        } else if (nodeToRemove.getLeft() != null && nodeToRemove.getRight() == null) {
            if (nodeToRemove == root) {
                root = nodeToRemove.getLeft();
                root.setParent(null);
            } else if (nodeToRemove == nodeToRemove.getParent().getLeft()) {
                nodeToRemove.getParent().setLeft(nodeToRemove.getLeft());
            } else {
                nodeToRemove.getParent().setRight(nodeToRemove.getLeft());
            }
            nodeToRemove.getLeft().setParent(nodeToRemove.getParent());
        } else if (nodeToRemove.getRight() != null && nodeToRemove.getLeft() == null) {
            if (nodeToRemove == root) {
                root = nodeToRemove.getRight();
                root.setParent(null);
            } else if (nodeToRemove == nodeToRemove.getParent().getLeft()) {
                nodeToRemove.getParent().setLeft(nodeToRemove.getRight());
            } else {
                nodeToRemove.getParent().setRight(nodeToRemove.getRight());
            }
            nodeToRemove.getRight().setParent(nodeToRemove.getParent());
        } else {
            MyBSTNode<K, V> successor = nodeToRemove.successor();
            nodeToRemove.setKey(successor.getKey());
            nodeToRemove.setValue(successor.getValue());

            if (successor.getParent().getLeft() == successor) {
                successor.getParent().setLeft(successor.getRight());
            } else {
                successor.getParent().setRight(successor.getRight());
            }
            if (successor.getRight() != null) {
                successor.getRight().setParent(successor.getParent());
            }
        }

        size--;
        return oldValue;
    }


    public ArrayList<MyBSTNode<K, V>> inorder() {
        ArrayList<MyBSTNode<K, V>> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        MyBSTNode<K, V> current = root;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }

        while (current != null) {
            result.add(current);
            current = current.successor();
        }

        return result;

    }

    public MyBST<K, V> copy() {

        MyBST<K, V> newTree = new MyBST<>();
        newTree.root = copyNode(root);
        newTree.size = size;
        return newTree;

    }

    private MyBSTNode<K, V> copyNode(MyBSTNode<K, V> node) {
        if (node == null) {
            return null;
        }

        MyBSTNode<K, V> newNode = new MyBSTNode<K, V>(node.getKey(), node.getValue(), null);
        newNode.setLeft(copyNode(node.getLeft()));
        if (newNode.getLeft() != null) {
            newNode.getLeft().setParent(newNode);            
        }
        newNode.setRight(copyNode(node.getRight()));
        if (newNode.getRight() != null) {
            newNode.getRight().setParent(newNode);
        }
        return newNode;


    }

    static class MyBSTNode<K, V> {
        private static final String TEMPLATE = "Key: %s, Value: %s";
        private static final String NULL_STR = "null";

        private K key;
        private V value;
        private MyBSTNode<K, V> parent;
        private MyBSTNode<K, V> left = null;
        private MyBSTNode<K, V> right = null;

        /**
         * Creates a MyBSTNode<K,V> storing specified data
         *
         * @param key    the key the MyBSTNode<K,V> will
         * @param value  the data the MyBSTNode<K,V> will store
         * @param parent the parent of this node
         */
        public MyBSTNode(K key, V value, MyBSTNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * Return the key stored in the the MyBSTNode<K,V>
         *
         * @return the key stored in the MyBSTNode<K,V>
         */
        public K getKey() {
            return key;
        }

        /**
         * Set the key stored in the MyBSTNode<K,V>
         *
         * @param newKey the key to be stored
         */
        public void setKey(K newKey) {
            this.key = newKey;
        }

        /**
         * Return data stored in the MyBSTNode<K,V>
         *
         * @return the data stored in the MyBSTNode<K,V>
         */
        public V getValue() {
            return value;
        }

        /**
         * Set the data stored in the MyBSTNode<K,V>
         *
         * @param newValue the data to be stored
         */
        public void setValue(V newValue) {
            this.value = newValue;
        }

        /**
         * Return the parent
         *
         * @return the parent
         */
        public MyBSTNode<K, V> getParent() {
            return parent;
        }

        /**
         * Set the parent
         *
         * @param newParent the parent
         */
        public void setParent(MyBSTNode<K, V> newParent) {
            this.parent = newParent;
        }

        /**
         * Return the left child
         *
         * @return left child
         */
        public MyBSTNode<K, V> getLeft() {
            return left;
        }

        /**
         * Set the left child
         *
         * @param newLeft the new left child
         */
        public void setLeft(MyBSTNode<K, V> newLeft) {
            this.left = newLeft;
        }

        /**
         * Return the right child
         *
         * @return right child
         */
        public MyBSTNode<K, V> getRight() {
            return right;
        }

        /**
         * Set the right child
         *
         * @param newRight the new right child
         */
        public void setRight(MyBSTNode<K, V> newRight) {
            this.right = newRight;
        }

        public MyBSTNode<K, V> successor() {
            if (this.right != null) {
                MyBSTNode<K, V> current = this.right;
                while (current.left != null) {
                    current = current.left;
                }
                return current;
            } else {
                MyBSTNode<K, V> current = this;
                MyBSTNode<K, V> parent = this.parent;
                while(parent != null && current == parent.right) {
                    current = parent;
                    parent = parent.parent;
                }
                return parent;
            }
        }

        /**
         * This method compares if two node objects are equal.
         *
         * @param obj The target object that the currect object compares to.
         * @return Boolean value indicates if two node objects are equal
         */
        public boolean equals(Object obj) {
            if (!(obj instanceof MyBSTNode))
                return false;

            MyBSTNode<K, V> comp = (MyBSTNode<K, V>) obj;

            return ((this.getKey() == null ? comp.getKey() == null :
                    this.getKey().equals(comp.getKey()))
                    && (this.getValue() == null ? comp.getValue() == null :
                    this.getValue().equals(comp.getValue())));
        }

        /**
         * This method gives a string representation of node object.
         *
         * @return "Key:Value" that represents the node object
         */
        public String toString() {
            return String.format(
                    TEMPLATE,
                    this.getKey() == null ? NULL_STR : this.getKey(),
                    this.getValue() == null ? NULL_STR : this.getValue());
        }
    }

}
