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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ObjectGraphIterator}, which traverses a nested object graph
 * by following an iterator-of-iterators structure or by applying a
 * {@link Transformer} to navigate from root objects down to leaf values.
 *
 * <p>The domain model used throughout these tests mirrors the example in the
 * {@code ObjectGraphIterator} Javadoc: a {@link Forest} contains {@link Tree}s,
 * each {@link Tree} contains {@link Branch}es, and each {@link Branch} contains
 * {@link Leaf} objects.  The {@link LeafFinder} transformer navigates this
 * hierarchy so that the iterator ultimately yields only {@link Leaf} instances.</p>
 */
class ObjectGraphIteratorTest extends AbstractIteratorTest<Object> {

    /**
     * A branch in the object graph that holds a collection of {@link Leaf} nodes.
     * Used as the third level of the Forest → Tree → Branch → Leaf hierarchy.
     */
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

    /**
     * The top-level container in the object graph, holding a collection of
     * {@link Tree}s. Used as the root object passed to
     * {@link ObjectGraphIterator} in the transformer-based tests.
     */
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

    /**
     * A leaf node — the terminal element of the object graph.
     * {@link ObjectGraphIterator} with {@link LeafFinder} is expected to
     * yield these objects as its iteration results.
     */
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
     * A {@link Transformer} that navigates the Forest → Tree → Branch → Leaf
     * hierarchy.  Given any node in the graph it returns either a child
     * iterator (so the {@link ObjectGraphIterator} recurses deeper) or the
     * node itself when a {@link Leaf} is reached (signalling that the value
     * should be returned to the caller).
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

    /**
     * A tree in the object graph that holds a collection of {@link Branch}es.
     * Used as the second level of the Forest → Tree → Branch → Leaf hierarchy.
     */
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

    /** The six string values spread across three sub-lists, in traversal order. */
    protected String[] testArray = { "One", "Two", "Three", "Four", "Five", "Six" };

    /** Sub-lists whose iterators are combined by the iterator-of-iterators tests. */
    protected List<String> list1;
    protected List<String> list2;
    protected List<String> list3;

    /**
     * A list of iterators over the three sub-lists, used as the root iterator
     * for iterator-of-iterators tests set up in {@link #setUp()}.
     */
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

    /**
     * Populates three sub-lists ("One","Two","Three"), ("Four"), ("Five","Six")
     * and builds {@link #iteratorList} containing an iterator over each.
     */
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

    // -----------------------------------------------------------------------
    // Tests for the (root, transformer) constructor — plain iterator-of-iterators
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("iterates all elements from an iterator of iterators when transformer is null")
    void testIteration_IteratorOfIterators() {
        final List<Iterator<String>> iterators = new ArrayList<>();
        iterators.add(list1.iterator());
        iterators.add(list2.iterator());
        iterators.add(list3.iterator());
        final Iterator<Object> it = new ObjectGraphIterator<>(iterators.iterator(), null);

        for (int i = 0; i < testArray.length; i++) {
            assertTrue(it.hasNext());
            assertEquals(testArray[i], it.next());
        }
        assertFalse(it.hasNext());
    }

    @Test
    @DisplayName("skips empty sub-iterators when transformer is null")
    void testIteration_IteratorOfIteratorsWithEmptyIterators() {
        final List<Iterator<String>> iterators = new ArrayList<>();
        iterators.add(IteratorUtils.<String>emptyIterator());
        iterators.add(list1.iterator());
        iterators.add(IteratorUtils.<String>emptyIterator());
        iterators.add(list2.iterator());
        iterators.add(IteratorUtils.<String>emptyIterator());
        iterators.add(list3.iterator());
        iterators.add(IteratorUtils.<String>emptyIterator());
        final Iterator<Object> it = new ObjectGraphIterator<>(iterators.iterator(), null);

        for (int i = 0; i < testArray.length; i++) {
            assertTrue(it.hasNext());
            assertEquals(testArray[i], it.next());
        }
        assertFalse(it.hasNext());
    }

