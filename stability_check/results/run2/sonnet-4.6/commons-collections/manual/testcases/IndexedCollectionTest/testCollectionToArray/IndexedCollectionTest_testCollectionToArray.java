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

    // Parses each string element as its integer value to use as the index key.
    private static final Transformer<String, Integer> INDEX_BY_INTEGER_VALUE = Integer::valueOf;

    // Strings used to populate the "full" collection; each must be parseable as an integer.
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    // Collection under test: an IndexedCollection wrapping an ArrayList.
    private Collection<String> collection;

    // Plain ArrayList that tracks the expected state for comparison.
    private Collection<String> confirmed;

    // --- Factory helpers ---

    private Collection<String> makeObject() {
        return IndexedCollection.nonUniqueIndexedCollection(
                new ArrayList<>(), INDEX_BY_INTEGER_VALUE);
    }

    private Collection<String> makeFullCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(
                new ArrayList<>(Arrays.asList(FULL_ELEMENTS)), INDEX_BY_INTEGER_VALUE);
    }

    private Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    private Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(FULL_ELEMENTS));
    }

    // --- State reset helpers ---

    private void resetEmpty() {
        collection = makeObject();
        confirmed  = makeConfirmedCollection();
    }

    private void resetFull() {
        collection = makeFullCollection();
        confirmed  = makeConfirmedFullCollection();
    }

    // --- Test ---

    /**
     * Verifies that {@link Collection#toArray()} returns an array whose elements exactly
     * match those of the IndexedCollection, both in count and content.
     */
    @Test
    void testCollectionToArray() {
        // An empty collection must produce a zero-length array.
        resetEmpty();
        assertEquals(0, collection.toArray().length,
                "Empty IndexedCollection should return an empty array for toArray()");

        // A full collection: the array length must equal the collection size.
        resetFull();
        final Object[] array          = collection.toArray();
        final Object[] confirmedArray = confirmed.toArray();

        assertEquals(array.length, collection.size(),
                "toArray() length should equal the collection size");
        assertEquals(confirmedArray.length, array.length,
                "toArray() length should match the confirmed collection's array length");

        // Every element in the array must be contained in the collection and must match
        // exactly one element in the confirmed array (one-to-one correspondence).
        final boolean[] matchedInConfirmed = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            assertTrue(collection.contains(array[i]),
                    "collection.contains() must be true for every element returned by toArray()");

            boolean foundMatch = false;
            for (int j = 0; j < array.length; j++) {
                if (!matchedInConfirmed[j] && Objects.equals(array[i], confirmedArray[j])) {
                    matchedInConfirmed[j] = true;
                    foundMatch = true;
                    break;
                }
            }
            if (!foundMatch) {
                fail("Element at index " + i + " in toArray() result was not found "
                        + "in the confirmed collection's array");
            }
        }

        // Every element from the confirmed array must have been matched exactly once.
        for (int i = 0; i < matchedInConfirmed.length; i++) {
            assertTrue(matchedInConfirmed[i],
                    "Confirmed element at index " + i + " was not returned by toArray()");
        }
    }
}
