package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link Iterator#remove()} behaviour of the iterator returned by a
 * non-unique {@link IndexedCollection} of {@link String} values keyed by their
 * integer value.
 *
 * <p>The test keeps a plain {@code confirmed} collection alongside the
 * {@code collection} under test. Each modification is applied to both, and
 * {@link #verify()} then asserts that the two still hold exactly the same
 * elements.</p>
 */
public class IndexedCollectionTest_testCollectionIteratorRemove {

    /** Elements used to build a populated collection. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** Derives the {@link Integer} index key from a {@link String} element by parsing it. */
    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** The decorated collection under test. */
    private Collection<String> collection;

    /** A plain collection mirroring the expected contents of {@link #collection}. */
    private Collection<String> confirmed;

    /** Wraps the given collection in a non-unique {@link IndexedCollection} keyed by integer value. */
    private static Collection<String> decorate(final Collection<String> backing) {
        return IndexedCollection.nonUniqueIndexedCollection(backing, new IntegerTransformer());
    }

    /** Resets both the collection under test and its mirror to empty. */
    private void resetEmpty() {
        collection = decorate(new ArrayList<>());
        confirmed = new ArrayList<>();
    }

    /** Resets both the collection under test and its mirror to the full element set. */
    private void resetFull() {
        collection = decorate(new ArrayList<>(asList(FULL_ELEMENTS)));
        confirmed = new ArrayList<>(asList(FULL_ELEMENTS));
    }

    /** Asserts the collection under test holds exactly the same elements as its mirror, ignoring order. */
    private void verify() {
        assertEquals(confirmed.size(), collection.size(),
                "size should match the confirmed collection");
        assertEquals(confirmed.isEmpty(), collection.isEmpty(),
                "isEmpty() should match the confirmed collection");

        // Match every element against the mirror, removing one occurrence per match
        // so that duplicates and differing iteration orders are both handled correctly.
        final List<String> remaining = new ArrayList<>(confirmed);
        for (final String element : collection) {
            assertTrue(remaining.remove(element),
                    "collection contains an element missing from the confirmed collection: " + element);
        }
        assertTrue(remaining.isEmpty(),
                "collection is missing elements present in the confirmed collection: " + remaining);
    }

    @Test
    public void testCollectionIteratorRemove() {
        // A fresh iterator has not yet returned an element, so remove() is illegal.
        resetEmpty();
        assertThrows(IllegalStateException.class, () -> collection.iterator().remove(),
                "remove() on a fresh iterator should raise IllegalStateException");
        verify();

        // hasNext() must not enable remove() either.
        final Iterator<String> emptyIter = collection.iterator();
        emptyIter.hasNext();
        assertThrows(IllegalStateException.class, emptyIter::remove,
                "remove() before next() should raise IllegalStateException even after hasNext()");
        verify();

        // Removing every element through the iterator drains the collection,
        // staying in sync with the mirror at each step.
        resetFull();
        int expectedSize = collection.size();
        final Iterator<String> iter = collection.iterator();
        while (iter.hasNext()) {
            final String element = iter.next();
            iter.remove();
            confirmed.remove(element);
            verify();
            expectedSize--;
            assertEquals(expectedSize, collection.size(),
                    "collection should shrink by one after iterator.remove()");
        }
        assertTrue(collection.isEmpty(), "collection should be empty after removing every element");

        // A second remove() without an intervening next() is illegal.
        resetFull();
        final Iterator<String> reusedIter = collection.iterator();
        reusedIter.next();
        reusedIter.remove();
        assertThrows(IllegalStateException.class, reusedIter::remove,
                "a second remove() without next() should raise IllegalStateException");
    }
}
