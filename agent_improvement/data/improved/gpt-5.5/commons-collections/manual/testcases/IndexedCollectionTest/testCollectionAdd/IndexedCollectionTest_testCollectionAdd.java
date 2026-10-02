package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testCollectionAdd {

    private Collection<String> collection;
    private Collection<String> confirmed;

    protected Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    public Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    public Collection<String> makeObject() {
        return decorateCollection(new ArrayList<>());
    }

    public boolean isAddSupported() {
        return true;
    }

    public Collection<String> getCollection() {
        return collection;
    }

    public Collection<String> getConfirmed() {
        return confirmed;
    }

    public void resetEmpty() {
        collection = makeObject();
        confirmed = makeConfirmedCollection();
    }

    public void verify() {
        assertEquals(confirmed.size(), collection.size(), "Collection size should match confirmed collection's");
        assertEquals(confirmed.isEmpty(), collection.isEmpty(), "Collection isEmpty() result should match confirmed collection's");
        assertTrue(collection.containsAll(confirmed), "Collection should contain all confirmed values");
        assertTrue(confirmed.containsAll(collection), "Confirmed collection should contain all collection values");
    }

    /**
     * Tests {@link Collection#add(Object)}.
     */
    @Test
    void testCollectionAdd() {
        if (!isAddSupported()) {
            return;
        }
        final String[] elements = getFullElements();
        for (final String element : elements) {
            resetEmpty();
            final boolean r = getCollection().add(element);
            getConfirmed().add(element);
            verify();
            assertTrue(r, "Empty collection changed after add");
            assertEquals(1, getCollection().size(), "Collection size is 1 after first add");
        }
        resetEmpty();
        int size = 0;
        for (final String element : elements) {
            final boolean r = getCollection().add(element);
            getConfirmed().add(element);
            verify();
            if (r) {
                size++;
            }
            assertEquals(size, getCollection().size(), "Collection size should grow after add");
            assertTrue(getCollection().contains(element), "Collection should contain added element");
        }
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
