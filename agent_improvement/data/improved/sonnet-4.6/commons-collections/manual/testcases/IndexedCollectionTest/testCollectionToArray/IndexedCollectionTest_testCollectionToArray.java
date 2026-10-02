package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Collection#toArray()} on an {@link IndexedCollection} wrapping
 * an {@link ArrayList}, verifying that the returned array matches the contents
 * of a plain ArrayList used as a reference ("confirmed") collection.
 */
@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionToArray {

    // -----------------------------------------------------------------------
    // Inner helper: transforms a numeric String to its Integer key
    // -----------------------------------------------------------------------

    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    // -----------------------------------------------------------------------
    // State: the collection under test and a plain reference collection
    // -----------------------------------------------------------------------

    /** The IndexedCollection being tested. */
    private Collection<String> collection;

    /** A plain ArrayList that is always kept in sync, used to verify results. */
    private Collection<String> confirmed;

    // -----------------------------------------------------------------------
    // Factory helpers
    // -----------------------------------------------------------------------

    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    private Collection<String> makeEmptyIndexedCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(new ArrayList<>(), new IntegerTransformer());
    }

    private Collection<String> makeFullIndexedCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(
                new ArrayList<>(Arrays.asList(FULL_ELEMENTS)), new IntegerTransformer());
    }

    // -----------------------------------------------------------------------
    // Reset helpers: bring both collections to a known state
    // -----------------------------------------------------------------------

    private void resetEmpty() {
        collection = makeEmptyIndexedCollection();
        confirmed  = new ArrayList<>();
    }

    private void resetFull() {
        collection = makeFullIndexedCollection();
        confirmed  = new ArrayList<>(Arrays.asList(FULL_ELEMENTS));
    }

    // -----------------------------------------------------------------------
    // Test
    // -----------------------------------------------------------------------

    /**
     * Verifies {@link Collection#toArray()} on an IndexedCollection:
     * <ol>
     *   <li>An empty collection returns a zero-length array.</li>
     *   <li>A full collection returns an array whose length equals the
     *       collection size and which contains exactly the same elements
     *       (ignoring order) as the reference ArrayList.</li>
     * </ol>
     */
    @Test
    void testCollectionToArray() {
        // --- empty case ---
        resetEmpty();
        assertEquals(0, collection.toArray().length,
                "Empty collection should return empty array for toArray");

        // --- full case ---
        resetFull();
        final Object[] array          = collection.toArray();
        final Object[] confirmedArray = confirmed.toArray();

        assertEquals(array.length, collection.size(),
                "Full collection toArray should be same size as collection");
        assertEquals(confirmedArray.length, array.length,
                "Length of array from confirmed collection should match the collection's array");

        // Verify that every element in array:
        //   (a) is reported as contained by the collection, and
        //   (b) has a corresponding, not-yet-matched element in confirmedArray.
        //
        // The boolean[] tracks which confirmedArray slots have been claimed,
        // preventing a single confirmed element from satisfying two array entries.
        final boolean[] confirmedUsed = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            assertTrue(collection.contains(array[i]),
                    "Collection should contain element in toArray");

            boolean matchFound = false;
            for (int j = 0; j < confirmedArray.length; j++) {
                if (confirmedUsed[j]) {
                    continue; // slot already claimed by an earlier element
                }
                if (Objects.equals(array[i], confirmedArray[j])) {
                    confirmedUsed[j] = true;
                    matchFound = true;
                    break;
                }
            }
            if (!matchFound) {
                fail("element " + i + " in returned array should be found "
                        + "in the confirmed collection's array");
            }
        }

        for (final boolean slotClaimed : confirmedUsed) {
            assertTrue(slotClaimed, "Collection should return all its elements in toArray");
        }
    }
}
