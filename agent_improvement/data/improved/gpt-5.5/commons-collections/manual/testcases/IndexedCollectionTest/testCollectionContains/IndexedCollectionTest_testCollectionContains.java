package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionContains {

    private Collection<String> collection;
    private Collection<String> confirmed;

    protected Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    protected IndexedCollection<Integer, String> decorateUniqueCollection(final Collection<String> collection) {
        return IndexedCollection.uniqueIndexedCollection(collection, new IntegerTransformer());
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

    public Collection<String> makeTestCollection() {
        return decorateCollection(new ArrayList<>());
    }

    public Collection<String> makeUniqueTestCollection() {
        return decorateUniqueCollection(new ArrayList<>());
    }

    public boolean areEqualElementsDistinguishable() {
        return false;
    }

    public Map.Entry<String, String> cloneMapEntry(final Map.Entry<String, String> entry) {
        final HashMap<String, String> map = new HashMap<>();
        map.put(entry.getKey(), entry.getValue());
        return map.entrySet().iterator().next();
    }

    public Collection<String> getCollection() {
        return collection;
    }

    public Collection<String> getConfirmed() {
        return confirmed;
    }

    protected int getIterationBehaviour() {
        return 0;
    }

    public boolean isAddSupported() {
        return true;
    }

    public boolean isEqualsCheckable() {
        return false;
    }

    public boolean isFailFastSupported() {
        return false;
    }

    public boolean isNullSupported() {
        return true;
    }

    public boolean isRemoveSupported() {
        return true;
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
            boolean match = false;
            for (int i = 0; i < confirmedSize; i++) {
                if (!matched[i] && Objects.equals(value, confirmedValues[i])) {
                    matched[i] = true;
                    match = true;
                    break;
                }
            }
            if (!match) {
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

    @Test
    void testCollectionContains() {
        Object[] elements;

        resetEmpty();
        elements = getFullElements();
        for (int i = 0; i < elements.length; i++) {
            assertFalse(getCollection().contains(elements[i]), "Empty collection shouldn't contain element[" + i + "]");
        }
        verify();

        elements = getOtherElements();
        for (int i = 0; i < elements.length; i++) {
            assertFalse(getCollection().contains(elements[i]), "Empty collection shouldn't contain element[" + i + "]");
        }
        verify();

        resetFull();
        elements = getFullElements();
        for (int i = 0; i < elements.length; i++) {
            assertTrue(getCollection().contains(elements[i]), "Full collection should contain element[" + i + "]");
        }
        verify();

        resetFull();
        elements = getOtherElements();
        for (final Object element : elements) {
            assertFalse(getCollection().contains(element), "Full collection shouldn't contain element");
        }
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
