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
 * Tests {@link ObjectGraphIterator} traversal over a multi-level object graph
 * (Forest -> Tree -> Branch -> Leaf) using a transformer that navigates each level.
 */
public class ObjectGraphIteratorTest_testIteration_Transformed2 {

    // -------------------------------------------------------------------------
    // Inner domain model: Forest -> Tree -> Branch -> Leaf
    // -------------------------------------------------------------------------

    static class Leaf {
        String color;

        String getColor() { return color; }
        void setColor(final String color) { this.color = color; }
    }

    static class Branch {
        List<Leaf> leaves = new ArrayList<>();

        Leaf addLeaf() {
            leaves.add(new Leaf());
            return leaves.get(leaves.size() - 1);
        }

        Leaf getLeaf(final int index) { return leaves.get(index); }

        Iterator<Leaf> leafIterator() { return leaves.iterator(); }
    }

    static class Tree {
        List<Branch> branches = new ArrayList<>();

        Branch addBranch() {
            branches.add(new Branch());
            return branches.get(branches.size() - 1);
        }

        Branch getBranch(final int index) { return branches.get(index); }

        Iterator<Branch> branchIterator() { return branches.iterator(); }
    }

    static class Forest {
        List<Tree> trees = new ArrayList<>();

        Tree addTree() {
            trees.add(new Tree());
            return trees.get(trees.size() - 1);
        }

        Tree getTree(final int index) { return trees.get(index); }

        Iterator<Tree> treeIterator() { return trees.iterator(); }
    }

    /**
     * Transformer that navigates the Forest->Tree->Branch->Leaf graph.
     * At each level it returns an iterator over the next level's children.
     * At the Leaf level it returns the leaf itself, signalling a result element.
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

    // -------------------------------------------------------------------------
    // Test
    // -------------------------------------------------------------------------

    /**
     * Verifies that ObjectGraphIterator with LeafFinder visits every Leaf in
     * depth-first order across a forest with multiple trees and branches,
     * including trees with no branches and branches with no leaves.
     *
     * Graph structure:
     * <pre>
     *   Forest
     *   ├─ Tree[0]
     *   │   ├─ Branch b1  →  Leaf l1, Leaf l2
     *   │   └─ Branch b2  →  Leaf l3
     *   ├─ Tree[1]          (no branches)
     *   └─ Tree[2]
     *       ├─ Branch b3  →  Leaf l4
     *       ├─ Branch b4    (no leaves)
     *       └─ Branch b5  →  Leaf l5
     * </pre>
     * Expected traversal order: l1, l2, l3, l4, l5
     */
    @Test
    void testIteration_Transformed2() {
        // Build the forest
        final Forest forest = new Forest();
        forest.addTree(); // Tree[0]
        forest.addTree(); // Tree[1] — no branches, produces no leaves
        forest.addTree(); // Tree[2]

        // Tree[0]: two branches with leaves
        final Branch b1 = forest.getTree(0).addBranch();
        final Branch b2 = forest.getTree(0).addBranch();

        // Tree[2]: three branches; b4 is intentionally left empty
        final Branch b3 = forest.getTree(2).addBranch();
        /* Branch b4 = */ forest.getTree(2).addBranch(); // empty branch — iterator must skip it
        final Branch b5 = forest.getTree(2).addBranch();

        // Add leaves
        final Leaf l1 = b1.addLeaf();
        final Leaf l2 = b1.addLeaf();
        final Leaf l3 = b2.addLeaf();
        final Leaf l4 = b3.addLeaf();
        final Leaf l5 = b5.addLeaf();

        // Traverse
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

        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, () -> it.next());
    }
}
