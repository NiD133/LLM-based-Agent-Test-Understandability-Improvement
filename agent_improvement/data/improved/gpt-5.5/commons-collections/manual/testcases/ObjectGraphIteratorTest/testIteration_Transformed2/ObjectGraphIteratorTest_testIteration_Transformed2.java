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

public class ObjectGraphIteratorTest_testIteration_Transformed2 {

    @Test
    void testIteration_Transformed2() {
        final Forest forest = new Forest();
        forest.addTree();
        forest.addTree();
        forest.addTree();

        final Branch b1 = forest.getTree(0).addBranch();
        final Branch b2 = forest.getTree(0).addBranch();
        final Branch b3 = forest.getTree(2).addBranch();
        forest.getTree(2).addBranch();
        final Branch b5 = forest.getTree(2).addBranch();

        final Leaf l1 = b1.addLeaf();
        final Leaf l2 = b1.addLeaf();
        final Leaf l3 = b2.addLeaf();
        final Leaf l4 = b3.addLeaf();
        final Leaf l5 = b5.addLeaf();

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

    private static final class Forest {
        private final List<Tree> trees = new ArrayList<>();

        private Tree addTree() {
            final Tree tree = new Tree();
            trees.add(tree);
            return tree;
        }

        private Tree getTree(final int index) {
            return trees.get(index);
        }

        private Iterator<Tree> treeIterator() {
            return trees.iterator();
        }
    }

    private static final class Tree {
        private final List<Branch> branches = new ArrayList<>();

        private Branch addBranch() {
            final Branch branch = new Branch();
            branches.add(branch);
            return branch;
        }

        private Iterator<Branch> branchIterator() {
            return branches.iterator();
        }
    }

    private static final class Branch {
        private final List<Leaf> leaves = new ArrayList<>();

        private Leaf addLeaf() {
            final Leaf leaf = new Leaf();
            leaves.add(leaf);
            return leaf;
        }

        private Iterator<Leaf> leafIterator() {
            return leaves.iterator();
        }
    }

    private static final class Leaf {
    }

    private static final class LeafFinder implements Transformer<Object, Object> {

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
}
