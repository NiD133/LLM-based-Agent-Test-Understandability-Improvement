package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testCollectionToArray {

    /** Transforms a numeric string element into its Integer key for indexing. */
    static class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** The IndexedCollection instance under test. */
    private Collection<String> collection;

    /** A plain ArrayList that mirrors what the collection should contain, used for verification. */
    private Collection<String> confirmed;

    /** Elements used to populate a "full" collection for testing. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    // --- Fixture setup helpers ---

    private Collection<String> makeEmptyIndexedCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(new ArrayList<>(), new IntegerTransformer());
    }

    private Collection<String> makeFullIndexedCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(
                new ArrayList<>(Arrays.asList(FULL_ELEMENTS)), new IntegerTransformer());
    }

    private void resetEmpty() {
        this.collection = makeEmptyIndexedCollection();
        this.confirmed = new ArrayList<>();
    }

    private void resetFull() {
        this.collection = makeFullIndexedCollection();
        this.confirmed = new ArrayList<>(Arrays.asList(FULL_ELEMENTS));
    }

    // --- Test ---

    /**
     * Verifies that {@link Collection#toArray()} returns an array whose elements
     * match the collection's contents exactly (same elements, same count).
     */
    @Test
    void testCollectionToArray() {
        // An empty IndexedCollection must return a zero-length array
        resetEmpty();
        assertEquals(0, collection.toArray().length,
                "Empty collection should return empty array for toArray");

        // A full IndexedCollection's toArray result must match its confirmed counterpart
        resetFull();
        final Object[] array = collection.toArray();
        assertEquals(array.length, collection.size(),
                "toArray length should equal the collection size");

        final Object[] confirmedArray = confirmed.toArray();
        assertEquals(confirmedArray.length, array.length,
                "toArray length should equal the confirmed collection's array length");

        // Each element returned by toArray must exist in the collection and correspond
        // one-to-one with an element from the confirmed array (accounts for duplicates).
        final boolean[] matched = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            assertTrue(collection.contains(array[i]),
                    "Collection should contain each element returned by toArray");

            boolean matchFound = false;
            for (int j = 0; j < array.length; j++) {
                if (!matched[j] && Objects.equals(array[i], confirmedArray[j])) {
                    matched[j] = true;
                    matchFound = true;
                    break;
                }
            }
            if (!matchFound) {
                fail("Element " + array[i] + " at index " + i
                        + " in toArray result was not found in the confirmed collection's array");
            }
        }

        // Every element in the confirmed array must have been matched
        for (final boolean elementMatched : matched) {
            assertTrue(elementMatched,
                    "Collection should return all its elements in toArray");
        }
    }
}
