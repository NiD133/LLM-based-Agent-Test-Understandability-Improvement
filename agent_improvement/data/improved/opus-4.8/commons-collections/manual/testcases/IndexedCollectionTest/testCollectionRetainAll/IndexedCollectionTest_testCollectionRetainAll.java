package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link IndexedCollection#retainAll(Collection)}.
 *
 * <p>The strategy of this test mirrors the classic Apache Commons Collections
 * approach: every mutation is applied to two collections in parallel.</p>
 * <ul>
 *   <li>{@code collection} &mdash; the {@link IndexedCollection} under test.</li>
 *   <li>{@code confirmed} &mdash; a plain {@link ArrayList} that is known to obey
 *       the {@link Collection} contract.</li>
 * </ul>
 * <p>After applying the same change to both, {@link #verify()} asserts that the
 * collection under test still holds exactly the same elements as the confirmed
 * reference, proving the decorator behaves like an ordinary collection.</p>
 */
public class IndexedCollectionTest_testCollectionRetainAll {

    /**
     * Key generator for the index: maps each String element to its Integer value.
     * The test elements are all numeric strings, so this gives every element a
     * distinct key.
     */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Elements that make up a "full" collection; every value is a distinct integer. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** Elements that are guaranteed not to overlap with {@link #FULL_ELEMENTS}. */
    private static final String[] OTHER_ELEMENTS = { "9", "88", "678", "87", "98", "78", "99" };

    /** The IndexedCollection under test. */
    private Collection<String> collection;

    /** A plain collection, known to be contract-correct, used as the reference. */
    private Collection<String> confirmed;

    /** Wraps the given backing collection in a non-unique IndexedCollection. */
    private Collection<String> decorate(final Collection<String> backing) {
        return IndexedCollection.nonUniqueIndexedCollection(backing, new IntegerTransformer());
    }

    /** Resets both collections to empty. */
    private void resetEmpty() {
        collection = decorate(new ArrayList<>());
        confirmed = new ArrayList<>();
    }

    /** Resets both collections to contain {@link #FULL_ELEMENTS}. */
    private void resetFull() {
        collection = decorate(new ArrayList<>(Arrays.asList(FULL_ELEMENTS)));
        confirmed = new ArrayList<>(Arrays.asList(FULL_ELEMENTS));
    }

    /**
     * Asserts that the collection under test holds exactly the same elements as
     * the confirmed reference, ignoring iteration order and accounting for
     * duplicates (each confirmed element must be matched once and only once).
     */
    private void verify() {
        assertEquals(confirmed.size(), collection.size(),
                "Collection size should match confirmed collection's");
        assertEquals(confirmed.isEmpty(), collection.isEmpty(),
                "Collection isEmpty() result should match confirmed collection's");

        final Object[] confirmedValues = confirmed.toArray();
        final boolean[] matched = new boolean[confirmedValues.length];

        for (final Object actual : collection) {
            boolean found = false;
            for (int i = 0; i < confirmedValues.length; i++) {
                if (!matched[i] && Objects.equals(actual, confirmedValues[i])) {
                    matched[i] = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                fail("Collection contains a value not present in the confirmed collection: " + actual
                        + "\nTest: " + collection + "\nReal: " + confirmed);
            }
        }

        for (final boolean wasMatched : matched) {
            if (!wasMatched) {
                fail("Collection is missing a value present in the confirmed collection"
                        + "\nTest: " + collection + "\nReal: " + confirmed);
            }
        }
    }

    /**
     * Tests {@link Collection#retainAll(Collection)} across the empty collection,
     * the full collection, and a variety of argument sets.
     */
    @Test
    void testCollectionRetainAll() {
        final List<String> elements = Arrays.asList(FULL_ELEMENTS);
        final List<String> other = Arrays.asList(OTHER_ELEMENTS);

        // retainAll on an empty collection never changes anything.
        resetEmpty();
        assertFalse(collection.retainAll(Collections.EMPTY_SET),
                "retainAll on empty collection should return false");
        verify();
        assertFalse(collection.retainAll(elements),
                "retainAll on empty collection should return false");
        verify();

        // Retaining nothing clears a full collection.
        resetFull();
        assertTrue(collection.retainAll(Collections.EMPTY_SET),
                "Collection should change when retaining the empty set");
        confirmed.retainAll(Collections.EMPTY_SET);
        verify();

        // Retaining only non-members clears a full collection.
        resetFull();
        assertTrue(collection.retainAll(other),
                "Collection should change when retaining only non-members");
        confirmed.retainAll(other);
        verify();

        // Retaining all current members changes nothing.
        resetFull();
        int size = collection.size();
        assertFalse(collection.retainAll(elements),
                "Collection shouldn't change when retaining all its elements");
        verify();
        assertEquals(size, collection.size(), "Collection size shouldn't change");

        // Retaining a strict subset keeps only that subset.
        // For 7 elements this is elements 2..4 inclusive: {"5", "7", "2"}.
        resetFull();
        final List<String> subset = elements.subList(2, 5);
        assertTrue(collection.retainAll(subset),
                "Collection should change from a partial retainAll");
        confirmed.retainAll(subset);
        verify();
        for (final String element : collection) {
            assertTrue(subset.contains(element), "Collection should only contain retained elements");
        }

        // Retaining the same elements supplied as a Set (no duplicates) changes nothing.
        resetFull();
        final HashSet<String> set = new HashSet<>(elements);
        size = collection.size();
        assertFalse(collection.retainAll(set),
                "Collection shouldn't change when retaining its elements as a Set");
        verify();
        assertEquals(size, collection.size(), "Collection size shouldn't change");
    }
}
