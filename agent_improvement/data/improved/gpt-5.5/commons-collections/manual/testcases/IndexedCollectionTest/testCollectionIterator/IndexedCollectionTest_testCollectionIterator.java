package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testCollectionIterator {

    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    private Collection<String> collection;
    private Collection<String> confirmed;

    protected Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    public String[] getFullElements() {
        return FULL_ELEMENTS.clone();
    }

    public Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    public Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(getFullElements()));
    }

    public Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(getFullElements())));
    }

    public Collection<String> makeObject() {
        return decorateCollection(new ArrayList<>());
    }

    public Collection<String> getCollection() {
        return collection;
    }

    public Collection<String> getConfirmed() {
        return confirmed;
    }

    public void resetEmpty() {
        this.collection = makeObject();
        this.confirmed = makeConfirmedCollection();
    }

    public void resetFull() {
        this.collection = makeFullCollection();
        this.confirmed = makeConfirmedFullCollection();
    }

    public void verify() {
        final int confirmedSize = getConfirmed().size();
        assertEquals(confirmedSize, getCollection().size(), "Collection size should match confirmed collection's");
        assertEquals(getConfirmed().isEmpty(), getCollection().isEmpty(), "Collection isEmpty() result should match confirmed collection's");

        final Object[] confirmedValues = new Object[confirmedSize];
        final Iterator<String> confirmedIterator = getConfirmed().iterator();
        int position = 0;
        while (confirmedIterator.hasNext()) {
            confirmedValues[position++] = confirmedIterator.next();
        }

        final boolean[] matched = new boolean[confirmedSize];
        final Iterator<String> collectionIterator = getCollection().iterator();
        while (collectionIterator.hasNext()) {
            final Object value = collectionIterator.next();
            boolean match = false;
            for (int i = 0; i < confirmedSize; i++) {
                if (!matched[i] && Objects.equals(value, confirmedValues[i])) {
                    matched[i] = true;
                    match = true;
                    break;
                }
            }
            if (!match) {
                fail("Collection should not contain a value that the confirmed collection does not have: "
                        + value + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }

        for (int i = 0; i < confirmedSize; i++) {
            if (!matched[i]) {
                fail("Collection should contain all values that are in the confirmed collection"
                        + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }
    }

    @Test
    void testCollectionIterator() {
        resetEmpty();
        Iterator<String> iterator = getCollection().iterator();
        assertFalse(iterator.hasNext(), "Iterator for empty Collection shouldn't have next.");
        final Iterator<String> emptyIterator = iterator;
        assertThrows(NoSuchElementException.class, () -> emptyIterator.next(),
                "Iterator at end of Collection should throw NoSuchElementException when next is called.");

        verify();

        resetFull();
        iterator = getCollection().iterator();
        for (final String element : getCollection()) {
            assertTrue(iterator.hasNext(), "Iterator for full collection should haveNext");
            iterator.next();
        }
        assertFalse(iterator.hasNext(), "Iterator should be finished");

        final ArrayList<String> iteratedElements = new ArrayList<>();
        iterator = getCollection().iterator();
        for (int i = 0; i < getCollection().size(); i++) {
            final String next = iterator.next();
            assertTrue(getCollection().contains(next), "Collection should contain element returned by its iterator");
            iteratedElements.add(next);
        }
        final Iterator<String> exhaustedIterator = iterator;
        assertThrows(NoSuchElementException.class, () -> exhaustedIterator.next(),
                "iterator.next() should raise NoSuchElementException after it finishes");

        verify();
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
