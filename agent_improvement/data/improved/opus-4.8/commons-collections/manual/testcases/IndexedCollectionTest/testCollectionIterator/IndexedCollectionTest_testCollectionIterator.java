package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests the read-only behaviour of the {@link Iterator} returned by an
 * {@link IndexedCollection}, for both the empty and the fully populated case.
 */
public class IndexedCollectionTest_testCollectionIterator {

    /** Maps each {@link String} element to its {@link Integer} index key. */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Distinct elements used to build a "full" collection. */
    private static String[] fullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    /** Wraps the given backing collection in a non-unique {@link IndexedCollection}. */
    private static Collection<String> indexed(final Collection<String> backing) {
        return IndexedCollection.nonUniqueIndexedCollection(backing, new IntegerTransformer());
    }

    /** The indexed collection under test. */
    private Collection<String> collection;

    /** A plain collection known to match the contract, used as an oracle. */
    private Collection<String> confirmed;

    /** Resets both collections to an empty state. */
    private void resetEmpty() {
        collection = indexed(new ArrayList<>());
        confirmed = new ArrayList<>();
    }

    /** Resets both collections to contain {@link #fullElements()}. */
    private void resetFull() {
        collection = indexed(new ArrayList<>(Arrays.asList(fullElements())));
        confirmed = new ArrayList<>(Arrays.asList(fullElements()));
    }

    /** Verifies that the collection under test still matches the confirmed oracle. */
    private void verify() {
        assertEquals(confirmed.size(), collection.size(),
                "Collection size should match confirmed collection's");
        assertEquals(confirmed.isEmpty(), collection.isEmpty(),
                "Collection isEmpty() result should match confirmed collection's");
        for (final String expected : confirmed) {
            assertTrue(collection.contains(expected),
                    "Collection should contain every confirmed element: " + expected);
        }
    }

    @Test
    void testCollectionIterator() {
        // An empty collection yields an iterator with no elements.
        resetEmpty();
        final Iterator<String> emptyIt = collection.iterator();
        assertFalse(emptyIt.hasNext(), "Iterator for empty Collection shouldn't have next.");
        assertThrows(NoSuchElementException.class, emptyIt::next,
                "Iterator at end of Collection should throw NoSuchElementException when next is called.");
        verify();

        // A full collection yields exactly size() elements before exhaustion.
        resetFull();
        Iterator<String> fullIt = collection.iterator();
        for (final String ignored : collection) {
            assertTrue(fullIt.hasNext(), "Iterator for full collection should haveNext");
            fullIt.next();
        }
        assertFalse(fullIt.hasNext(), "Iterator should be finished");

        // Every element returned by the iterator is reported as contained.
        fullIt = collection.iterator();
        for (int i = 0; i < collection.size(); i++) {
            final String next = fullIt.next();
            assertTrue(collection.contains(next),
                    "Collection should contain element returned by its iterator");
        }
        final Iterator<String> exhausted = fullIt;
        assertThrows(NoSuchElementException.class, exhausted::next,
                "iterator.next() should raise NoSuchElementException after it finishes");
        verify();
    }
}
