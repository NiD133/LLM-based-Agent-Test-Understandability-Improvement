package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Focused test for {@link IndexedCollection#toArray()}.
 *
 * <p>It verifies two cases:</p>
 * <ul>
 *   <li>an empty indexed collection returns an empty array;</li>
 *   <li>a fully populated indexed collection returns an array that holds
 *       exactly the same elements (order independent) as an equivalent plain
 *       collection.</li>
 * </ul>
 */
public class IndexedCollectionTest_testCollectionToArray {

    /** Transforms the string values into the integer keys used by the index. */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {

        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** The values used to populate a full collection; each maps to a distinct index key. */
    private static String[] fullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    /** Wraps the given collection in a non-unique {@link IndexedCollection}. */
    private static Collection<String> asIndexedCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    @Test
    void testCollectionToArray() {
        // An empty indexed collection must produce an empty array.
        final Collection<String> emptyCollection = asIndexedCollection(new ArrayList<>());
        assertEquals(0, emptyCollection.toArray().length,
                "Empty Collection should return empty array for toArray");

        // A full indexed collection, plus a plain collection holding the same elements
        // used as the trusted reference ("confirmed").
        final Collection<String> fullCollection =
                asIndexedCollection(new ArrayList<>(Arrays.asList(fullElements())));
        final Collection<String> confirmed = new ArrayList<>(Arrays.asList(fullElements()));

        final Object[] array = fullCollection.toArray();
        assertEquals(array.length, fullCollection.size(),
                "Full collection toArray should be same size as collection");

        final Object[] confirmedArray = confirmed.toArray();
        assertEquals(confirmedArray.length, array.length,
                "length of array from confirmed collection should "
                        + "match the length of the collection's array");

        // Match every element from the collection's array against the confirmed array,
        // allowing each confirmed element to be matched only once (order independent).
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
                fail("element " + i + " in returned array should be found "
                        + "in the confirmed collection's array");
            }
        }

        // Every confirmed element must have been matched exactly once.
        for (final boolean element : matched) {
            assertTrue(element, "Collection should return all its elements in toArray");
        }
    }
}
