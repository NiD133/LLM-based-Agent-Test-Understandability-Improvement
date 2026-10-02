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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testCollectionRetainAll {

    /** Transformer that converts a String to its Integer value, used as the index key. */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    // -----------------------------------------------------------------------
    // Test data
    // -----------------------------------------------------------------------

    /** Elements that populate the "full" collection under test. */
    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    /** Elements that are guaranteed to be absent from the full collection. */
    public String[] getOtherElements() {
        return new String[] { "9", "88", "678", "87", "98", "78", "99" };
    }

    // -----------------------------------------------------------------------
    // Collection factories
    // -----------------------------------------------------------------------

    /** Returns an empty IndexedCollection (non-unique index). */
    public Collection<String> makeObject() {
        return IndexedCollection.nonUniqueIndexedCollection(new ArrayList<>(), new IntegerTransformer());
    }

    /** Returns an IndexedCollection pre-populated with {@link #getFullElements()}. */
    public Collection<String> makeFullCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(
                new ArrayList<>(Arrays.asList(getFullElements())), new IntegerTransformer());
    }

    /** Returns an empty plain collection used as the ground-truth reference. */
    public Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    /** Returns a plain collection pre-populated with {@link #getFullElements()}. */
    public Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(getFullElements()));
    }

    // -----------------------------------------------------------------------
    // State held between reset and verify calls
    // -----------------------------------------------------------------------

    /** The collection under test. */
    private Collection<String> collection;

    /** A plain reference collection that tracks the expected state. */
    private Collection<String> confirmed;

    public Collection<String> getCollection() {
        return collection;
    }

    public Collection<String> getConfirmed() {
        return confirmed;
    }

    /** Resets both collections to empty state. */
    public void resetEmpty() {
        this.collection = makeObject();
        this.confirmed = makeConfirmedCollection();
    }

    /** Resets both collections to the full element set. */
    public void resetFull() {
        this.collection = makeFullCollection();
        this.confirmed = makeConfirmedFullCollection();
    }

    /**
     * Asserts that {@link #collection} and {@link #confirmed} contain the same
     * elements (order-independent, duplicates counted).
     */
    public void verify() {
        final int confirmedSize = confirmed.size();
        assertEquals(confirmedSize, collection.size(),
                "Collection size should match confirmed collection's");
        assertEquals(confirmed.isEmpty(), collection.isEmpty(),
                "Collection isEmpty() result should match confirmed collection's");

        final Object[] confirmedValues = confirmed.toArray();
        final boolean[] matched = new boolean[confirmedSize];

        for (final Object actual : collection) {
            boolean found = false;
            for (int i = 0; i < confirmedSize; i++) {
                if (!matched[i] && Objects.equals(actual, confirmedValues[i])) {
                    matched[i] = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                fail("Collection contains an element not in confirmed: " + actual
                        + "\nTest: " + collection + "\nReal: " + confirmed);
            }
        }

        for (int i = 0; i < confirmedSize; i++) {
            if (!matched[i]) {
                fail("Collection is missing an element that confirmed contains"
                        + "\nTest: " + collection + "\nReal: " + confirmed);
            }
        }
    }

    // -----------------------------------------------------------------------
    // Test
    // -----------------------------------------------------------------------

    /**
     * Tests {@link Collection#retainAll(Collection)} on an IndexedCollection.
     *
     * <p>Scenarios covered:
     * <ol>
     *   <li>retainAll on an empty collection returns {@code false} (nothing changed).
     *   <li>retainAll(emptySet) on a full collection clears it and returns {@code true}.
     *   <li>retainAll(disjoint elements) on a full collection empties it and returns {@code true}.
     *   <li>retainAll(same elements) on a full collection returns {@code false} (nothing changed).
     *   <li>retainAll(subset) on a full collection reduces it to the subset.
     *   <li>retainAll(same elements as a Set) on a full collection returns {@code false}.
     * </ol>
     */
    @Test
    void testCollectionRetainAll() {
        final List<String> fullElements = Arrays.asList(getFullElements());
        final List<String> otherElements = Arrays.asList(getOtherElements());

        // --- empty collection: retainAll never changes it ---
        resetEmpty();
        assertFalse(getCollection().retainAll(Collections.EMPTY_SET),
                "retainAll(emptySet) on empty collection should return false");
        verify();

        assertFalse(getCollection().retainAll(fullElements),
                "retainAll(fullElements) on empty collection should return false");
        verify();

        // --- full collection: retainAll(emptySet) clears it ---
        resetFull();
        assertTrue(getCollection().retainAll(Collections.EMPTY_SET),
                "retainAll(emptySet) on full collection should return true (collection changed)");
        getConfirmed().retainAll(Collections.EMPTY_SET);
        verify();

        // --- full collection: retainAll(disjoint set) clears it ---
        resetFull();
        assertTrue(getCollection().retainAll(otherElements),
                "retainAll(otherElements) on full collection should return true (collection changed)");
        getConfirmed().retainAll(otherElements);
        verify();

        // --- full collection: retainAll(same elements) changes nothing ---
        resetFull();
        int sizeBefore = getCollection().size();
        assertFalse(getCollection().retainAll(fullElements),
                "retainAll(fullElements) on full collection should return false (nothing removed)");
        verify();
        assertEquals(sizeBefore, getCollection().size(),
                "Collection size should not change after retainAll with same elements");

        // --- full collection: retainAll(subset) reduces to that subset ---
        if (getFullElements().length > 1) {
            resetFull();
            sizeBefore = getCollection().size();
            final int min = getFullElements().length < 4 ? 0 : 2;
            final int max = getFullElements().length <= 5 ? getFullElements().length - 1 : 5;
            final List<String> subset = fullElements.subList(min, max);

            assertTrue(getCollection().retainAll(subset),
                    "retainAll(subset) on full collection should return true (collection changed)");
            getConfirmed().retainAll(subset);
            verify();

            for (final String element : getCollection()) {
                assertTrue(subset.contains(element),
                        "Collection should only contain elements from the retained subset");
            }
        }

        // --- full collection: retainAll(same elements as HashSet) changes nothing ---
        resetFull();
        sizeBefore = getCollection().size();
        final HashSet<String> fullElementsAsSet = new HashSet<>(fullElements);
        assertFalse(getCollection().retainAll(fullElementsAsSet),
                "retainAll(fullElementsAsSet) should return false (no duplicates to remove)");
        verify();
        assertEquals(sizeBefore, getCollection().size(),
                "Collection size should not change after retainAll with equivalent set");
    }
}
