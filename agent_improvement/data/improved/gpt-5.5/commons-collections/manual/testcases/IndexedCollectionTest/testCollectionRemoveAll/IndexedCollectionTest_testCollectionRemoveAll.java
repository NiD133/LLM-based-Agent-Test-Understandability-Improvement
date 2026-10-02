package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "boxing", "unchecked" })
public class IndexedCollectionTest_testCollectionRemoveAll {

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

    /**
     * Tests {@link Collection#removeAll(Collection)}.
     */
    @Test
    void testCollectionRemoveAll() {
        if (!isRemoveSupported()) {
            return;
        }

        resetEmpty();
        assertFalse(getCollection().removeAll(Collections.EMPTY_SET),
            "Empty collection removeAll should return false for empty input");
        verify();
        assertFalse(getCollection().removeAll(new ArrayList<>(getCollection())),
            "Empty collection removeAll should return false for nonempty input");
        verify();

        resetFull();
        assertFalse(getCollection().removeAll(Collections.EMPTY_SET),
            "Full collection removeAll should return false for empty input");
        verify();
        assertFalse(getCollection().removeAll(Arrays.asList(getOtherElements())),
            "Full collection removeAll should return false for other elements");
        verify();
        assertTrue(getCollection().removeAll(new HashSet<>(getCollection())),
            "Full collection removeAll should return true for full elements");
        getConfirmed().removeAll(new HashSet<>(getConfirmed()));
        verify();

        resetFull();
        final int size = getCollection().size();
        final int min = getFullElements().length < 4 ? 0 : 2;
        final int max = getFullElements().length == 1 ? 1 : getFullElements().length <= 5 ? getFullElements().length - 1 : 5;
        final Collection<String> all = Arrays.asList(getFullElements()).subList(min, max);
        assertTrue(getCollection().removeAll(all), "Full collection removeAll should work");
        getConfirmed().removeAll(all);
        verify();
        assertTrue(getCollection().size() < size, "Collection should shrink after removeAll");
        for (final String element : all) {
            assertFalse(getCollection().contains(element), "Collection shouldn't contain removed element");
        }
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
