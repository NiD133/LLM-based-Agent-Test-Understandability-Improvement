package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link IndexedCollection#toString()} never returns {@code null},
 * for both an empty and a fully populated collection.
 */
public class IndexedCollectionTest_testCollectionToString {

    /** Derives an Integer index key from each String element. */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Elements used to build a fully populated collection. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /**
     * Builds a non-unique {@link IndexedCollection} keyed by {@link IntegerTransformer},
     * backed by a list containing the given elements.
     */
    private static IndexedCollection<Integer, String> newIndexedCollection(final String... elements) {
        final Collection<String> backing = new ArrayList<>(asList(elements));
        return IndexedCollection.nonUniqueIndexedCollection(backing, new IntegerTransformer());
    }

    @Test
    void testCollectionToString() {
        final IndexedCollection<Integer, String> emptyCollection = newIndexedCollection();
        assertNotNull(emptyCollection.toString(), "toString shouldn't return null");

        final IndexedCollection<Integer, String> fullCollection = newIndexedCollection(FULL_ELEMENTS);
        assertNotNull(fullCollection.toString(), "toString shouldn't return null");
    }
}
