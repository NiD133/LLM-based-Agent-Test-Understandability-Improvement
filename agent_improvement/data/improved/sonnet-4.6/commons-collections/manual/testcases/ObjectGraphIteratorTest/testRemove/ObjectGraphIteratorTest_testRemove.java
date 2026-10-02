package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the remove() behaviour of {@link ObjectGraphIterator}.
 *
 * <p>The iterator wraps three string lists joined end-to-end:
 * ["One","Two","Three"] + ["Four"] + ["Five","Six"].
 * Each test method obtains a fresh iterator via {@link #makeObject()}.
 */
public class ObjectGraphIteratorTest_testRemove {

    // -----------------------------------------------------------------------
    // Test data: six strings spread across three lists
    // -----------------------------------------------------------------------

    protected List<String> list1; // "One", "Two", "Three"
    protected List<String> list2; // "Four"
    protected List<String> list3; // "Five", "Six"

    /** An iterator-of-iterators that traverses list1, list2, list3 in order. */
    protected List<Iterator<String>> iteratorList;

    // -----------------------------------------------------------------------
    // Lifecycle
    // -----------------------------------------------------------------------

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
    // Factory helpers
    // -----------------------------------------------------------------------

    /** Returns an iterator over an empty collection. */
    public ObjectGraphIterator<Object> makeEmptyIterator() {
        return new ObjectGraphIterator<>(new ArrayList<>().iterator());
    }

    /**
     * Returns a fully populated iterator that flattens list1, list2, and list3
     * via an iterator-of-iterators.
     */
    public ObjectGraphIterator<Object> makeObject() {
        setUp();
        return new ObjectGraphIterator<>(iteratorList.iterator());
    }

    // -----------------------------------------------------------------------
    // Iterator capability flags
    // -----------------------------------------------------------------------

    /** @return true – this iterator supports being empty. */
    public boolean supportsEmptyIterator() {
        return true;
    }

    /** @return true – this iterator supports being full (non-empty). */
    public boolean supportsFullIterator() {
        return true;
    }

    /** @return true – ObjectGraphIterator delegates remove() to the backing iterators. */
    public boolean supportsRemove() {
        return true;
    }

    /** Hook for subclasses to add cross-cutting post-condition checks. */
    public void verify() {
        // intentionally empty
    }

    // -----------------------------------------------------------------------
    // Test
    // -----------------------------------------------------------------------

    /**
     * Verifies the remove() contract of {@link ObjectGraphIterator}:
     * <ol>
     *   <li>If remove is unsupported, every call must throw {@link UnsupportedOperationException}.</li>
     *   <li>Calling remove() <em>before</em> the first next() must throw {@link IllegalStateException}.</li>
     *   <li>Calling remove() <em>once</em> after next() must succeed.</li>
     *   <li>Calling remove() a <em>second time</em> without an intervening next() must throw
     *       {@link IllegalStateException}.</li>
     * </ol>
     */
    @Test
    void testRemove() {
        final Iterator<Object> iterator = makeObject();

        if (!supportsRemove()) {
            // Iterator does not support remove at all – any call must raise UnsupportedOperationException.
            assertThrows(UnsupportedOperationException.class, iterator::remove);
            return;
        }

        // 1. remove() before next() is illegal – no element has been consumed yet.
        assertThrows(IllegalStateException.class, () -> iterator.remove());

        // Allow subclasses to assert any post-condition invariants.
        verify();

        // 2. Advance to the first element, then remove it – this must succeed.
        iterator.next();
        iterator.remove();

        // 3. A second consecutive remove() without an intervening next() must be rejected.
        assertThrows(IllegalStateException.class, () -> iterator.remove());
    }
}