    @Test
    @DisplayName("returns the root object directly when no transformer is provided")
    void testIteration_RootNoTransformer() {
        final Forest forest = new Forest();
        final Iterator<Object> it = new ObjectGraphIterator<>(forest, null);

        assertTrue(it.hasNext());
        assertSame(forest, it.next());
        assertFalse(it.hasNext());

        assertThrows(NoSuchElementException.class, () -> it.next());
    }

    @Test
    @DisplayName("produces an empty iteration when the root object is null")
    void testIteration_RootNull() {
        final Iterator<Object> it = new ObjectGraphIterator<>(null, null);

        assertFalse(it.hasNext());

        assertThrows(NoSuchElementException.class, () -> it.next());

        assertThrows(IllegalStateException.class, () -> it.remove());
    }

    // -----------------------------------------------------------------------
    // Tests for the (root, transformer) constructor — LeafFinder traversal
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("transformer traverses a single-leaf graph: Forest > Tree > Branch > Leaf")
    void testIteration_Transformed1() {
        // Graph: Forest → Tree[0] → Branch[0] → Leaf[0]
        final Forest forest = new Forest();
        final Leaf l1 = forest.addTree().addBranch().addLeaf();
        final Iterator<Object> it = new ObjectGraphIterator<>(forest, new LeafFinder());

        assertTrue(it.hasNext());
        assertSame(l1, it.next());
        assertFalse(it.hasNext());

        assertThrows(NoSuchElementException.class, () -> it.next());
    }

