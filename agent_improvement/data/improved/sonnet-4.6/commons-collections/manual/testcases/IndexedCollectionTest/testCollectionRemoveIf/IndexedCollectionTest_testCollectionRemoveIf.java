package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link IndexedCollection#removeIf(Predicate)} across four scenarios:
 * <ol>
 *   <li>Empty collection with an always-false predicate</li>
 *   <li>Empty collection with an always-true predicate</li>
 *   <li>Full collection with an always-false predicate</li>
 *   <li>Full collection with an always-true predicate (clears it)</li>
 *   <li>Full collection with an element-specific predicate (partial removal)</li>
 * </ol>
 * Each scenario checks the return value and that the index stays in sync with
 * the backing collection via a "confirmed" plain ArrayList.
 */
@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionRemoveIf {

    /** Parses string digits into Integer keys used to index the collection. */
    private static class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Elements used to populate the "full" collection under test. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** The IndexedCollection being exercised. */
    private Collection<String> collection;

    /**
     * A plain ArrayList that mirrors every mutation applied to {@link #collection}.
     * {@link #verify()} asserts the two collections contain the same elements.
     */
    private Collection<String> confirmed;

    // -----------------------------------------------------------------------
    // State-reset helpers
    // -----------------------------------------------------------------------

    private void resetEmpty() {
        collection = IndexedCollection.nonUniqueIndexedCollection(
            new ArrayList<>(), new IntegerTransformer());
        confirmed = new ArrayList<>();
    }

    private void resetFull() {
        collection = IndexedCollection.nonUniqueIndexedCollection(
            new ArrayList<>(Arrays.asList(FULL_ELEMENTS)), new IntegerTransformer());
        confirmed = new ArrayList<>(Arrays.asList(FULL_ELEMENTS));
    }

    /**
     * Asserts that {@link #collection} and {@link #confirmed} contain exactly
     * the same elements (size and mutual containsAll).
     */
    private void verify() {
        assertEquals(confirmed.size(), collection.size(),
            "Collection size should match confirmed collection's");
        assertTrue(confirmed.containsAll(collection),
            "Confirmed collection should contain all elements from the test collection");
        assertTrue(collection.containsAll(confirmed),
            "Test collection should contain all elements from the confirmed collection");
    }

    // -----------------------------------------------------------------------
    // Test
    // -----------------------------------------------------------------------

    @Test
    void testCollectionRemoveIf() {
        // --- Scenario 1 & 2: empty collection ---
        // removeIf on an empty collection always returns false because nothing
        // can be removed, regardless of the predicate.
        resetEmpty();
        assertFalse(collection.removeIf(e -> false),
            "Empty collection removeIf should return false for a predicate that returns only false");
        verify();

        assertFalse(collection.removeIf(e -> true),
            "Empty collection removeIf should return false for a predicate that returns only true");
        verify();

        // --- Scenario 3: full collection, always-false predicate ---
        // Nothing matches, so no elements are removed and the method returns false.
        resetFull();
        assertFalse(collection.removeIf(e -> false),
            "Full collection removeIf should return false for a predicate that returns only false");
        verify();

        // --- Scenario 4: full collection, always-true predicate ---
        // Every element matches, so all are removed and the method returns true.
        assertTrue(collection.removeIf(e -> true),
            "Full collection removeIf should return true for a predicate that returns only true");
        confirmed.removeIf(e -> true);
        verify();

        // --- Scenario 5: full collection, element-specific predicate ---
        // Only elements equal to the middle element are removed; the collection
        // shrinks by exactly that count and no longer contains the target element.
        resetFull();
        final List<String> elements = Arrays.asList(FULL_ELEMENTS);
        final int mid = FULL_ELEMENTS.length / 2;
        final String target = elements.get(mid);
        final int initialSize = collection.size();
        final int targetCount = Collections.frequency(elements, target);
        final Predicate<String> filter = target::equals;

        assertTrue(collection.removeIf(filter),
            "Full collection removeIf should work");
        confirmed.removeIf(filter);
        verify();
        assertEquals(initialSize - targetCount, collection.size(),
            "Collection should shrink after removeIf");
        assertFalse(collection.contains(target),
            "Collection shouldn't contain removed element");
    }
}
