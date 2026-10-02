package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
public class IndexedCollectionTest_testCollectionIteratorRemove {

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

    public boolean areEqualElementsDistinguishable() {
        return false;
    }

    public boolean isRemoveSupported() {
        return true;
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

        final Object[] confirmedValues = getConfirmed().toArray();
        final boolean[] matched = new boolean[confirmedSize];

        for (final String actualValue : getCollection()) {
            boolean match = false;
            for (int i = 0; i < confirmedSize; i++) {
                if (!matched[i] && Objects.equals(actualValue, confirmedValues[i])) {
                    matched[i] = true;
                    match = true;
                    break;
                }
            }
            if (!match) {
                fail("Collection should not contain a value that the confirmed collection does not have: "
                    + actualValue + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }

        for (final boolean wasMatched : matched) {
            if (!wasMatched) {
                fail("Collection should contain all values that are in the confirmed collection"
                    + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }
    }

    /**
     * Tests removals from {@link Collection#iterator()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testCollectionIteratorRemove() {
        if (!isRemoveSupported()) {
            return;
        }

        resetEmpty();
        assertThrows(IllegalStateException.class, () -> getCollection().iterator().remove(), "New iterator.remove should raise IllegalState");
        verify();

        final Iterator<String> emptyIterator = getCollection().iterator();
        emptyIterator.hasNext();
        assertThrows(IllegalStateException.class, () -> emptyIterator.remove(), "New iterator.remove should raise IllegalState even after hasNext");
        verify();

        resetFull();
        int expectedSize = getCollection().size();
        Iterator<String> iterator = getCollection().iterator();
        while (iterator.hasNext()) {
            Object removedValue = iterator.next();
            if (removedValue instanceof Map.Entry) {
                removedValue = cloneMapEntry((Map.Entry<String, String>) removedValue);
            }

            iterator.remove();
            if (!areEqualElementsDistinguishable()) {
                getConfirmed().remove(removedValue);
                verify();
            }

            expectedSize--;
            assertEquals(expectedSize, getCollection().size(), "Collection should shrink by one after iterator.remove");
        }
        assertTrue(getCollection().isEmpty(), "Collection should be empty after iterator purge");

        resetFull();
        iterator = getCollection().iterator();
        iterator.next();
        iterator.remove();
        final Iterator<String> iteratorAfterRemove = iterator;
        assertThrows(IllegalStateException.class, () -> iteratorAfterRemove.remove(), "Second iter.remove should raise IllegalState");
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
