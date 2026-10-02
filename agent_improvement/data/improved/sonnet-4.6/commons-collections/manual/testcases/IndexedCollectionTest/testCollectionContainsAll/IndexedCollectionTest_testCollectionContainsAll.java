package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link IndexedCollection#containsAll(Collection)} for both empty and full collections.
 */
@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionContainsAll {

    /** Transforms a numeric string (e.g. "42") to its Integer value, used as the index key. */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Elements that populate the full collection under test. */
    private static final String[] FULL_ELEMENTS  = { "1", "3", "5", "7", "2", "4", "6" };

    /** Elements that are never present in the full collection. */
    private static final String[] OTHER_ELEMENTS = { "9", "88", "678", "87", "98", "78", "99" };

    /** The IndexedCollection under test. */
    private Collection<String> collection;

    /**
     * A plain ArrayList that mirrors the expected contents of {@link #collection}.
     * Used by {@link #verify()} to confirm containsAll did not mutate the collection.
     */
    private Collection<String> confirmed;

    // ---------------------------------------------------------------------------
    // Factory / setup helpers
    // ---------------------------------------------------------------------------

    private Collection<String> decorateCollection(final Collection<String> source) {
        return IndexedCollection.nonUniqueIndexedCollection(source, new IntegerTransformer());
    }

    /** Resets both collections to an empty state. */
    private void resetEmpty() {
        this.collection = decorateCollection(new ArrayList<>());
        this.confirmed  = new ArrayList<>();
    }

    /** Resets both collections to the full set of elements. */
    private void resetFull() {
        this.collection = decorateCollection(new ArrayList<>(Arrays.asList(FULL_ELEMENTS)));
        this.confirmed  = new ArrayList<>(Arrays.asList(FULL_ELEMENTS));
    }

    /**
     * Asserts that {@link #collection} and {@link #confirmed} contain exactly the same
     * elements (size, isEmpty, and element-by-element match ignoring order).
     * Call this after every containsAll invocation to ensure the collection was not mutated.
     */
    private void verify() {
        assertEquals(confirmed.size(), collection.size(),
                "Collection size should match confirmed collection's");
        assertEquals(confirmed.isEmpty(), collection.isEmpty(),
                "Collection isEmpty() result should match confirmed collection's");

        final Object[] confirmedValues = confirmed.toArray();
        final boolean[] matched = new boolean[confirmedValues.length];

        for (final Object element : collection) {
            boolean matchFound = false;
            for (int i = 0; i < confirmedValues.length; i++) {
                if (!matched[i] && Objects.equals(element, confirmedValues[i])) {
                    matched[i] = true;
                    matchFound = true;
                    break;
                }
            }
            if (!matchFound) {
                fail("Collection contains an element absent from confirmed: " + element
                        + "\nCollection: " + collection + "\nConfirmed: " + confirmed);
            }
        }

        for (int i = 0; i < confirmedValues.length; i++) {
            if (!matched[i]) {
                fail("Collection is missing an element present in confirmed: " + confirmedValues[i]
                        + "\nCollection: " + collection + "\nConfirmed: " + confirmed);
            }
        }
    }

    // ---------------------------------------------------------------------------
    // Test
    // ---------------------------------------------------------------------------

    /**
     * Tests {@link Collection#containsAll(Collection)} across several scenarios:
     * <ul>
     *   <li>empty collection vs. empty/non-empty argument</li>
     *   <li>full collection vs. other/full/partial/duplicate elements</li>
     *   <li>collection containsAll itself</li>
     * </ul>
     * After each containsAll call, {@link #verify()} confirms the collection was not mutated.
     */
    @Test
    void testCollectionContainsAll() {
        // --- Empty collection ---
        resetEmpty();

        Collection<String> col = new HashSet<>();
        assertTrue(collection.containsAll(col),
                "Every Collection should contain all elements of an empty Collection.");

        col.addAll(Arrays.asList(OTHER_ELEMENTS));
        assertFalse(collection.containsAll(col),
                "Empty Collection shouldn't contain all elements of a non-empty Collection.");
        verify(); // containsAll must not mutate the collection

        // --- Full collection vs. other elements (disjoint) ---
        resetFull();
        assertFalse(collection.containsAll(col),
                "Full collection shouldn't contain other elements");

        // Full collection vs. all its own elements
        col.clear();
        col.addAll(Arrays.asList(FULL_ELEMENTS));
        assertTrue(collection.containsAll(col),
                "Full collection should containAll full elements");
        verify(); // containsAll must not mutate the collection

        // Full collection vs. a contiguous slice of its elements
        final int min = FULL_ELEMENTS.length < 4 ? 0 : 2;
        final int max = FULL_ELEMENTS.length == 1 ? 1
                : FULL_ELEMENTS.length <= 5 ? FULL_ELEMENTS.length - 1 : 5;
        col = Arrays.asList(FULL_ELEMENTS).subList(min, max);
        assertTrue(collection.containsAll(col),
                "Full collection should containAll partial full elements");

        // Full collection vs. itself
        assertTrue(collection.containsAll(collection),
                "Full collection should containAll itself");
        verify(); // containsAll must not mutate the collection

        // Full collection vs. a list containing each element twice
        col = new ArrayList<>(Arrays.asList(FULL_ELEMENTS));
        col.addAll(Arrays.asList(FULL_ELEMENTS));
        assertTrue(collection.containsAll(col),
                "Full collection should containAll duplicate full elements");
        verify(); // containsAll must not mutate the collection
    }
}
