package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link IndexedCollection#removeAll(Collection)} for a non-unique index.
 *
 * <p>The collection under test is an {@link IndexedCollection} decorating an
 * {@link ArrayList}. Each modification is mirrored on a plain {@code ArrayList}
 * ("confirmed"), which is known to obey the {@link Collection} contract. After
 * every modification {@link #verify()} asserts the decorator still holds exactly
 * the same elements as the confirmed collection.</p>
 */
public class IndexedCollectionTest_testCollectionRemoveAll {

    /** Maps a numeric string element to its {@link Integer} index key. */
    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Elements contained in a full collection. */
    private static String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    /** Elements guaranteed not to be contained in a full collection. */
    private static String[] getOtherElements() {
        return new String[] { "9", "88", "678", "87", "98", "78", "99" };
    }

    /** The IndexedCollection under test. */
    private Collection<String> collection;

    /** Plain ArrayList mirroring the expected contents of {@link #collection}. */
    private Collection<String> confirmed;

    /** Wraps the given list in a non-unique IndexedCollection keyed by integer value. */
    private static Collection<String> decorate(final Collection<String> list) {
        return IndexedCollection.nonUniqueIndexedCollection(list, new IntegerTransformer());
    }

    /** Resets both collections to an empty state. */
    private void resetEmpty() {
        collection = decorate(new ArrayList<>());
        confirmed = new ArrayList<>();
    }

    /** Resets both collections to a full state containing {@link #getFullElements()}. */
    private void resetFull() {
        collection = decorate(new ArrayList<>(asList(getFullElements())));
        confirmed = new ArrayList<>(asList(getFullElements()));
    }

    /**
     * Asserts that {@link #collection} holds exactly the same elements (as a
     * multiset, ignoring order) as {@link #confirmed}.
     */
    private void verify() {
        assertEquals(confirmed.size(), collection.size(), "size should match confirmed collection");
        assertEquals(confirmed.isEmpty(), collection.isEmpty(), "isEmpty() should match confirmed collection");

        // Compare as multisets: every element of the collection must consume one
        // matching element from a copy of the confirmed collection, with none left over.
        final List<String> remaining = new ArrayList<>(confirmed);
        for (final String element : collection) {
            assertTrue(remaining.remove(element),
                "collection has an element not present in the confirmed collection: " + element);
        }
        assertTrue(remaining.isEmpty(),
            "confirmed collection has elements missing from the collection: " + remaining);
    }

    /**
     * Tests {@link Collection#removeAll(Collection)} across empty and full collections.
     */
    @Test
    void testCollectionRemoveAll() {
        // removeAll on an empty collection never changes it and returns false.
        resetEmpty();
        assertFalse(collection.removeAll(Collections.EMPTY_SET),
            "empty collection: removeAll of an empty input returns false");
        verify();
        assertFalse(collection.removeAll(new ArrayList<>(collection)),
            "empty collection: removeAll of a (still empty) copy returns false");
        verify();

        // removeAll on a full collection: no change unless the input shares elements.
        resetFull();
        assertFalse(collection.removeAll(Collections.EMPTY_SET),
            "full collection: removeAll of an empty input returns false");
        verify();
        assertFalse(collection.removeAll(asList(getOtherElements())),
            "full collection: removeAll of non-member elements returns false");
        verify();

        // Removing every element clears the collection and returns true.
        assertTrue(collection.removeAll(new HashSet<>(collection)),
            "full collection: removeAll of all its elements returns true");
        confirmed.removeAll(new HashSet<>(confirmed));
        verify();

        // Removing a subset shrinks the collection and drops exactly those elements.
        resetFull();
        final int originalSize = collection.size();
        // For the 7-element fixture this is the sublist {"5", "7", "2"}.
        final Collection<String> subset = asList(getFullElements()).subList(2, 5);
        assertTrue(collection.removeAll(subset), "full collection: removeAll of a subset returns true");
        confirmed.removeAll(subset);
        verify();

        assertTrue(collection.size() < originalSize, "collection should shrink after removeAll");
        for (final String element : subset) {
            assertFalse(collection.contains(element), "removed element should no longer be contained: " + element);
        }
    }
}
