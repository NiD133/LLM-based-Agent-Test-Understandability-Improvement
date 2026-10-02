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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests the LoopingListIterator class.
 */
class LoopingListIteratorTest {

    /**
     * Tests the add method.
     */
    @Test
    void testAdd() {
        List<String> list = mutableList("b", "e", "f");
        LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        loop.add("a");
        assertEquals("b", loop.next());
        loop.reset();
        assertEquals("a", loop.next());
        assertEquals("b", loop.next());

        loop.add("c");
        assertEquals("e", loop.next());
        assertEquals("e", loop.previous());
        assertEquals("c", loop.previous());
        assertEquals("c", loop.next());

        loop.add("d");
        loop.reset();
        assertForwardSequenceAfterReset(loop);

        list = mutableList("b", "e", "f");
        loop = new LoopingListIterator<>(list);

        loop.add("a");
        assertEquals("a", loop.previous());
        loop.reset();
        assertEquals("f", loop.previous());
        assertEquals("e", loop.previous());

        loop.add("d");
        assertEquals("d", loop.previous());

        loop.add("c");
        assertEquals("c", loop.previous());

        loop.reset();
        assertForwardSequenceAfterReset(loop);
    }

    /**
     * Tests constructor exception.
     */
    @Test
    void testConstructorEx() {
        assertThrows(NullPointerException.class, () -> new LoopingListIterator<>(null));
    }

    /**
     * Tests jogging back and forth between two elements, but not over
     * the begin/end boundary of the list.
     */
    @Test
    void testJoggingNotOverBoundary() {
        final List<String> list = Arrays.asList("a", "b");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        loop.reset();
        assertEquals("a", loop.next());
        assertEquals("a", loop.previous());
        assertEquals("a", loop.next());

        assertEquals("b", loop.next());
        assertEquals("b", loop.previous());
        assertEquals("b", loop.next());
    }

    /**
     * Tests jogging back and forth between two elements over the
     * begin/end boundary of the list.
     */
    @Test
    void testJoggingOverBoundary() {
        final List<String> list = Arrays.asList("a", "b");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        assertEquals("b", loop.previous());
        assertEquals("b", loop.next());
        assertEquals("b", loop.previous());

        assertEquals("a", loop.previous());
        assertEquals("a", loop.next());
        assertEquals("a", loop.previous());
    }

    /**
     * Tests whether an empty looping list iterator works.
     */
    @Test
    void testLooping0() {
        final List<Object> list = new ArrayList<>();
        final LoopingListIterator<Object> loop = new LoopingListIterator<>(list);

        assertFalse(loop.hasNext());
        assertFalse(loop.hasPrevious());
        assertThrows(NoSuchElementException.class, () -> loop.next());
        assertThrows(NoSuchElementException.class, () -> loop.previous());
    }

