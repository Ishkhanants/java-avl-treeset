package set.collections;

import java.util.Iterator;

import set.exceptions.NonexistentItemException;
import set.structures.MyAVLTree;

/**
 * MyTreeSet is a custom implementation of set data structure store items of
 * any type in sorted order.
 *
 * @param <E>
 */
public class MyTreeSet<E extends Comparable<E>> {
    private final MyAVLTree<E> tree = new MyAVLTree<>();
    private int size = 0;

    /**
     * Add item to set if there wasn't such.
     *
     * @param item Item to add
     */
    public void add(E item){
        if(tree.insert(item)){
            ++size;
        } else {
            System.out.print("\nYour set already has such item!\n");
        }
    }

    /**
     * Remove item if there was such.
     *
     * @param item Item to remove
     * @exception NonexistentItemException If there was not such item
     * @see NonexistentItemException
     */
    public void remove(E item){
        if(tree.delete(item)) {
            --size;
        } else {
            throw new NonexistentItemException();
        }
    }

    /**
     * Checks if item is in set.
     *
     * @param item Item to check
     * @return true if item was in set, false otherwise
     */
    public boolean contains(E item){
        return tree.contains(item);
    }

    /**
     * Get size of set.
     *
     * @return size
     */
    public int getSize(){
        return size;
    }

    @Override
    public String toString() {
        Iterator<E> it = tree.iterator();
        StringBuilder treeContent = new StringBuilder("(");

        while(it.hasNext()) {
            treeContent.append(it.next()).append(", ");
        }

        return treeContent.substring(0, treeContent.length() - 2) + ")";
    }
}
