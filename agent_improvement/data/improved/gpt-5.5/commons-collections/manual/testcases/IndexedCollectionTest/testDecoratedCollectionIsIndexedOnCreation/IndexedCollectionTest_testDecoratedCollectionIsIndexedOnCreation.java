package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testDecoratedCollectionIsIndexedOnCreation {

    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    protected Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    protected IndexedCollection<Integer, String> decorateUniqueCollection(final Collection<String> collection) {
        return IndexedCollection.uniqueIndexedCollection(collection, new IntegerTransformer());
    }

    public String[] getFullElements() {
        return FULL_ELEMENTS.clone();
    }

    public Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(getFullElements())));
    }

    @Test
    void testDecoratedCollectionIsIndexedOnCreation() throws Exception {
        final Collection<String> original = makeFullCollection();
        final IndexedCollection<Integer, String> indexed = decorateUniqueCollection(original);

        assertEquals("1", indexed.get(1));
        assertEquals("2", indexed.get(2));
        assertEquals("3", indexed.get(3));
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
