/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ObjectGraphIterator}.
 * <p>
 * The tests exercise the iterator in its two usage modes:
 * </p>
 * <ul>
 *   <li><b>Iterator-of-iterators</b> &mdash; a flat list of iterators is walked in order,
 *       producing every element of every iterator as a single sequence.</li>
 *   <li><b>Transformed object graph</b> &mdash; a single root object is walked using a
 *       {@link Transformer} (here {@link LeafFinder}) that, at each node, either returns the
 *       child iterator to descend into or the leaf value to emit. This is modelled with the
 *       {@code Forest -> Tree -> Branch -> Leaf} hierarchy below.</li>
 * </ul>
 */
class ObjectGraphIteratorTest extends AbstractIteratorTest<Object> {

    // ------------------------------------------------------------------
    // Object-graph model: Forest -> Tree -> Branch -> Leaf
    // ------------------------------------------------------------------

    /** A branch holds an ordered list of leaves. */
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

    /** A forest holds an ordered list of trees; it is the root of the graph. */
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

    /** A leaf is a terminal value in the graph; it carries an optional color. */
    static class Leaf {

        String color;

        String getColor() {
            return color;
        }

        void setColor(final String color) {
            this.color = color;
        }

    }

    /**
     * Transformer used to walk a {@link Forest}. At each node it returns the iterator over the
     * node's children so the {@link ObjectGraphIterator} can descend, except for a {@link Leaf},
     * which is itself the value to emit.
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

    /** A tree holds an ordered list of branches. */
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

    // ------------------------------------------------------------------
    // Fixture
    // ------------------------------------------------------------------

    /**
     * The full sequence of values expected when {@link #list1}, {@link #list2} and {@link #list3}
     * are iterated back to back.
     */
    protected String[] testArray = { "One", "Two", "Three", "Four", "Five", "Six" };

    /** First chunk of {@link #testArray}: "One", "Two", "Three". */
    protected List<String> list1;

    /** Second chunk of {@link #testArray}: "Four". */
    protected List<String> list2;

    /** Third chunk of {@link #testArray}: "Five", "Six". */
    protected List<String> list3;

    /** The three lists above wrapped as a list of iterators, ready to feed the iterator. */
    protected List<Iterator<String>> iteratorList;

    @Override
    public ObjectGraphIterator<Object> makeEmptyIterator() {
        final ArrayList<Object> list = new ArrayList<>();
        return new ObjectGraphIterator<>(list.iterator());
    }

    @Override
    public ObjectGraphIterator<Object> makeObject() {
        setUp();
        return new ObjectGraphIterator<>(iteratorList.iterator());
    }

