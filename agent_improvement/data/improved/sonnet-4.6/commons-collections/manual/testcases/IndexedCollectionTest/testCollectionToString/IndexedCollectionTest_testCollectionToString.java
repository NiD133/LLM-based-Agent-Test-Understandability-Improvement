package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testCollectionToString {

    // Elements whose string values are valid integers, matching the IntegerTransformer key
    private static final String[] FULL_ELEMENTS = {"1", "3", "5", "7", "2", "4", "6"};

    /**
     * Transforms a string element to its Integer value to serve as the collection index key.
     */
    private static class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    private Collection<String> makeEmptyIndexedCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(
                new ArrayList<>(), new IntegerTransformer());
    }

    private Collection<String> makeFullIndexedCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(
                new ArrayList<>(Arrays.asList(FULL_ELEMENTS)), new IntegerTransformer());
    }

    /**
     * Verifies that {@code toString()} never returns null for either an empty
     * or a fully-populated {@link IndexedCollection}.
     */
    @Test
    void testCollectionToString() {
        Collection<String> empty = makeEmptyIndexedCollection();
        assertNotNull(empty.toString(), "toString() should not return null for an empty collection");

        Collection<String> full = makeFullIndexedCollection();
        assertNotNull(full.toString(), "toString() should not return null for a full collection");
    }
}
