package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link IndexedCollection} correctly rejects add operations when they are
 * not supported. IndexedCollection does support adds, so {@link #isAddSupported()} returns
 * {@code true} and the test body exits immediately — serving as a guard for subclasses
 * that override {@link #isAddSupported()} to return {@code false}.
 */
public class IndexedCollectionTest_testUnsupportedAdd {

    /** Transforms a numeric string to its Integer value, used as the index key. */
    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    private static final String[] FULL_ELEMENTS  = { "1", "3", "5", "7", "2", "4", "6" };
    private static final String[] OTHER_ELEMENTS = { "9", "88", "678", "87", "98", "78", "99" };

    /** The collection instance under test, reset before each scenario. */
    private Collection<String> collection;

    // -----------------------------------------------------------------------
    // Collection factory helpers
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
    // State-reset helpers (mirror AbstractCollectionTest contract)
    // -----------------------------------------------------------------------

    private void resetEmpty() {
        this.collection = makeEmptyIndexedCollection();
    }

    private void resetFull() {
        this.collection = makeFullIndexedCollection();
    }

    /** Returns {@code true} because {@link IndexedCollection} supports add operations. */
    public boolean isAddSupported() {
        return true;
    }

    // -----------------------------------------------------------------------
    // Test
    // -----------------------------------------------------------------------

    /**
     * Verifies that when the collection does <em>not</em> support add operations,
     * both {@link Collection#add} and {@link Collection#addAll} throw
     * {@link UnsupportedOperationException} — and leave the collection unchanged —
     * whether the collection starts empty or full.
     *
     * <p>Because {@link #isAddSupported()} returns {@code true} here, this test
     * exits immediately without executing any assertions.
     */
    @Test
    void testUnsupportedAdd() {
        if (isAddSupported()) {
            return;
        }

        // --- empty collection ---
        resetEmpty();
        assertThrows(UnsupportedOperationException.class,
                () -> collection.add(FULL_ELEMENTS[0]),
                "Empty collection should not support add.");
        assertThrows(UnsupportedOperationException.class,
                () -> collection.addAll(Arrays.asList(FULL_ELEMENTS)),
                "Empty collection should not support addAll.");

        // --- full collection ---
        resetFull();
        assertThrows(UnsupportedOperationException.class,
                () -> collection.add(FULL_ELEMENTS[0]),
                "Full collection should not support add.");
        assertThrows(UnsupportedOperationException.class,
                () -> collection.addAll(Arrays.asList(OTHER_ELEMENTS)),
                "Full collection should not support addAll.");
    }
}
