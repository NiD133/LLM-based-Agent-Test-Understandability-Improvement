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

public class ObjectGraphIteratorTest_testIteration_Transformed3 {

    static class Leaf {
        String color;

        String getColor() {
            return color;
        }

        void setColor(final String color) {
            this.color = color;
        }
    }

    static class Branch {
        List<Leaf> leaves = new ArrayList<>();

        Leaf addLeaf() {
            leaves.add(new Leaf());
            return getLeaf(leaves.size() - 1);
        }

        Leaf getLeaf(final int index) {
            return leaves.get(index);
        }

        Iterator<Leaf> leafIterator() {
            return leaves.iterator();
        }
    }

    static class Tree {
        List<Branch> branches = new ArrayList<>();

        Branch addBranch() {
            branches.add(new Branch());
            return getBranch(branches.size() - 1);
        }

        Iterator<Branch> branchIterator() {
            return branches.iterator();
        }

        Branch getBranch(final int index) {
            return branches.get(index);
        }
    }

    static class Forest {
        List<Tree> trees = new ArrayList<>();

        Tree addTree() {
            trees.add(new Tree());
            return getTree(trees.size() - 1);
        }

        Tree getTree(final int index) {
            return trees.get(index);
        }

        Iterator<Tree> treeIterator() {
            return trees.iterator();
        }
    }

    // At each level, returns an iterator over the next level down; at Leaf, returns the leaf itself.
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

    /**
     * Verifies depth-first traversal of a Forest -> Tree -> Branch -> Leaf hierarchy.
     *
     * Graph structure:
     *   tree 0: (no branches)
     *   tree 1: b1 -> [l1, l2],  b2 -> [l3]
     *   tree 2: b3 -> [l4],  b4 -> [l5],  b5 (empty)
     *
     * Expected order: l1, l2, l3, l4, l5; then NoSuchElementException.
     */
    @Test
    void testIteration_Transformed3() {
        final Forest forest = new Forest();
        forest.addTree();   // tree 0: no branches
        forest.addTree();   // tree 1
        forest.addTree();   // tree 2
        final Branch b1 = forest.getTree(1).addBranch();
        final Branch b2 = forest.getTree(1).addBranch();
        final Branch b3 = forest.getTree(2).addBranch();
        final Branch b4 = forest.getTree(2).addBranch();
        forest.getTree(2).addBranch();  // b5: empty, no leaves
        final Leaf l1 = b1.addLeaf();
        final Leaf l2 = b1.addLeaf();
        final Leaf l3 = b2.addLeaf();
        final Leaf l4 = b3.addLeaf();
        final Leaf l5 = b4.addLeaf();

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