    @BeforeEach
    public void setUp() {
        list1 = new ArrayList<>();
        list1.add("One");
        list1.add("Two");
        list1.add("Three");
        list2 = new ArrayList<>();
        list2.add("Four");
        list3 = new ArrayList<>();
        list3.add("Five");
        list3.add("Six");
        iteratorList = new ArrayList<>();
        iteratorList.add(list1.iterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(list3.iterator());
    }

    // ------------------------------------------------------------------
    // Assertion helpers
    // ------------------------------------------------------------------

    /**
     * Asserts that {@code it} yields exactly the six values of {@link #testArray}, in order, and
     * is then exhausted. Each value is checked with an interleaved {@code hasNext()} call so that
     * both navigation methods are verified at every step.
     *
     * @param it  the iterator to drain
     */
    private void assertYieldsAllTestValues(final Iterator<Object> it) {
        for (int i = 0; i < testArray.length; i++) {
            assertTrue(it.hasNext());
            assertEquals(testArray[i], it.next());
        }
        assertFalse(it.hasNext());
    }

    /**
     * Asserts that {@code it} yields exactly the given leaves, in order, is then exhausted, and
     * finally throws {@link NoSuchElementException} on one more {@code next()}.
     *
     * @param it  the iterator to drain
     * @param expectedLeaves  the leaves expected in iteration order
     */
    private void assertYieldsLeaves(final Iterator<Object> it, final Leaf... expectedLeaves) {
        for (final Leaf expectedLeaf : expectedLeaves) {
            assertTrue(it.hasNext());
            assertSame(expectedLeaf, it.next());
        }
        assertFalse(it.hasNext());

        assertThrows(NoSuchElementException.class, () -> it.next());
    }

    // ------------------------------------------------------------------
    // Iterator-of-iterators tests (root + null transformer)
    // ------------------------------------------------------------------

    @Test
    void testIteration_IteratorOfIterators() {
        final List<Iterator<String>> iteratorList = new ArrayList<>();
        iteratorList.add(list1.iterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(list3.iterator());
        final Iterator<Object> it = new ObjectGraphIterator<>(iteratorList.iterator(), null);

        assertYieldsAllTestValues(it);
    }

    @Test
    void testIteration_IteratorOfIteratorsWithEmptyIterators() {
        // Empty iterators are interleaved between the populated ones and must be transparently
        // skipped, leaving the same six-value sequence.
        final List<Iterator<String>> iteratorList = new ArrayList<>();
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(list1.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(list3.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        final Iterator<Object> it = new ObjectGraphIterator<>(iteratorList.iterator(), null);

        assertYieldsAllTestValues(it);
    }

    // ------------------------------------------------------------------
    // Root + transformer tests
    // ------------------------------------------------------------------

    @Test
    void testIteration_RootNoTransformer() {
        // With no transformer, a non-iterator root is emitted as the single element.
        final Forest forest = new Forest();
        final Iterator<Object> it = new ObjectGraphIterator<>(forest, null);

        assertTrue(it.hasNext());
        assertSame(forest, it.next());
        assertFalse(it.hasNext());

        assertThrows(NoSuchElementException.class, () -> it.next());
    }

    @Test
    void testIteration_RootNull() {
        // A null root yields an empty iterator.
        final Iterator<Object> it = new ObjectGraphIterator<>(null, null);

        assertFalse(it.hasNext());

        assertThrows(NoSuchElementException.class, () -> it.next());

        assertThrows(IllegalStateException.class, () -> it.remove());
    }

    @Test
    void testIteration_Transformed1() {
        // Smallest graph: Forest -> Tree -> Branch -> single Leaf.
        final Forest forest = new Forest();
        final Leaf l1 = forest.addTree().addBranch().addLeaf();
        final Iterator<Object> it = new ObjectGraphIterator<>(forest, new LeafFinder());

        assertYieldsLeaves(it, l1);
    }

    @Test
    void testIteration_Transformed2() {
        // Graph layout (only leaf-bearing branches shown); empty trees/branches must be skipped:
        //   tree0 -> b1 -> l1, l2
        //         -> b2 -> l3
        //   tree1 -> (no branches)
        //   tree2 -> b3 -> l4
        //         -> b4 -> (no leaves)
        //         -> b5 -> l5
        // Expected leaf order: l1, l2, l3, l4, l5.
        final Forest forest = new Forest();
        forest.addTree();
        forest.addTree();
        forest.addTree();
        final Branch b1 = forest.getTree(0).addBranch();
        final Branch b2 = forest.getTree(0).addBranch();
        final Branch b3 = forest.getTree(2).addBranch();
        /* Branch b4 = */ forest.getTree(2).addBranch();
        final Branch b5 = forest.getTree(2).addBranch();
        final Leaf l1 = b1.addLeaf();
        final Leaf l2 = b1.addLeaf();
        final Leaf l3 = b2.addLeaf();
        final Leaf l4 = b3.addLeaf();
        final Leaf l5 = b5.addLeaf();

        final Iterator<Object> it = new ObjectGraphIterator<>(forest, new LeafFinder());

        assertYieldsLeaves(it, l1, l2, l3, l4, l5);
    }

    @Test
    void testIteration_Transformed3() {
        // Variant of the previous graph with a different leaf distribution:
        //   tree0 -> (no branches)
        //   tree1 -> b1 -> l1, l2
        //         -> b2 -> l3
        //   tree2 -> b3 -> l4
        //         -> b4 -> l5
        //         -> b5 -> (no leaves)
        // Expected leaf order: l1, l2, l3, l4, l5.
        final Forest forest = new Forest();
        forest.addTree();
        forest.addTree();
        forest.addTree();
        final Branch b1 = forest.getTree(1).addBranch();
        final Branch b2 = forest.getTree(1).addBranch();
        final Branch b3 = forest.getTree(2).addBranch();
        final Branch b4 = forest.getTree(2).addBranch();
        /* Branch b5 = */ forest.getTree(2).addBranch();
        final Leaf l1 = b1.addLeaf();
        final Leaf l2 = b1.addLeaf();
        final Leaf l3 = b2.addLeaf();
        final Leaf l4 = b3.addLeaf();
        final Leaf l5 = b4.addLeaf();

        final Iterator<Object> it = new ObjectGraphIterator<>(forest, new LeafFinder());

        assertYieldsLeaves(it, l1, l2, l3, l4, l5);
    }

    // ------------------------------------------------------------------
    // Single iterator-of-iterators constructor tests
    // ------------------------------------------------------------------

    @Test
    void testIteratorConstructor_null_next() {
        // A null root iterator behaves as an empty iterator: next() throws.
        final Iterator<Object> it = new ObjectGraphIterator<>(null);
        assertThrows(NoSuchElementException.class, () -> it.next());
    }

    @Test
    void testIteratorConstructor_null_remove() {
        // remove() before any next() is illegal.
        final Iterator<Object> it = new ObjectGraphIterator<>(null);
        assertThrows(IllegalStateException.class, () -> it.remove());
    }

    @Test
    void testIteratorConstructor_null1() {
        final Iterator<Object> it = new ObjectGraphIterator<>(null);

        assertFalse(it.hasNext());

        assertThrows(NoSuchElementException.class, () -> it.next());

        assertThrows(IllegalStateException.class, () -> it.remove());
    }

    @Test
    void testIteratorConstructorIteration_Empty() {
        final List<Iterator<Object>> iteratorList = new ArrayList<>();
        final Iterator<Object> it = new ObjectGraphIterator<>(iteratorList.iterator());

        assertFalse(it.hasNext());

        assertThrows(NoSuchElementException.class, () -> it.next());

        assertThrows(IllegalStateException.class, () -> it.remove());
    }

    @Test
    void testIteratorConstructorIteration_Simple() {
        final List<Iterator<String>> iteratorList = new ArrayList<>();
        iteratorList.add(list1.iterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(list3.iterator());
        final Iterator<Object> it = new ObjectGraphIterator<>(iteratorList.iterator());

        assertYieldsAllTestValues(it);

        assertThrows(NoSuchElementException.class, () -> it.next());
    }

    @Test
    void testIteratorConstructorIteration_SimpleNoHasNext() {
        // Same sequence as above, but driven purely by next() without consulting hasNext().
        final List<Iterator<String>> iteratorList = new ArrayList<>();
        iteratorList.add(list1.iterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(list3.iterator());
        final Iterator<Object> it = new ObjectGraphIterator<>(iteratorList.iterator());

        for (int i = 0; i < testArray.length; i++) {
            assertEquals(testArray[i], it.next());
        }

        assertThrows(NoSuchElementException.class, () -> it.next());
    }

    @Test
    void testIteratorConstructorIteration_WithEmptyIterators() {
        // Interleaved empty iterators must be skipped, leaving the same six-value sequence.
        final List<Iterator<String>> iteratorList = new ArrayList<>();
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(list1.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        iteratorList.add(list3.iterator());
        iteratorList.add(IteratorUtils.<String>emptyIterator());
        final Iterator<Object> it = new ObjectGraphIterator<>(iteratorList.iterator());

        assertYieldsAllTestValues(it);

        assertThrows(NoSuchElementException.class, () -> it.next());
    }

    @Test
    void testIteratorConstructorRemove() {
        // remove() must delete each returned element from its backing list, emptying all three.
        final List<Iterator<String>> iteratorList = new ArrayList<>();
        iteratorList.add(list1.iterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(list3.iterator());
        final Iterator<Object> it = new ObjectGraphIterator<>(iteratorList.iterator());

        for (int i = 0; i < testArray.length; i++) {
            assertEquals(testArray[i], it.next());
            it.remove();
        }
        assertFalse(it.hasNext());
        assertEquals(0, list1.size());
        assertEquals(0, list2.size());
        assertEquals(0, list3.size());
    }

}
