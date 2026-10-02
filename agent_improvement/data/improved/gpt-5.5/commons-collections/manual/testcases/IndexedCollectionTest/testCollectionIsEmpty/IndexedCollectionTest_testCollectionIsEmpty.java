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

@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionIsEmpty {

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
        setCollection(makeObject());
        setConfirmed(makeConfirmedCollection());
    }

    public void resetFull() {
        setCollection(makeFullCollection());
        setConfirmed(makeConfirmedFullCollection());
    }

    public void setCollection(final Collection<String> collection) {
        this.collection = collection;
    }

    public void setConfirmed(final Collection<String> confirmed) {
        this.confirmed = confirmed;
    }

    public void verify() {
        final int confirmedSize = getConfirmed().size();
        assertEquals(confirmedSize, getCollection().size(), "Collection size should match confirmed collection's");
        assertEquals(getConfirmed().isEmpty(), getCollection().isEmpty(), "Collection isEmpty() result should match confirmed collection's");

        final Object[] confirmedValues = new Object[confirmedSize];
        final Iterator<String> confirmedIterator = getConfirmed().iterator();
        int pos = 0;
        while (confirmedIterator.hasNext()) {
            confirmedValues[pos++] = confirmedIterator.next();
        }

        final boolean[] matched = new boolean[confirmedSize];
        final Iterator<String> collectionIterator = getCollection().iterator();
        while (collectionIterator.hasNext()) {
            final Object value = collectionIterator.next();
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

    private boolean markFirstMatchingValue(final Object value, final Object[] confirmedValues, final boolean[] matched) {
        for (int i = 0; i < confirmedValues.length; i++) {
            if (!matched[i] && Objects.equals(value, confirmedValues[i])) {
                matched[i] = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Tests {@link Collection#isEmpty()}.
     */
    @Test
    void testCollectionIsEmpty() {
        resetEmpty();
        assertTrue(getCollection().isEmpty(), "New Collection should be empty.");
        verify();

        resetFull();
        assertFalse(getCollection().isEmpty(), "Full collection shouldn't be empty");
        verify();
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
