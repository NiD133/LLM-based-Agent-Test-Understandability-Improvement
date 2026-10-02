package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests full-iteration behaviour of {@link ObjectGraphIterator}.
 *
 * <p>The iterator under test wraps three sub-iterators whose elements, in order, are:
 * <pre>
 *   list1: ["One", "Two", "Three"]
 *   list2: ["Four"]
 *   list3: ["Five", "Six"]
 * </pre>
 * Altogether six elements are reachable through the object graph.
 */
public class ObjectGraphIteratorTest_testFullIterator {

    /** All expected elements in traversal order. */
    protected String[] testArray = { "One", "Two", "Three", "Four", "Five", "Six" };

    protected List<String> list1;
    protected List<String> list2;
    protected List<String> list3;

    /** A list of iterators; the ObjectGraphIterator flattens them into a single sequence. */
    protected List<Iterator<String>> iteratorList;

    /** Returns an ObjectGraphIterator over an empty collection (no elements). */
    public ObjectGraphIterator<Object> makeEmptyIterator() {
        final ArrayList<Object> emptyList = new ArrayList<>();
        return new ObjectGraphIterator<>(emptyList.iterator());
    }

    /**
     * Returns an ObjectGraphIterator whose root is {@link #iteratorList}, producing
     * all six elements from list1, list2, and list3 in sequence.
     */
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

        // Build the top-level iterator whose elements are themselves iterators.
        iteratorList = new ArrayList<>();
        iteratorList.add(list1.iterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(list3.iterator());
    }

    /** Returns true if the iterator under test can contain elements (always true here). */
    public boolean supportsFullIterator() {
        return true;
    }

    /** Hook for subclasses to add cross-verification after each element; no-op here. */
    public void verify() {
        // do nothing
    }

    /**
     * Verifies that a fully-populated ObjectGraphIterator:
     * <ol>
     *   <li>reports {@code hasNext() == true} before any elements are consumed,</li>
     *   <li>returns elements without throwing on the first {@code next()} call,</li>
     *   <li>exhausts all remaining elements without error,</li>
     *   <li>throws {@link NoSuchElementException} once all elements have been consumed, and</li>
     *   <li>returns a non-null {@code toString()} at any point.</li>
     * </ol>
     */
    @Test
    void testFullIterator() {
        if (!supportsFullIterator()) {
            return;
        }

        final Iterator<Object> it = makeObject();

        // The iterator must have at least one element to be a valid "full" iterator.
        assertTrue(it.hasNext(), "hasNext() should return true for at least one element");

        // First call to next() must succeed without throwing.
        assertDoesNotThrow(it::next, "Full iterators must have at least one element");

        // Drain remaining elements, invoking any subclass cross-verification after each.
        while (it.hasNext()) {
            it.next();
            verify();
        }

        // After exhaustion, next() must throw NoSuchElementException.
        assertThrows(
                NoSuchElementException.class,
                () -> it.next(),
                "NoSuchElementException must be thrown when Iterator is exhausted");

        assertNotNull(it.toString());
    }
}
