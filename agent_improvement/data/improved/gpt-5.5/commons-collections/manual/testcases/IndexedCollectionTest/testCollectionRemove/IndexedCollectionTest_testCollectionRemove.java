package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testCollectionRemove {

    private Collection<String> collection;
    private Collection<String> confirmed;

    protected Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    public String[] getOtherElements() {
        return new String[] { "9", "88", "678", "87", "98", "78", "99" };
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

    public boolean areEqualElementsDistinguishable() {
        return false;
    }

    public Collection<String> getCollection() {
        return collection;
    }

    public Collection<String> getConfirmed() {
        return confirmed;
    }

    public boolean isRemoveSupported() {
        return true;
    }

    public void resetEmpty() {
        collection = makeObject();
        confirmed = makeConfirmedCollection();
    }

    public void resetFull() {
        collection = makeFullCollection();
        confirmed = makeConfirmedFullCollection();
    }

    public void verify() {
        final int confirmedSize = getConfirmed().size();
        assertEquals(confirmedSize, getCollection().size(), "Collection size should match confirmed collection's");
        assertEquals(getConfirmed().isEmpty(), getCollection().isEmpty(),
                "Collection isEmpty() result should match confirmed collection's");

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
            if (!markFirstUnmatchedEqualValue(value, confirmedValues, matched)) {
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

    private boolean markFirstUnmatchedEqualValue(final Object value, final Object[] confirmedValues,
            final boolean[] matched) {
        for (int i = 0; i < confirmedValues.length; i++) {
            if (!matched[i] && Objects.equals(value, confirmedValues[i])) {
                matched[i] = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Tests {@link Collection#remove(Object)}.
     */
    @Test
    void testCollectionRemove() {
        if (!isRemoveSupported()) {
            return;
        }
        resetEmpty();
        final String[] elements = getFullElements();
        for (final String element : elements) {
            assertFalse(getCollection().remove(element), "Shouldn't remove nonexistent element");
            verify();
        }
        final String[] other = getOtherElements();
        resetFull();
        for (final String element : other) {
            assertFalse(getCollection().remove(element), "Shouldn't remove nonexistent other element");
            verify();
        }
        final int size = getCollection().size();
        for (final String element : elements) {
            resetFull();
            assertTrue(getCollection().remove(element), "Collection should remove extant element: " + element);
            if (!areEqualElementsDistinguishable()) {
                getConfirmed().remove(element);
                verify();
            }
            assertEquals(size - 1, getCollection().size(), "Collection should shrink after remove");
        }
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
