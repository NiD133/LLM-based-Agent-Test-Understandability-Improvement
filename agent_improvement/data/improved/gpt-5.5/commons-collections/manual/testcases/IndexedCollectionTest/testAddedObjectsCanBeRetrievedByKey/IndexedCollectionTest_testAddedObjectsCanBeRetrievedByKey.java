package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testAddedObjectsCanBeRetrievedByKey {

    protected Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    public Collection<String> makeTestCollection() {
        return decorateCollection(new ArrayList<>());
    }

    @Test
    void testAddedObjectsCanBeRetrievedByKey() throws Exception {
        final Collection<String> coll = makeTestCollection();
        coll.add("12");
        coll.add("16");
        coll.add("1");
        coll.addAll(asList("2", "3", "4"));

        @SuppressWarnings("unchecked")
        final IndexedCollection<Integer, String> indexed = (IndexedCollection<Integer, String>) coll;

        assertIndexedValue(indexed, 12, "12");
        assertIndexedValue(indexed, 16, "16");
        assertIndexedValue(indexed, 1, "1");
        assertIndexedValue(indexed, 2, "2");
        assertIndexedValue(indexed, 3, "3");
        assertIndexedValue(indexed, 4, "4");
    }

    private static void assertIndexedValue(final IndexedCollection<Integer, String> indexed,
            final Integer key, final String expectedValue) {
        assertEquals(expectedValue, indexed.get(key));
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {
        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
