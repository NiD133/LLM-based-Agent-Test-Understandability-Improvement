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

public class ObjectGraphIteratorTest_testIteration_Transformed1 {

    static class Forest {
        List<Tree> trees = new ArrayList<>();

        Tree addTree() {
            trees.add(new Tree());
            return trees.get(trees.size() - 1);
        }

        Iterator<Tree> treeIterator() {
            return trees.iterator();
        }
    }

    static class Tree {
        List<Branch> branches = new ArrayList<>();

        Branch addBranch() {
            branches.add(new Branch());
            return branches.get(branches.size() - 1);
        }

        Iterator<Branch> branchIterator() {
            return branches.iterator();
        }
    }

    static class Branch {
        List<Leaf> leaves = new ArrayList<>();

        Leaf addLeaf() {
            leaves.add(new Leaf());
            return leaves.get(leaves.size() - 1);
        }

        Iterator<Leaf> leafIterator() {
            return leaves.iterator();
        }
    }

    static class Leaf {
    }

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
        final Forest forest = new Forest();
        final Leaf l1 = forest.addTree().addBranch().addLeaf();
        final Iterator<Object> it = new ObjectGraphIterator<>(forest, new LeafFinder());

        assertTrue(it.hasNext());
        assertSame(l1, it.next());
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, () -> it.next());
    }
}
