package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Verifies that elements added to an {@link IndexedCollection} can later be
 * looked up via {@link IndexedCollection#get(Object)} using the key derived
 * from each element.
 */
public class IndexedCollectionTest_testAddedObjectsCanBeRetrievedByKey {

    /** Derives the integer index key for a value by parsing its string form. */
    private static final Transformer<String, Integer> STRING_TO_INTEGER_KEY = Integer::valueOf;

    /** Creates a non-unique indexed collection keyed by the integer value of each string. */
    private static IndexedCollection<Integer, String> newIndexedCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(new ArrayList<>(), STRING_TO_INTEGER_KEY);
    }

    @Test
    void testAddedObjectsCanBeRetrievedByKey() {
        final IndexedCollection<Integer, String> indexed = newIndexedCollection();

        // Add elements both individually and in bulk.
        indexed.add("12");
        indexed.add("16");
        indexed.add("1");
        indexed.addAll(asList("2", "3", "4"));

        // Each element must be retrievable by its integer key.
        assertEquals("12", indexed.get(12));
        assertEquals("16", indexed.get(16));
        assertEquals("1", indexed.get(1));
        assertEquals("2", indexed.get(2));
        assertEquals("3", indexed.get(3));
        assertEquals("4", indexed.get(4));
    }
}
