package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Verifies that wrapping a {@link Collection} in an {@link IndexedCollection}
 * builds the index from the elements already present, without needing any
 * further {@code add} calls.
 */
@SuppressWarnings("boxing")
public class IndexedCollectionTest_testDecoratedCollectionIsIndexedOnCreation {

    /** Keys the collection by the integer value of each string element ("1" -> 1). */
    private static final Transformer<String, Integer> STRING_TO_INTEGER = Integer::valueOf;

    /** Elements used to populate the collection before it is indexed. */
    private static final String[] ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    @Test
    void testDecoratedCollectionIsIndexedOnCreation() {
        // A non-unique indexed collection already holding all the elements.
        final Collection<String> alreadyPopulated =
                IndexedCollection.nonUniqueIndexedCollection(
                        new ArrayList<>(asList(ELEMENTS)), STRING_TO_INTEGER);

        // Decorating it again builds the index from the existing contents on creation.
        final IndexedCollection<Integer, String> indexed =
                IndexedCollection.uniqueIndexedCollection(alreadyPopulated, STRING_TO_INTEGER);

        // Each element is retrievable by its integer key without any explicit add() call.
        assertEquals("1", indexed.get(1));
        assertEquals("2", indexed.get(2));
        assertEquals("3", indexed.get(3));
    }
}
