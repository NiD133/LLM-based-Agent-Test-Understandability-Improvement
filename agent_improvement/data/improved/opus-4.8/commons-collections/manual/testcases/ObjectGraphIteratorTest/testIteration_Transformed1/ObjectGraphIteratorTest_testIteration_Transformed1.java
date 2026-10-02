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
 * Tests that {@link ObjectGraphIterator} can walk a tree-shaped object graph
 * (Forest -> Tree -> Branch -> Leaf) using a {@link Transformer} that maps each
 * intermediate node to an iterator over its children, and yields only the leaves.
 */
public class ObjectGraphIteratorTest_testIteration_Transformed1 {

    /**
     * A Forest holds Trees. The graph here is intentionally minimal:
     * one Forest -> one Tree -> one Branch -> one Leaf.
     */
    static class Forest {
        private final List<Tree> trees = new ArrayList<>();

        Tree addTree() {
            final Tree tree = new Tree();
            trees.add(tree);
            return tree;
        }

        Iterator<Tree> treeIterator() {
            return trees.iterator();
        }
    }

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

    static class Leaf {
        // A terminal node in the graph; no children.
    }

    /**
     * Maps each node of the graph to the iterator over its children, except for
     * a Leaf, which is returned as-is so the ObjectGraphIterator emits it.
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
    void testIteration_Transformed1() {
        // Build a graph with exactly one leaf.
        final Forest forest = new Forest();
        final Leaf onlyLeaf = forest.addTree().addBranch().addLeaf();

        final Iterator<Object> it = new ObjectGraphIterator<>(forest, new LeafFinder());

        // The iterator should yield the single leaf, then be exhausted.
        assertTrue(it.hasNext());
        assertSame(onlyLeaf, it.next());
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, () -> it.next());
    }
}
