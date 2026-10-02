package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ObjectGraphIterator} can walk a tree-shaped object graph
 * (Forest &rarr; Tree &rarr; Branch &rarr; Leaf) and, using a {@link Transformer},
 * yield only the {@code Leaf} objects in depth-first order without building any
 * intermediate collection.
 */
public class ObjectGraphIteratorTest_testIteration_Transformed3 {

    /** A leaf, the only object the iterator is expected to return. */
    static class Leaf {
        // No state needed; identity is what the test asserts on.
    }

    /** A branch holds an ordered list of leaves. */
    static class Branch {
        private final List<Leaf> leaves = new ArrayList<>();

        Leaf addLeaf() {
            final Leaf leaf = new Leaf();
            leaves.add(leaf);
            return leaf;
        }

        Iterator<Leaf> leafIterator() {
            return leaves.iterator();
        }
    }

    /** A tree holds an ordered list of branches. */
    static class Tree {
        private final List<Branch> branches = new ArrayList<>();

        Branch addBranch() {
            final Branch branch = new Branch();
            branches.add(branch);
            return branch;
        }

        Iterator<Branch> branchIterator() {
            return branches.iterator();
        }
    }

    /** A forest holds an ordered list of trees. */
    static class Forest {
        private final List<Tree> trees = new ArrayList<>();

        void addTree() {
            trees.add(new Tree());
        }

        Tree getTree(final int index) {
            return trees.get(index);
        }

        Iterator<Tree> treeIterator() {
            return trees.iterator();
        }
    }

    /**
     * Transformer used by the iterator: for each container it returns the iterator
     * over its children; for a {@link Leaf} it returns the leaf itself, signalling
     * that an element has been reached.
     */
    static class LeafFinder implements Transformer<Object, Object> {
        @Override
        public Object transform(final Object input) {
            if (input instanceof Forest) {
                return ((Forest) input).treeIterator();
            }
            if (input instanceof Tree) {
                return ((Tree) input).branchIterator();
            }
            if (input instanceof Branch) {
                return ((Branch) input).leafIterator();
            }
            if (input instanceof Leaf) {
                return input;
            }
            throw new ClassCastException();
        }
    }

    @Test
    void testIteration_Transformed3() {
        // Build a forest of three trees.
        // tree[0]: no branches
        // tree[1]: branch b1 -> [l1, l2], branch b2 -> [l3]
        // tree[2]: branch b3 -> [l4], branch b4 -> [l5], branch b5 -> [] (empty)
        final Forest forest = new Forest();
        forest.addTree();
        forest.addTree();
        forest.addTree();

        final Branch b1 = forest.getTree(1).addBranch();
        final Branch b2 = forest.getTree(1).addBranch();
        final Branch b3 = forest.getTree(2).addBranch();
        final Branch b4 = forest.getTree(2).addBranch();
        forest.getTree(2).addBranch(); // empty branch, contributes no leaves

        final Leaf l1 = b1.addLeaf();
        final Leaf l2 = b1.addLeaf();
        final Leaf l3 = b2.addLeaf();
        final Leaf l4 = b3.addLeaf();
        final Leaf l5 = b4.addLeaf();

        // The iterator should return exactly the five leaves, in depth-first order.
        final Iterator<Object> it = new ObjectGraphIterator<>(forest, new LeafFinder());

        assertTrue(it.hasNext());
        assertSame(l1, it.next());
        assertTrue(it.hasNext());
        assertSame(l2, it.next());
        assertTrue(it.hasNext());
        assertSame(l3, it.next());
        assertTrue(it.hasNext());
        assertSame(l4, it.next());
        assertTrue(it.hasNext());
        assertSame(l5, it.next());

        // No more leaves: hasNext() is false and next() throws.
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, () -> it.next());
    }
}
