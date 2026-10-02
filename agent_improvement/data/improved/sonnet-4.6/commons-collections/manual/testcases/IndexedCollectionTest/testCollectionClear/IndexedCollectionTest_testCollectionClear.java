package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link IndexedCollection#clear()} via a minimal, self-contained fixture.
 *
 * <p>The test verifies two scenarios:
 * <ol>
 *   <li>Clearing an already-empty collection is a no-op.</li>
 *   <li>Clearing a populated collection leaves it empty and in sync with a
 *       reference {@link ArrayList}.</li>
 * </ol>
 */
public class IndexedCollectionTest_testCollectionClear {

    // -----------------------------------------------------------------------
    // Transformer: converts a numeric String key to its Integer representation,
    // used to build the index inside IndexedCollection.
    // -----------------------------------------------------------------------

    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    // -----------------------------------------------------------------------
    // Test elements
    // -----------------------------------------------------------------------

    /** Distinct numeric strings used to populate the full collection. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    // -----------------------------------------------------------------------
    // State: the collection under test and a plain-ArrayList reference.
    // -----------------------------------------------------------------------

    /** The IndexedCollection being exercised. */
    private Collection<String> collection;

    /** A standard ArrayList that mirrors the expected state of {@link #collection}. */
    private Collection<String> confirmed;

    // -----------------------------------------------------------------------
    // Factory helpers
    // -----------------------------------------------------------------------

    private Collection<String> makeEmptyIndexedCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(
                new ArrayList<>(), new IntegerTransformer());
    }

    private Collection<String> makeFullIndexedCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(
                new ArrayList<>(Arrays.asList(FULL_ELEMENTS)), new IntegerTransformer());
    }

    // -----------------------------------------------------------------------
    // Reset helpers
    // -----------------------------------------------------------------------

    private void resetEmpty() {
        this.collection = makeEmptyIndexedCollection();
        this.confirmed  = new ArrayList<>();
    }

    private void resetFull() {
        this.collection = makeFullIndexedCollection();
        this.confirmed  = new ArrayList<>(Arrays.asList(FULL_ELEMENTS));
    }

    // -----------------------------------------------------------------------
    // Verification
    // -----------------------------------------------------------------------

    /**
     * Asserts that {@link #collection} and {@link #confirmed} contain exactly
     * the same multiset of elements (order-independent).
     */
    private void verify() {
        assertEquals(confirmed.size(), collection.size(),
                "collection size should match confirmed size");
        assertEquals(confirmed.isEmpty(), collection.isEmpty(),
                "collection isEmpty() should match confirmed isEmpty()");

        // Match every element in the test collection against the confirmed values,
        // tracking which confirmed slots have already been matched to handle duplicates.
        final Object[] confirmedValues = confirmed.toArray();
        final boolean[] matched = new boolean[confirmedValues.length];

        for (final Object element : collection) {
            boolean found = false;
            for (int i = 0; i < confirmedValues.length; i++) {
                if (!matched[i] && Objects.equals(element, confirmedValues[i])) {
                    matched[i] = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                fail("collection contains element not present in confirmed: " + element
                        + "\n  collection : " + collection
                        + "\n  confirmed  : " + confirmed);
            }
        }

        for (int i = 0; i < confirmedValues.length; i++) {
            if (!matched[i]) {
                fail("collection is missing element from confirmed: " + confirmedValues[i]
                        + "\n  collection : " + collection
                        + "\n  confirmed  : " + confirmed);
            }
        }
    }

    // -----------------------------------------------------------------------
    // Test
    // -----------------------------------------------------------------------

    @Test
    void testCollectionClear() {
        // Clearing an empty collection should be a harmless no-op.
        resetEmpty();
        collection.clear();
        verify();

        // Clearing a full collection must drain both the collection and its index,
        // leaving it in the same empty state as the reference ArrayList.
        resetFull();
        collection.clear();
        confirmed.clear();
        verify();
    }
}