    @Test
    @DisplayName("transformer traverses a multi-branch graph and skips an empty branch")
    void testIteration_Transformed2() {
        /*
         * Graph structure:
         *
         *            ┌─ Branch b1 ── Leaf l1
         *            │             └─ Leaf l2
         * Tree[0] ───┤
         *            └─ Branch b2 ── Leaf l3
         *
         * Tree[1] (no branches — yields no leaves)
         *
         *            ┌─ Branch b3 ── Leaf l4
         * Tree[2] ───┼─ Branch b4   (no leaves — skipped)
         *            └─ Branch b5 ── Leaf l5
         */
        final Forest forest = new Forest();
        forest.addTree();
        forest.addTree();
        forest.addTree();
        final Branch b1 = forest.getTree(0).addBranch();
        final Branch b2 = forest.getTree(0).addBranch();
        final Branch b3 = forest.getTree(2).addBranch();
        /* b4 has no leaves and should be silently skipped */ forest.getTree(2).addBranch();
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

    @Test
    @DisplayName("transformer skips an empty leading tree and an empty trailing branch")
    void testIteration_Transformed3() {
        /*
         * Graph structure:
         *
         * Tree[0] (no branches — yields no leaves)
         *
         *            ┌─ Branch b1 ── Leaf l1
         * Tree[1] ───┤             └─ Leaf l2
         *            └─ Branch b2 ── Leaf l3
         *
         *            ┌─ Branch b3 ── Leaf l4
         * Tree[2] ───┼─ Branch b4 ── Leaf l5
         *            └─ Branch b5   (no leaves — skipped)
         */
        final Forest forest = new Forest();
        forest.addTree();
        forest.addTree();
        forest.addTree();
        final Branch b1 = forest.getTree(1).addBranch();
        final Branch b2 = forest.getTree(1).addBranch();
        final Branch b3 = forest.getTree(2).addBranch();
        final Branch b4 = forest.getTree(2).addBranch();
        /* b5 has no leaves and should be silently skipped */ forest.getTree(2).addBranch();
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

    // -----------------------------------------------------------------------
    // Tests for the single-argument (Iterator) constructor
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("next() on a null-rooted iterator throws NoSuchElementException")
    void testIteratorConstructor_null_next() {
        final Iterator<Object> it = new ObjectGraphIterator<>(null);
        assertThrows(NoSuchElementException.class, () -> it.next());
    }

    @Test
    @DisplayName("remove() on a null-rooted iterator throws IllegalStateException")
    void testIteratorConstructor_null_remove() {
        final Iterator<Object> it = new ObjectGraphIterator<>(null);
        assertThrows(IllegalStateException.class, () -> it.remove());
    }

    @Test
    @DisplayName("null root iterator: hasNext() is false and next()/remove() throw")
    void testIteratorConstructorIteration_null1() {
        final Iterator<Object> it = new ObjectGraphIterator<>(null);

        assertFalse(it.hasNext());

        assertThrows(NoSuchElementException.class, () -> it.next());

        assertThrows(IllegalStateException.class, () -> it.remove());
    }

    @Test
    @DisplayName("empty iterator list: hasNext() is false and next()/remove() throw")
    void testIteratorConstructorIteration_Empty() {
        final List<Iterator<Object>> emptyIteratorList = new ArrayList<>();
        final Iterator<Object> it = new ObjectGraphIterator<>(emptyIteratorList.iterator());

        assertFalse(it.hasNext());

        assertThrows(NoSuchElementException.class, () -> it.next());

        assertThrows(IllegalStateException.class, () -> it.remove());
    }

    @Test
    @DisplayName("iterates all elements from an iterator of iterators in order")
    void testIteratorConstructorIteration_Simple() {
        final List<Iterator<String>> iterators = new ArrayList<>();
        iterators.add(list1.iterator());
        iterators.add(list2.iterator());
        iterators.add(list3.iterator());
        final Iterator<Object> it = new ObjectGraphIterator<>(iterators.iterator());

        for (int i = 0; i < testArray.length; i++) {
            assertTrue(it.hasNext());
            assertEquals(testArray[i], it.next());
        }
        assertFalse(it.hasNext());

        assertThrows(NoSuchElementException.class, () -> it.next());
    }

    @Test
    @DisplayName("next() works without calling hasNext() first")
    void testIteratorConstructorIteration_SimpleNoHasNext() {
        final List<Iterator<String>> iterators = new ArrayList<>();
        iterators.add(list1.iterator());
        iterators.add(list2.iterator());
        iterators.add(list3.iterator());
        final Iterator<Object> it = new ObjectGraphIterator<>(iterators.iterator());

        for (int i = 0; i < testArray.length; i++) {
            assertEquals(testArray[i], it.next());
        }

        assertThrows(NoSuchElementException.class, () -> it.next());
    }

    @Test
    @DisplayName("skips empty sub-iterators when using the single-argument constructor")
    void testIteratorConstructorIteration_WithEmptyIterators() {
        final List<Iterator<String>> iterators = new ArrayList<>();
        iterators.add(IteratorUtils.<String>emptyIterator());
        iterators.add(list1.iterator());
        iterators.add(IteratorUtils.<String>emptyIterator());
        iterators.add(list2.iterator());
        iterators.add(IteratorUtils.<String>emptyIterator());
        iterators.add(list3.iterator());
        iterators.add(IteratorUtils.<String>emptyIterator());
        final Iterator<Object> it = new ObjectGraphIterator<>(iterators.iterator());

        for (int i = 0; i < testArray.length; i++) {
            assertTrue(it.hasNext());
            assertEquals(testArray[i], it.next());
        }
        assertFalse(it.hasNext());

        assertThrows(NoSuchElementException.class, () -> it.next());
    }

    @Test
    @DisplayName("remove() delegates to the underlying sub-iterator and empties all source lists")
    void testIteratorConstructorRemove() {
        final List<Iterator<String>> iterators = new ArrayList<>();
        iterators.add(list1.iterator());
        iterators.add(list2.iterator());
        iterators.add(list3.iterator());
        final Iterator<Object> it = new ObjectGraphIterator<>(iterators.iterator());

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
