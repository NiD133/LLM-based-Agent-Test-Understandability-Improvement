package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
 * Tests {@link IndexedCollection#add(Object)}: verifies size growth, return value,
 * and element containment for both single-element and cumulative add scenarios.
 */
@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionAdd {

    /**
     * Transforms a String to its Integer value; used as the index key for
     * the {@link IndexedCollection} under test.
     */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** The {@link IndexedCollection} instance being tested. */
    private Collection<String> collection;

    /**
     * A plain {@link ArrayList} that shadows every mutation applied to
     * {@link #collection}, serving as the ground-truth for {@link #verify()}.
     */
    private Collection<String> confirmed;

    /** Element set used to populate a full collection; each value parses to a distinct integer key. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    // -------------------------------------------------------------------------
    // Factory / lifecycle helpers
    // -------------------------------------------------------------------------

    private static Collection<String> makeEmptyIndexedCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(new ArrayList<>(), new IntegerTransformer());
    }

    /**
     * Resets both {@link #collection} and {@link #confirmed} to empty before
     * each sub-scenario inside the test.
     */
    private void resetEmpty() {
        collection = makeEmptyIndexedCollection();
        confirmed  = new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Verification helper
    // -------------------------------------------------------------------------

    /**
     * Asserts that {@link #collection} and {@link #confirmed} contain exactly
     * the same elements (order-insensitive, duplicates counted).
     */
    private void verify() {
        final int expectedSize = confirmed.size();
        assertEquals(expectedSize, collection.size(),
                "Collection size should match confirmed collection's");
        assertEquals(confirmed.isEmpty(), collection.isEmpty(),
                "Collection isEmpty() result should match confirmed collection's");

        // Snapshot the confirmed values and match them one-for-one.
        final Object[] confirmedValues = confirmed.toArray();
        final boolean[] matched = new boolean[expectedSize];

        for (final Object actual : collection) {
            boolean found = false;
            for (int i = 0; i < expectedSize; i++) {
                if (!matched[i] && Objects.equals(actual, confirmedValues[i])) {
                    matched[i] = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                fail("Collection should not contain a value that the confirmed collection does not have: "
                        + actual + "\nTest: " + collection + "\nReal: " + confirmed);
            }
        }

        for (int i = 0; i < expectedSize; i++) {
            if (!matched[i]) {
                fail("Collection should contain all values that are in the confirmed collection"
                        + "\nTest: " + collection + "\nReal: " + confirmed);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Test
    // -------------------------------------------------------------------------

    /**
     * Tests {@link Collection#add(Object)} in two phases:
     *
     * <p><b>Phase 1 – single-element adds:</b> for every element in
     * {@link #FULL_ELEMENTS}, start with a fresh empty collection, add the element,
     * and assert that {@code add()} returned {@code true} and the size is exactly 1.
     *
     * <p><b>Phase 2 – cumulative adds:</b> add all elements to one collection in
     * sequence and assert that after each add the size grows correctly and
     * {@code contains()} returns {@code true} for the newly added element.
     */
    @Test
    void testCollectionAdd() {
        // Phase 1: each element individually on a freshly emptied collection
        for (final String element : FULL_ELEMENTS) {
            resetEmpty();
            final boolean addedToEmpty = collection.add(element);
            confirmed.add(element);
            verify();
            assertTrue(addedToEmpty, "add() on an empty collection must return true");
            assertEquals(1, collection.size(), "Size must be 1 after the very first add");
        }

        // Phase 2: all elements added cumulatively to a single collection
        resetEmpty();
        int expectedSize = 0;
        for (final String element : FULL_ELEMENTS) {
            final boolean added = collection.add(element);
            confirmed.add(element);
            verify();
            if (added) {
                expectedSize++;
            }
            assertEquals(expectedSize, collection.size(),
                    "Collection size should grow after add");
            assertTrue(collection.contains(element),
                    "Collection should contain the element just added");
        }
    }
}
