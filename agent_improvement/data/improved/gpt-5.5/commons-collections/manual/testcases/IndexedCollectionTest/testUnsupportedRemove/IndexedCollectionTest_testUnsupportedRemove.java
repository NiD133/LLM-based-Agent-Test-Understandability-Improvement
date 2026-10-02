package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testUnsupportedRemove {

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
        assertEquals(getConfirmed().size(), getCollection().size(), "Collection size should match confirmed collection's");
        assertEquals(getConfirmed().isEmpty(), getCollection().isEmpty(), "Collection isEmpty() result should match confirmed collection's");
    }

    /**
     * If isRemoveSupported() returns false, remove operations must raise an
     * UnsupportedOperationException and leave the collection state unchanged.
     */
    @Test
    void testUnsupportedRemove() {
        if (isRemoveSupported()) {
            return;
        }

        resetEmpty();
        assertThrows(UnsupportedOperationException.class, () -> getCollection().clear(), "clear should raise UnsupportedOperationException");
        verify();
        assertThrows(UnsupportedOperationException.class, () -> getCollection().remove(null), "remove should raise UnsupportedOperationException");
        verify();
        assertThrows(UnsupportedOperationException.class, () -> getCollection().removeIf(e -> true), "removeIf should raise UnsupportedOperationException");
        verify();
        assertThrows(UnsupportedOperationException.class, () -> getCollection().removeAll(null), "removeAll should raise UnsupportedOperationException");
        verify();
        assertThrows(UnsupportedOperationException.class, () -> getCollection().retainAll(null), "retainAll should raise UnsupportedOperationException");
        verify();

        resetFull();
        final Iterator<String> iterator = getCollection().iterator();
        iterator.next();
        assertThrows(UnsupportedOperationException.class, () -> iterator.remove(), "iterator.remove should raise UnsupportedOperationException");
        verify();
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
