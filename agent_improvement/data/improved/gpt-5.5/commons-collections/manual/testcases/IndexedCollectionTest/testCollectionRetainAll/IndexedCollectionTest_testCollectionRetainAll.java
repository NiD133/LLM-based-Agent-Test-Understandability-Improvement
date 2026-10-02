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
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "boxing", "unchecked" })
public class IndexedCollectionTest_testCollectionRetainAll {

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

    public boolean isRemoveSupported() {
        return true;
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

    /**
     * Tests {@link Collection#retainAll(Collection)}.
     */
    @Test
    void testCollectionRetainAll() {
        if (!isRemoveSupported()) {
            return;
        }

        resetEmpty();
        final List<String> elements = Arrays.asList(getFullElements());
        final List<String> other = Arrays.asList(getOtherElements());

        assertFalse(getCollection().retainAll(Collections.EMPTY_SET), "Empty retainAll() should return false");
        verify();

        assertFalse(getCollection().retainAll(elements), "Empty retainAll() should return false");
        verify();

        resetFull();
        assertTrue(getCollection().retainAll(Collections.EMPTY_SET), "Collection should change from retainAll empty");
        getConfirmed().retainAll(Collections.EMPTY_SET);
        verify();

        resetFull();
        assertTrue(getCollection().retainAll(other), "Collection changed from retainAll other");
        getConfirmed().retainAll(other);
        verify();

        resetFull();
        int size = getCollection().size();
        assertFalse(getCollection().retainAll(elements), "Collection shouldn't change from retainAll elements");
        verify();
        assertEquals(size, getCollection().size(), "Collection size shouldn't change");

        if (getFullElements().length > 1) {
            resetFull();
            size = getCollection().size();
            final int min = getFullElements().length < 4 ? 0 : 2;
            final int max = getFullElements().length <= 5 ? getFullElements().length - 1 : 5;
            assertTrue(getCollection().retainAll(elements.subList(min, max)), "Collection should changed by partial retainAll");
            getConfirmed().retainAll(elements.subList(min, max));
            verify();
            for (final String element : getCollection()) {
                assertTrue(elements.subList(min, max).contains(element), "Collection only contains retained element");
            }
        }

        resetFull();
        final HashSet<String> set = new HashSet<>(elements);
        size = getCollection().size();
        assertFalse(getCollection().retainAll(set), "Collection shouldn't change from retainAll without duplicate elements");
        verify();
        assertEquals(size, getCollection().size(), "Collection size didn't change from nonduplicate retainAll");
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {
        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
