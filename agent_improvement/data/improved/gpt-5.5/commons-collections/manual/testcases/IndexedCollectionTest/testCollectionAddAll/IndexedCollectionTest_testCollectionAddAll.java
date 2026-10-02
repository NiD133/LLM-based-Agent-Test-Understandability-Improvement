package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testCollectionAddAll {

    private Collection<String> collection;
    private Collection<String> confirmed;

    private Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    private String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    private String[] getOtherElements() {
        return new String[] { "9", "88", "678", "87", "98", "78", "99" };
    }

    private Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    private Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(getFullElements()));
    }

    private Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(getFullElements())));
    }

    private Collection<String> makeObject() {
        return decorateCollection(new ArrayList<>());
    }

    private Collection<String> getCollection() {
        return collection;
    }

    private Collection<String> getConfirmed() {
        return confirmed;
    }

    private boolean isAddSupported() {
        return true;
    }

    private void resetEmpty() {
        collection = makeObject();
        confirmed = makeConfirmedCollection();
    }

    private void resetFull() {
        collection = makeFullCollection();
        confirmed = makeConfirmedFullCollection();
    }

    private void verify() {
        final int confirmedSize = getConfirmed().size();
        assertEquals(confirmedSize, getCollection().size(), "Collection size should match confirmed collection's");
        assertEquals(getConfirmed().isEmpty(), getCollection().isEmpty(), "Collection isEmpty() result should match confirmed collection's");

        final Object[] confirmedValues = getConfirmed().toArray();
        final boolean[] matched = new boolean[confirmedSize];

        for (final String value : getCollection()) {
            if (!markFirstMatchingValue(value, confirmedValues, matched)) {
                fail("Collection should not contain a value that the confirmed collection does not have: " + value
                    + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }

        for (int i = 0; i < confirmedSize; i++) {
            if (!matched[i]) {
                fail("Collection should contain all values that are in the confirmed collection"
                    + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }
    }

    private boolean markFirstMatchingValue(final String value, final Object[] confirmedValues, final boolean[] matched) {
        for (int i = 0; i < confirmedValues.length; i++) {
            if (!matched[i] && Objects.equals(value, confirmedValues[i])) {
                matched[i] = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Tests {@link Collection#addAll(Collection)}.
     */
    @Test
    public void testCollectionAddAll() {
        if (!isAddSupported()) {
            return;
        }

        resetEmpty();
        String[] elements = getFullElements();
        boolean changed = getCollection().addAll(Arrays.asList(elements));
        getConfirmed().addAll(Arrays.asList(elements));
        verify();
        assertTrue(changed, "Empty collection should change after addAll");
        for (final String element : elements) {
            assertTrue(getCollection().contains(element), "Collection should contain added element");
        }

        resetFull();
        int size = getCollection().size();
        elements = getOtherElements();
        changed = getCollection().addAll(Arrays.asList(elements));
        getConfirmed().addAll(Arrays.asList(elements));
        verify();
        assertTrue(changed, "Full collection should change after addAll");
        for (final String element : elements) {
            assertTrue(getCollection().contains(element), "Full collection should contain added element");
        }
        assertEquals(size + elements.length, getCollection().size(), "Size should increase after addAll");

        resetFull();
        size = getCollection().size();
        changed = getCollection().addAll(Arrays.asList(getFullElements()));
        getConfirmed().addAll(Arrays.asList(getFullElements()));
        verify();
        if (changed) {
            assertTrue(size < getCollection().size(), "Size should increase if addAll returns true");
        } else {
            assertEquals(size, getCollection().size(), "Size should not change if addAll returns false");
        }
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
