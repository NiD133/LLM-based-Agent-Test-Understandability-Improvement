package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testUnsupportedRemove {

    /**
     * Transforms a String to its parsed Integer value, used as the index key.
     */
    private static class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.parseInt(input);
        }
    }

    private Collection<String> collection;
    private Collection<String> confirmed;

    private Collection<String> decorateCollection(final Collection<String> coll) {
        return IndexedCollection.nonUniqueIndexedCollection(coll, new IntegerTransformer());
    }

    private Collection<String> makeObject() {
        return decorateCollection(new ArrayList<>());
    }

    private Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    private String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    private Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(getFullElements())));
    }

    private Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(getFullElements()));
    }

    private void setCollection(final Collection<String> coll) {
        this.collection = coll;
    }

    private void setConfirmed(final Collection<String> coll) {
        this.confirmed = coll;
    }

    private Collection<String> getCollection() {
        return collection;
    }

    private Collection<String> getConfirmed() {
        return confirmed;
    }

    private void resetEmpty() {
        setCollection(makeObject());
        setConfirmed(makeConfirmedCollection());
    }

    private void resetFull() {
        setCollection(makeFullCollection());
        setConfirmed(makeConfirmedFullCollection());
    }

    /**
     * IndexedCollection supports all remove operations, so this returns true
     * and causes {@link #testUnsupportedRemove()} to exit without assertions.
     */
    private boolean isRemoveSupported() {
        return true;
    }

    /**
     * Verifies that {@link #collection} and {@link #confirmed} hold identical elements.
     * Performs an order-insensitive, duplicate-aware comparison.
     */
    private void verify() {
        final int confirmedSize = getConfirmed().size();
        assertEquals(confirmedSize, getCollection().size(),
                "Collection size should match confirmed collection's");
        assertEquals(getConfirmed().isEmpty(), getCollection().isEmpty(),
                "Collection isEmpty() result should match confirmed collection's");

        final Object[] confirmedValues = new Object[confirmedSize];
        Iterator<String> iter = getConfirmed().iterator();
        int pos = 0;
        while (iter.hasNext()) {
            confirmedValues[pos++] = iter.next();
        }

        final boolean[] matched = new boolean[confirmedSize];
        iter = getCollection().iterator();
        while (iter.hasNext()) {
            final Object o = iter.next();
            boolean match = false;
            for (int i = 0; i < confirmedSize; i++) {
                if (!matched[i] && Objects.equals(o, confirmedValues[i])) {
                    matched[i] = true;
                    match = true;
                    break;
                }
            }
            if (!match) {
                fail("Collection should not contain a value that the confirmed collection does not have: " + o
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
     * Verifies that remove operations throw {@link UnsupportedOperationException} when
     * the collection does not support removal. Because {@code IndexedCollection} does
     * support removal, {@link #isRemoveSupported()} returns {@code true} and this test
     * exits immediately without making any assertions.
     */
    @Test
    void testUnsupportedRemove() {
        if (isRemoveSupported()) {
            return;
        }

        resetEmpty();
        assertThrows(UnsupportedOperationException.class,
                () -> getCollection().clear(),
                "clear should raise UnsupportedOperationException");
        verify();

        assertThrows(UnsupportedOperationException.class,
                () -> getCollection().remove(null),
                "remove should raise UnsupportedOperationException");
        verify();

        assertThrows(UnsupportedOperationException.class,
                () -> getCollection().removeIf(e -> true),
                "removeIf should raise UnsupportedOperationException");
        verify();

        assertThrows(UnsupportedOperationException.class,
                () -> getCollection().removeAll(null),
                "removeAll should raise UnsupportedOperationException");
        verify();

        assertThrows(UnsupportedOperationException.class,
                () -> getCollection().retainAll(null),
                "retainAll should raise UnsupportedOperationException");
        verify();

        resetFull();
        final Iterator<String> iterator = getCollection().iterator();
        iterator.next();
        assertThrows(UnsupportedOperationException.class,
                () -> iterator.remove(),
                "iterator.remove should raise UnsupportedOperationException");
        verify();
    }
}
