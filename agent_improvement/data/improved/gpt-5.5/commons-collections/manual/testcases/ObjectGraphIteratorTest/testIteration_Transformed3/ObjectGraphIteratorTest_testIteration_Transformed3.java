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

    @Test
    void testIteration_Transformed3() {
        final Forest forest = new Forest();
        forest.addTree();
        forest.addTree();
        forest.addTree();

        final Branch firstBranchInSecondTree = forest.getTree(1).addBranch();
        final Branch secondBranchInSecondTree = forest.getTree(1).addBranch();
        final Branch firstBranchInThirdTree = forest.getTree(2).addBranch();
        final Branch secondBranchInThirdTree = forest.getTree(2).addBranch();
        forest.getTree(2).addBranch();

        final Leaf firstLeaf = firstBranchInSecondTree.addLeaf();
        final Leaf secondLeaf = firstBranchInSecondTree.addLeaf();
        final Leaf thirdLeaf = secondBranchInSecondTree.addLeaf();
        final Leaf fourthLeaf = firstBranchInThirdTree.addLeaf();
        final Leaf fifthLeaf = secondBranchInThirdTree.addLeaf();

        final Iterator<Object> it = new ObjectGraphIterator<>(forest, new LeafFinder());
        assertTrue(it.hasNext());
        assertSame(firstLeaf, it.next());
        assertTrue(it.hasNext());
        assertSame(secondLeaf, it.next());
        assertTrue(it.hasNext());
        assertSame(thirdLeaf, it.next());
        assertTrue(it.hasNext());
        assertSame(fourthLeaf, it.next());
        assertTrue(it.hasNext());
        assertSame(fifthLeaf, it.next());
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, () -> it.next());
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

    private static final class Forest {

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

    private static final class Tree {

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

    private static final class Branch {

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

    private static final class Leaf {
    }
}
