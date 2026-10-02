package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link IndexedCollection#remove(Object)} for a non-unique indexed collection
 * whose index keys are the integer values of its String elements.
 *
 * <p>Each modification is mirrored against a plain {@link ArrayList} ("confirmed")
 * that is known to obey the {@link Collection} contract; {@link #verify()} then
 * checks that the collection under test still holds exactly the same elements.</p>
 */
public class IndexedCollectionTest_testCollectionRemove {

    /** Derives an index key from a value by parsing the String as an integer. */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {

        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Values contained in a full collection. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** Values that are never contained in a full collection. */
    private static final String[] OTHER_ELEMENTS = { "9", "88", "678", "87", "98", "78", "99" };

    /** The indexed collection under test. */
    private Collection<String> collection;

    /** A plain collection, kept in lock-step with {@link #collection}, used as the source of truth. */
    private Collection<String> confirmed;

    /** Wraps the backing collection in a non-unique IndexedCollection. */
    private static Collection<String> indexed(final Collection<String> backing) {
        return IndexedCollection.nonUniqueIndexedCollection(backing, new IntegerTransformer());
    }

    /** Resets both collections to an empty state. */
    private void resetEmpty() {
        collection = indexed(new ArrayList<>());
        confirmed = new ArrayList<>();
    }

    /** Resets both collections to contain all {@link #FULL_ELEMENTS}. */
    private void resetFull() {
        collection = indexed(new ArrayList<>(Arrays.asList(FULL_ELEMENTS)));
        confirmed = new ArrayList<>(Arrays.asList(FULL_ELEMENTS));
    }

    /** Asserts that the collection under test holds exactly the same elements as the confirmed collection. */
    private void verify() {
        assertEquals(confirmed.size(), collection.size(), "size should match confirmed collection");
        assertEquals(confirmed.isEmpty(), collection.isEmpty(), "isEmpty() should match confirmed collection");

        // Match every element once and only once, ignoring order (multiset comparison).
        final List<String> remaining = new ArrayList<>(confirmed);
        for (final String value : collection) {
            assertTrue(remaining.remove(value),
                "collection contains a value the confirmed collection does not: " + value);
        }
        assertTrue(remaining.isEmpty(),
            "confirmed collection contains values missing from the collection: " + remaining);
    }

    /**
     * Tests {@link IndexedCollection#remove(Object)}.
     */
    @Test
    void testCollectionRemove() {
        // Removing any element from an empty collection fails and leaves it unchanged.
        resetEmpty();
        for (final String element : FULL_ELEMENTS) {
            assertFalse(collection.remove(element), "shouldn't remove element from empty collection");
            verify();
        }

        // Removing an absent element from a full collection fails and leaves it unchanged.
        resetFull();
        for (final String element : OTHER_ELEMENTS) {
            assertFalse(collection.remove(element), "shouldn't remove element that is not present");
            verify();
        }

        // Removing each present element succeeds and shrinks the collection by exactly one.
        final int fullSize = collection.size();
        for (final String element : FULL_ELEMENTS) {
            resetFull();
            assertTrue(collection.remove(element), "collection should remove extant element: " + element);
            confirmed.remove(element);
            verify();
            assertEquals(fullSize - 1, collection.size(), "collection should shrink after remove");
        }
    }
}