    /**
     * Tests whether a looping list iterator works on a list with only
     * one element.
     */
    @Test
    void testLooping1() {
        final List<String> list = Arrays.asList("a");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());

        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());

        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());

        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());

        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());

        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());
    }

    /**
     * Tests whether a looping list iterator works on a list with two
     * elements.
     */
    @Test
    void testLooping2() {
        final List<String> list = Arrays.asList("a", "b");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());

        assertTrue(loop.hasNext());
        assertEquals("b", loop.next());

        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());

        loop.reset();

        assertTrue(loop.hasPrevious());
        assertEquals("b", loop.previous());

        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());

        assertTrue(loop.hasPrevious());
        assertEquals("b", loop.previous());
    }

    /**
     * Tests nextIndex and previousIndex.
     */
    @Test
    void testNextAndPreviousIndex() {
        final List<String> list = Arrays.asList("a", "b", "c");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        assertEquals(0, loop.nextIndex());
        assertEquals(2, loop.previousIndex());

        assertEquals("a", loop.next());
        assertEquals(1, loop.nextIndex());
        assertEquals(0, loop.previousIndex());

        assertEquals("a", loop.previous());
        assertEquals(0, loop.nextIndex());
        assertEquals(2, loop.previousIndex());

        assertEquals("c", loop.previous());
        assertEquals(2, loop.nextIndex());
        assertEquals(1, loop.previousIndex());

        assertEquals("b", loop.previous());
        assertEquals(1, loop.nextIndex());
        assertEquals(0, loop.previousIndex());

        assertEquals("a", loop.previous());
        assertEquals(0, loop.nextIndex());
        assertEquals(2, loop.previousIndex());
    }

    /**
     * Tests removing an element from a wrapped ArrayList.
     */
    @Test
    void testRemovingElementsAndIteratingBackwards() {
        final List<String> list = mutableList("a", "b", "c");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        assertTrue(loop.hasPrevious());
        assertEquals("c", loop.previous());
        loop.remove();
        assertSize(2, list);

        assertTrue(loop.hasPrevious());
        assertEquals("b", loop.previous());
        loop.remove();
        assertSize(1, list);

        assertTrue(loop.hasPrevious());
        assertEquals("a", loop.previous());
        loop.remove();
        assertSize(0, list);

        assertFalse(loop.hasPrevious());
        assertThrows(NoSuchElementException.class, () -> loop.previous());
    }

    /**
     * Tests removing an element from a wrapped ArrayList.
     */
    @Test
    void testRemovingElementsAndIteratingForward() {
        final List<String> list = mutableList("a", "b", "c");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        assertTrue(loop.hasNext());
        assertEquals("a", loop.next());
        loop.remove();
        assertSize(2, list);

        assertTrue(loop.hasNext());
        assertEquals("b", loop.next());
        loop.remove();
        assertSize(1, list);

        assertTrue(loop.hasNext());
        assertEquals("c", loop.next());
        loop.remove();
        assertSize(0, list);

        assertFalse(loop.hasNext());
        assertThrows(NoSuchElementException.class, () -> loop.next());
    }

    /**
     * Tests the reset method.
     */
    @Test
    void testReset() {
        final List<String> list = Arrays.asList("a", "b", "c");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        assertEquals("a", loop.next());
        assertEquals("b", loop.next());
        loop.reset();
        assertEquals("a", loop.next());
        loop.reset();
        assertEquals("a", loop.next());
        assertEquals("b", loop.next());
        assertEquals("c", loop.next());
        loop.reset();

        assertEquals("c", loop.previous());
        assertEquals("b", loop.previous());
        loop.reset();
        assertEquals("c", loop.previous());
        loop.reset();
        assertEquals("c", loop.previous());
        assertEquals("b", loop.previous());
        assertEquals("a", loop.previous());
    }

    /**
     * Tests using the set method to change elements.
     */
    @Test
    void testSet() {
        final List<String> list = Arrays.asList("q", "r", "z");
        final LoopingListIterator<String> loop = new LoopingListIterator<>(list);

        assertEquals("z", loop.previous());
        loop.set("c");

        loop.reset();
        assertEquals("q", loop.next());
        loop.set("a");

        assertEquals("r", loop.next());
        loop.set("b");

        loop.reset();
        assertEquals("a", loop.next());
        assertEquals("b", loop.next());
        assertEquals("c", loop.next());
    }

    private static void assertForwardSequenceAfterReset(final LoopingListIterator<String> loop) {
        assertEquals("a", loop.next());
        assertEquals("b", loop.next());
        assertEquals("c", loop.next());
        assertEquals("d", loop.next());
        assertEquals("e", loop.next());
        assertEquals("f", loop.next());
        assertEquals("a", loop.next());
    }

    private static void assertSize(final int expectedSize, final List<?> list) {
        assertEquals(expectedSize, list.size());
    }

    private static List<String> mutableList(final String... values) {
        return new ArrayList<>(Arrays.asList(values));
    }

}
