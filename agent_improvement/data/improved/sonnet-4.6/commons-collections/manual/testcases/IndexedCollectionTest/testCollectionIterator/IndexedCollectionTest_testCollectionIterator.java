package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionIterator {

    /** Parses a numeric String element to produce an Integer index key. */
    private static class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** The IndexedCollection instance under test. */
    private Collection<String> collection;

    /** A plain ArrayList that mirrors the expected state of {@link #collection}. */
    private Collection<String> confirmed;

    // ---------------------------------------------------------------------------
    // Factory helpers
    // ---------------------------------------------------------------------------

    private Collection<String> decorateCollection(final Collection<String> coll) {
        return IndexedCollection.nonUniqueIndexedCollection(coll, new IntegerTransformer());
    }

    /** Elements used to populate a "full" collection: seven distinct numeric strings. */
    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    public Collection<String> makeObject() {
        return decorateCollection(new ArrayList<>());
    }

    public Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(getFullElements())));
    }

    public Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    public Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(getFullElements()));
    }

    // ---------------------------------------------------------------------------
    // State management
    // ---------------------------------------------------------------------------

    public Collection<String> getCollection() {
        return collection;
    }

    public Collection<String> getConfirmed() {
        return confirmed;
    }

    /** Resets both collection and confirmed to empty state before each scenario. */
    public void resetEmpty() {
        this.collection = makeObject();
        this.confirmed = makeConfirmedCollection();
    }

    /** Resets both collection and confirmed to a full, pre-populated state. */
    public void resetFull() {
        this.collection = makeFullCollection();
        this.confirmed = makeConfirmedFullCollection();
    }

    /**
     * Asserts that {@link #collection} and {@link #confirmed} contain the same elements,
     * accounting for possible duplicates and ordering differences.
     */
    public void verify() {
        final int expectedSize = getConfirmed().size();
        assertEquals(expectedSize, getCollection().size(),
            "Collection size should match confirmed collection's");
        assertEquals(getConfirmed().isEmpty(), getCollection().isEmpty(),
            "Collection isEmpty() result should match confirmed collection's");

        // Snapshot confirmed elements into an array for membership matching
        final Object[] confirmedValues = new Object[expectedSize];
        final Iterator<String> confirmedIter = getConfirmed().iterator();
        int pos = 0;
        while (confirmedIter.hasNext()) {
            confirmedValues[pos++] = confirmedIter.next();
        }

        // Track which confirmed slots have been matched to handle duplicates correctly
        final boolean[] matched = new boolean[expectedSize];
        final Iterator<String> collectionIter = getCollection().iterator();
        while (collectionIter.hasNext()) {
            final Object element = collectionIter.next();
            boolean matchFound = false;
            for (int i = 0; i < expectedSize; i++) {
                if (!matched[i] && Objects.equals(element, confirmedValues[i])) {
                    matched[i] = true;
                    matchFound = true;
                    break;
                }
            }
            if (!matchFound) {
                fail("Collection contains an element not present in confirmed collection: " + element
                    + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }

        for (int i = 0; i < expectedSize; i++) {
            if (!matched[i]) {
                fail("Collection is missing an element from the confirmed collection"
                    + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }
    }

    // ---------------------------------------------------------------------------
    // Test
    // ---------------------------------------------------------------------------

    /**
     * Tests the read-only iteration behaviour of {@link Collection#iterator()}.
     *
     * <p>Covers three scenarios:
     * <ol>
     *   <li>Empty collection – iterator has no next element and throws
     *       {@link NoSuchElementException} on {@code next()}.</li>
     *   <li>Full collection – iterator visits every element reported by the
     *       collection and is exhausted exactly when the collection is exhausted.</li>
     *   <li>Every element returned by the iterator is actually contained in the
     *       collection, and a further {@code next()} call after exhaustion
     *       throws {@link NoSuchElementException}.</li>
     * </ol>
     */
    @Test
    void testCollectionIterator() {
        // Scenario 1: empty collection – iterator must be immediately exhausted
        resetEmpty();
        final Iterator<String> emptyIterator = getCollection().iterator();
        assertFalse(emptyIterator.hasNext(),
            "Iterator for empty collection should have no next element");
        assertThrows(NoSuchElementException.class, emptyIterator::next,
            "Calling next() on an empty-collection iterator should throw NoSuchElementException");
        verify(); // confirm that read-only iteration did not modify the collection

        // Scenario 2: full collection – iterator visits exactly the right number of elements
        resetFull();
        final Iterator<String> fullIterator = getCollection().iterator();
        for (final String element : getCollection()) {
            assertTrue(fullIterator.hasNext(),
                "Iterator should still have elements while the for-each loop has elements");
            fullIterator.next();
        }
        assertFalse(fullIterator.hasNext(),
            "Iterator should be exhausted after visiting every element");

        // Scenario 3: every element returned by next() belongs to the collection;
        //             calling next() after exhaustion must throw NoSuchElementException
        final ArrayList<String> visitedElements = new ArrayList<>();
        final Iterator<String> verifyingIterator = getCollection().iterator();
        for (int i = 0; i < getCollection().size(); i++) {
            final String next = verifyingIterator.next();
            assertTrue(getCollection().contains(next),
                "Each element returned by the iterator must be contained in the collection");
            visitedElements.add(next);
        }
        assertThrows(NoSuchElementException.class, verifyingIterator::next,
            "Calling next() on an exhausted iterator should throw NoSuchElementException");
        verify(); // confirm that read-only iteration did not modify the collection
    }
}
