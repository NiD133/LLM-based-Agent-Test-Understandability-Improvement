package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testCollectionSize {

    // Maps each string element to its integer value, used as the index key
    private static final Transformer<String, Integer> KEY_TRANSFORMER = s -> Integer.valueOf(s);

    private static final String[] FULL_ELEMENTS = {"1", "3", "5", "7", "2", "4", "6"};

    private Collection<String> makeEmptyIndexedCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(new ArrayList<>(), KEY_TRANSFORMER);
    }

    private Collection<String> makeFullIndexedCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(
            new ArrayList<>(Arrays.asList(FULL_ELEMENTS)), KEY_TRANSFORMER);
    }

    @Test
    void testCollectionSize() {
        Collection<String> emptyCollection = makeEmptyIndexedCollection();
        assertEquals(0, emptyCollection.size(), "Size of new Collection is 0.");

        Collection<String> fullCollection = makeFullIndexedCollection();
        assertFalse(fullCollection.isEmpty(), "Size of full collection should be greater than zero");
    }
}
