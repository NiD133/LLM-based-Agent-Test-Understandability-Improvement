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
 * Tests {@link java.util.Collection#toArray()} on an {@link IndexedCollection}.
 */
class IndexedCollectionTest_testCollectionToArray {

    /** Elements used to populate a full collection. All values are unique. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** Derives an Integer index key from each String element. */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Wraps the given collection in a non-unique IndexedCollection keyed by Integer value. */
    private static Collection<String> indexed(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    @Test
    void testCollectionToArray() {
        // An empty indexed collection yields an empty array.
        final Collection<String> empty = indexed(new ArrayList<>());
        assertEquals(0, empty.toArray().length, "Empty Collection should return empty array for toArray");

        // A full indexed collection, plus a plain "confirmed" list holding the same elements.
        final Collection<String> collection = indexed(new ArrayList<>(asList(FULL_ELEMENTS)));
        final Collection<String> confirmed = new ArrayList<>(asList(FULL_ELEMENTS));

        final Object[] array = collection.toArray();
        assertEquals(array.length, collection.size(), "Full collection toArray should be same size as collection");

        final Object[] confirmedArray = confirmed.toArray();
        assertEquals(confirmedArray.length, array.length,
                "length of array from confirmed collection should match the length of the collection's array");

        // Every element returned by toArray must be contained in the collection and must
        // match exactly one (still-unmatched) element of the confirmed array. The matched[]
        // flags guard against double-counting duplicates and ignore element ordering.
        final boolean[] matched = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            assertTrue(collection.contains(array[i]), "Collection should contain element in toArray");
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
        for (final boolean element : matched) {
            assertTrue(element, "Collection should return all its elements in toArray");
        }
    }
}
