package set.structures;

import java.util.Iterator;
import java.util.Stack;

/**
 * MyAVLTree is a custom implementation of self-balancing binary search tree
 * (BST) with AVL algorithm, which means the difference between heights of left
 * and right subtrees cannot be more than one for all nodes. To not deviate from
 * this idea we augment the standard BST insert and delete operations with some
 * re-balancing. Depending on case we rotate tree to needed direction. It allows
 * us to take O(h) time in most of BST operations, where h is height of tree.
 *
 * @param <E>
 */
public class MyAVLTree<E extends Comparable <E>> implements Iterable<E> {
    private Node<E> root;

    /**
     * An MyAVLTree node, which contains data inserted to tree.
     *
     * @param <E>
     */
    private static final class Node<E> {
        private E data;
        private int balance;
        private int height;
        private Node<E> left;
        private Node<E> right;
        private Node<E> parent;

        Node(E data, Node<E> parent) {
            this.data = data;
            this.parent = parent;
        }
    }

    /**
     * Inserts data to tree if there wasn't such.
     *
     * @param data Data to insert
     * @return true if inserting was successfully performed, false otherwise
     */
    public boolean insert(E data) {
        if (root == null) {
            root = new Node<>(data, null);
            return true;
        }

        Node<E> current = root;

        while (!current.data.equals(data)) {
            Node<E> parent = current;

            boolean goLeft = current.data.compareTo(data) > 0;
            current = goLeft ? current.left : current.right;

            if (current == null) {
                if (goLeft) {
                    parent.left = new Node<>(data, parent);
                } else {
                    parent.right = new Node<>(data, parent);
                }

                setNewBalance(parent);

                return true;
            }
        }

        return false;
    }

    /**
     * Finds and deletes data from tree if there was such.
     *
     * @param dataToDelete Data to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean delete(E dataToDelete) {
        if (root == null) {
            return false;
        }

        Node<E> current = root;

        while (current != null) {
            Node<E> node = current;

            current = dataToDelete.compareTo(node.data) >= 0 ? node.right : node.left;

            if (dataToDelete.equals(node.data)) {
                delete(node);
                return true;
            }
        }

        return false;
    }

    private void delete(Node<E> node) {
        if (node.left == null && node.right == null) {
            if (node.parent == null) {
                root = null;
            } else {
                Node<E> parent = node.parent;

                if (parent.left == node) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }

                setNewBalance(parent);
            }

            return;
        }

        if (node.left != null) {
            Node<E> child = node.left;

            while (child.right != null) {
                child = child.right;
            }

            node.data = child.data;
            delete(child);
        } else {
            Node<E> child = node.right;

            while (child.left != null){
                child = child.left;
            }

            node.data = child.data;
            delete(child);
        }
    }

    /**
     * Checks if data is in tree.
     *
     * @param data Data to be checked
     * @return true if tree contains data, false otherwise
     */
    public boolean contains(E data) {
        return contains(data, root);
    }

    private boolean contains(E data, Node<E> current) {
        if (current == null) {
            return false;
        }

        if (data == current.data) {
            return true;
        }

        if (data.compareTo(current.data) < 0) {
            return contains(data, current.left);
        } else {
            return contains(data, current.right);
        }
    }

    /**
     * Set new balance factor to node after insert or delete operations.
     *
     * @param node Node to be balanced
     */
    private void setNewBalance(Node<E> node) {
        setBalance(node);

        if (node.balance == -2) {
            if (getHeight(node.left.left) >= getHeight(node.left.right)) {
                node = rotateRight(node);
            } else {
                node = rotateLeftThenRight(node);
            }
        } else if (node.balance == 2) {
            if (getHeight(node.right.right) >= getHeight(node.right.left)) {
                node = rotateLeft(node);
            } else {
                node = rotateRightThenLeft(node);
            }
        }

        if (node.parent != null) {
            setNewBalance(node.parent);
        } else {
            root = node;
        }
    }

    /**
     * A utility function to left rotate subtree rooted with node "a".
     *
     * @param a Root of subtree to rotate
     * @return New root of rotated subtree
     */
    private Node<E> rotateLeft(Node<E> a) {
        Node<E> b = a.right;
        b.parent = a.parent;

        a.right = b.left;

        if (a.right != null) {
            a.right.parent = a;
        }

        b.left = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }

        setBalance(a, b);

        return b;
    }

    /**
     * A utility function to right rotate subtree rooted with node "a".
     *
     * @param a Root of subtree to rotate
     * @return New root of rotated subtree
     */
    private Node<E> rotateRight(Node<E> a) {
        Node<E> b = a.left;
        b.parent = a.parent;

        a.left = b.right;

        if (a.left != null) {
            a.left.parent = a;
        }

        b.right = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }

        setBalance(a, b);

        return b;
    }

    /**
     * An auxiliary method for convenience.
     *
     * @param node Root of subtree to rotate
     */
    private Node<E> rotateLeftThenRight(Node<E> node) {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    /**
     * An auxiliary method for convenience.
     *
     * @param node Root of subtree to rotate
     */
    private Node<E> rotateRightThenLeft(Node<E> node) {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    /**
     * Get height of node.
     *
     * @param node Root of subtree
     * @return Height of subtree
     */
    private int getHeight(Node<E> node) {
        if (node == null) {
            return -1;
        }

        return node.height;
    }

    /**
     * Set balance factor to nodes.
     *
     * @param nodes Nodes to be balanced
     */
    @SafeVarargs
    private void setBalance(Node<E> ...nodes) {
        for (Node<E> n : nodes) {
            setNewHeight(n);
            n.balance = getHeight(n.right) - getHeight(n.left);
        }
    }

    /**
     * Set new height of subtree after balancing.
     *
     * @param node Root of subtree
     */
    private void setNewHeight(Node<E> node) {
        if (node != null) {
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new TreeIterator();
    }

    private class TreeIterator implements Iterator<E> {
        Node<E> current = root;
        Stack<Node<E>> stack;

        public TreeIterator() {
            stack = new Stack<>();

            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public E next() {
            Node<E> node = stack.pop();
            E result = node.data;

            if (node.right != null) {
                node = node.right;

                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }

            return result;
        }
    }
}