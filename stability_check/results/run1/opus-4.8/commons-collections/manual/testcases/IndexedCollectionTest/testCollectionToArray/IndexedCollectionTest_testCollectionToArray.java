package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Focused test for {@link java.util.Collection#toArray()} as exposed by
 * {@link IndexedCollection}.
 *
 * <p>The collection under test is a non-unique {@link IndexedCollection} that
 * decorates an {@link ArrayList} of {@link String}s and indexes each element by
 * its integer value. A plain {@link ArrayList} holding the same elements acts
 * as the "confirmed" reference collection that is known to honour the
 * {@link java.util.Collection} contract.</p>
 */
class IndexedCollectionTest_testCollectionToArray {

    /** Elements used to build a full collection. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** Maps each String element to its Integer index key. */
    private static final Transformer<String, Integer> KEY_TRANSFORMER = Integer::valueOf;

    /** Creates an IndexedCollection view over the given backing collection. */
    private static Collection<String> asIndexedCollection(final Collection<String> backing) {
        return IndexedCollection.nonUniqueIndexedCollection(backing, KEY_TRANSFORMER);
    }

    /**
     * Tests that {@link java.util.Collection#toArray()} returns an array whose
     * contents match the collection, for both an empty and a full collection.
     */
    @Test
    void testCollectionToArray() {
        // An empty indexed collection must produce an empty array.
        final Collection<String> emptyCollection = asIndexedCollection(new ArrayList<>());
        assertEquals(0, emptyCollection.toArray().length,
                "Empty Collection should return empty array for toArray");

        // A full indexed collection and an equivalent confirmed collection.
        final Collection<String> fullCollection = asIndexedCollection(new ArrayList<>(asList(FULL_ELEMENTS)));
        final Collection<String> confirmedCollection = new ArrayList<>(asList(FULL_ELEMENTS));

        final Object[] array = fullCollection.toArray();
        assertEquals(array.length, fullCollection.size(),
                "Full collection toArray should be same size as collection");

        final Object[] confirmedArray = confirmedCollection.toArray();
        assertEquals(confirmedArray.length, array.length,
                "length of array from confirmed collection should match the length of the collection's array");

        // Every element of the returned array must be contained in the collection
        // and must correspond, one-to-one, to an element of the confirmed array.
        // Ordering is not assumed, so matches are tracked with a flag per element.
        final boolean[] matched = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            assertTrue(fullCollection.contains(array[i]),
                    "Collection should contain element in toArray");

            boolean match = false;
            for (int j = 0; j < array.length; j++) {
                if (matched[j]) {
                    continue;
                }
                if (Objects.equals(array[i], confirmedArray[j])) {
                    matched[j] = true;
                    match = true;
                    break;
                }
            }
            if (!match) {
                fail("element " + i + " in returned array should be found in the confirmed collection's array");
            }
        }

        // Every confirmed element must have been matched exactly once.
        for (final boolean element : matched) {
            assertTrue(element, "Collection should return all its elements in toArray");
        }
    }
}
