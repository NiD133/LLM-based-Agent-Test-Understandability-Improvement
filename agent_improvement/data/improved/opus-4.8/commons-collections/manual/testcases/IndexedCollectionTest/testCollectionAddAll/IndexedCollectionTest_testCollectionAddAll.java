package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link IndexedCollection#addAll(Collection)} through a non-unique indexed
 * collection that decorates an {@link ArrayList} of {@link String} values, keyed by
 * the integer value of each string.
 */
public class IndexedCollectionTest_testCollectionAddAll {

    /** Key transformer mapping each string element to its integer value. */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Elements present in a "full" collection. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** Elements that are not present in a "full" collection. */
    private static final String[] OTHER_ELEMENTS = { "9", "88", "678", "87", "98", "78", "99" };

    /** Indexed collection under test. */
    private Collection<String> collection;

    /** Plain collection kept in sync with {@link #collection} to confirm expected contents. */
    private Collection<String> confirmed;

    /** Wraps the given list in a non-unique indexed collection keyed by integer value. */
    private static Collection<String> decorate(final Collection<String> list) {
        return IndexedCollection.nonUniqueIndexedCollection(list, new IntegerTransformer());
    }

    /** Resets both collections to an empty state. */
    private void resetEmpty() {
        collection = decorate(new ArrayList<>());
        confirmed = new ArrayList<>();
    }

    /** Resets both collections to a full state containing {@link #FULL_ELEMENTS}. */
    private void resetFull() {
        collection = decorate(new ArrayList<>(asList(FULL_ELEMENTS)));
        confirmed = new ArrayList<>(asList(FULL_ELEMENTS));
    }

    /**
     * Verifies that {@link #collection} contains exactly the same elements as
     * {@link #confirmed}, ignoring iteration order but accounting for duplicates.
     */
    private void verify() {
        assertEquals(confirmed.size(), collection.size(),
                "Collection size should match confirmed collection's");
        assertEquals(confirmed.isEmpty(), collection.isEmpty(),
                "Collection isEmpty() result should match confirmed collection's");

        final Object[] confirmedValues = confirmed.toArray();
        final boolean[] matched = new boolean[confirmedValues.length];
        for (final Object actual : collection) {
            boolean match = false;
            for (int i = 0; i < confirmedValues.length; i++) {
                if (!matched[i] && Objects.equals(actual, confirmedValues[i])) {
                    matched[i] = true;
                    match = true;
                    break;
                }
            }
            if (!match) {
                fail("Collection should not contain a value that the confirmed collection does not have: "
                        + actual + "\nTest: " + collection + "\nReal: " + confirmed);
            }
        }
        for (final boolean wasMatched : matched) {
            if (!wasMatched) {
                fail("Collection should contain all values that are in the confirmed collection"
                        + "\nTest: " + collection + "\nReal: " + confirmed);
            }
        }
    }

    /**
     * Tests {@link Collection#addAll(Collection)} for empty, full, and duplicate-insertion cases.
     */
    @Test
    public void testCollectionAddAll() {
        // Adding to an empty collection should change it and include every added element.
        resetEmpty();
        boolean changed = collection.addAll(asList(FULL_ELEMENTS));
        confirmed.addAll(asList(FULL_ELEMENTS));
        verify();
        assertTrue(changed, "Empty collection should change after addAll");
        for (final String element : FULL_ELEMENTS) {
            assertTrue(collection.contains(element), "Collection should contain added element");
        }

        // Adding brand-new elements to a full collection should grow it by their count.
        resetFull();
        int size = collection.size();
        changed = collection.addAll(asList(OTHER_ELEMENTS));
        confirmed.addAll(asList(OTHER_ELEMENTS));
        verify();
        assertTrue(changed, "Full collection should change after addAll");
        for (final String element : OTHER_ELEMENTS) {
            assertTrue(collection.contains(element), "Full collection should contain added element");
        }
        assertEquals(size + OTHER_ELEMENTS.length, collection.size(), "Size should increase after addAll");

        // Re-adding the existing full elements: size grows iff addAll reports a change.
        resetFull();
        size = collection.size();
        changed = collection.addAll(asList(FULL_ELEMENTS));
        confirmed.addAll(asList(FULL_ELEMENTS));
        verify();
        if (changed) {
            assertTrue(size < collection.size(), "Size should increase if addAll returns true");
        } else {
            assertEquals(size, collection.size(), "Size should not change if addAll returns false");
        }
    }
}
