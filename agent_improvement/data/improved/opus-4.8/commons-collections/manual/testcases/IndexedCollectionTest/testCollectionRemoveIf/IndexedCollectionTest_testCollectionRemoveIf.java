package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link IndexedCollection#removeIf(java.util.function.Predicate)} on a
 * non-unique {@link IndexedCollection}.
 *
 * <p>Each scenario mutates the indexed collection and an independent "confirmed"
 * {@link ArrayList} the same way, then asserts that both still hold exactly the
 * same elements. This guards against the index drifting out of sync with the
 * decorated collection after a {@code removeIf}.</p>
 */
class IndexedCollectionTest_testCollectionRemoveIf {

    /** Keys every element by its integer value (e.g. {@code "7" -> 7}). */
    private static final Transformer<String, Integer> INTEGER_KEY = Integer::valueOf;

    /** Elements of a full collection; every value is a distinct integer string. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** Wraps a backing collection in a non-unique indexed collection keyed by integer value. */
    private static IndexedCollection<Integer, String> indexedCollection(final Collection<String> backing) {
        return IndexedCollection.nonUniqueIndexedCollection(backing, INTEGER_KEY);
    }

    /** Asserts that two collections hold the same elements, ignoring iteration order. */
    private static void assertSameElements(final Collection<String> actual, final Collection<String> expected) {
        final List<String> sortedActual = new ArrayList<>(actual);
        final List<String> sortedExpected = new ArrayList<>(expected);
        Collections.sort(sortedActual);
        Collections.sort(sortedExpected);
        assertEquals(sortedExpected, sortedActual,
                "Indexed collection should hold the same elements as the confirmed collection");
    }

    @Test
    void testCollectionRemoveIf() {
        // --- Empty collection: removeIf never removes anything and reports no change ---
        IndexedCollection<Integer, String> collection = indexedCollection(new ArrayList<>());
        Collection<String> confirmed = new ArrayList<>();

        assertFalse(collection.removeIf(e -> false),
                "Empty collection: removeIf(always false) removes nothing and returns false");
        assertSameElements(collection, confirmed);

        assertFalse(collection.removeIf(e -> true),
                "Empty collection: removeIf(always true) has nothing to remove and returns false");
        assertSameElements(collection, confirmed);

        // --- Full collection: removeIf(always false) keeps every element ---
        collection = indexedCollection(new ArrayList<>(asList(FULL_ELEMENTS)));
        confirmed = new ArrayList<>(asList(FULL_ELEMENTS));

        assertFalse(collection.removeIf(e -> false),
                "Full collection: removeIf(always false) removes nothing and returns false");
        assertSameElements(collection, confirmed);

        // --- Full collection: removeIf(always true) clears the collection ---
        assertTrue(collection.removeIf(e -> true),
                "Full collection: removeIf(always true) removes every element and returns true");
        confirmed.removeIf(e -> true);
        assertSameElements(collection, confirmed);

        // --- Full collection: removeIf removes only the matching element ---
        collection = indexedCollection(new ArrayList<>(asList(FULL_ELEMENTS)));
        confirmed = new ArrayList<>(asList(FULL_ELEMENTS));

        final String target = FULL_ELEMENTS[FULL_ELEMENTS.length / 2]; // "7"
        final int sizeBefore = collection.size();
        final int targetCount = Collections.frequency(asList(FULL_ELEMENTS), target);
        final Predicate<String> removeTarget = target::equals;

        assertTrue(collection.removeIf(removeTarget),
                "Full collection: removeIf matching one element returns true");
        confirmed.removeIf(removeTarget);
        assertSameElements(collection, confirmed);

        assertEquals(sizeBefore - targetCount, collection.size(),
                "Collection should shrink by the number of removed elements");
        assertFalse(collection.contains(target),
                "Collection should no longer contain the removed element");
    }
}
