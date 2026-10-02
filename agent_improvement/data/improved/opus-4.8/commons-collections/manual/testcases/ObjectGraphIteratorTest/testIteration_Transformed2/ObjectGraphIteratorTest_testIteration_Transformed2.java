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
 * Verifies that {@link ObjectGraphIterator}, when driven by a {@link Transformer},
 * walks an object graph (Forest &gt; Tree &gt; Branch &gt; Leaf) and yields every
 * {@link Leaf} in depth-first order without building an intermediate list.
 */
public class ObjectGraphIteratorTest_testIteration_Transformed2 {

    /** A leaf - the terminal element the iterator is expected to return. */
    static class Leaf {
        // no state needed; identity is what the test asserts on
    }

    /** A branch holds leaves. */
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

    /** A tree holds branches. */
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

    /** A forest holds trees, the root of the graph. */
    static class Forest {

        private final List<Tree> trees = new ArrayList<>();

        Tree addTree() {
            final Tree tree = new Tree();
            trees.add(tree);
            return tree;
        }

        Tree getTree(final int index) {
            return trees.get(index);
        }

        Iterator<Tree> treeIterator() {
            return trees.iterator();
        }
    }

    /**
     * Transformer that drives the graph descent: each container is mapped to an
     * iterator over its children, while a leaf maps to itself so it is emitted.
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
    void testIteration_Transformed2() {
        // Build a forest of three trees. Only trees 0 and 2 carry branches/leaves;
        // tree 1 stays empty to confirm empty branches are skipped transparently.
        final Forest forest = new Forest();
        final Tree tree0 = forest.addTree();
        forest.addTree(); // tree 1: intentionally left empty
        final Tree tree2 = forest.addTree();

        final Branch b1 = tree0.addBranch();
        final Branch b2 = tree0.addBranch();
        final Branch b3 = tree2.addBranch();
        tree2.addBranch();              // empty branch, expected to be skipped
        final Branch b5 = tree2.addBranch();

        final Leaf l1 = b1.addLeaf();
        final Leaf l2 = b1.addLeaf();
        final Leaf l3 = b2.addLeaf();
        final Leaf l4 = b3.addLeaf();
        final Leaf l5 = b5.addLeaf();

        // Expected depth-first leaf order across the whole forest.
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

        // Iteration is exhausted; further access must fail.
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, () -> it.next());
    }
}
