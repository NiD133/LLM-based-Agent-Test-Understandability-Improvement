package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testUnsupportedAdd {

    private Collection<String> collection;
    private Collection<String> confirmed;

    protected Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, Integer::valueOf);
    }

    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    public String[] getOtherElements() {
        return new String[] { "9", "88", "678", "87", "98", "78", "99" };
    }

    public String[] getFullNonNullElements() {
        return new String[] { "" };
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

    public boolean isAddSupported() {
        return true;
    }

    public Collection<String> getCollection() {
        return collection;
    }

    public Collection<String> getConfirmed() {
        return confirmed;
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
        assertEquals(confirmed.size(), collection.size(), "Collection size should match confirmed collection's");
        assertEquals(confirmed.isEmpty(), collection.isEmpty(), "Collection isEmpty() result should match confirmed collection's");
        assertEquals(confirmed, collection);
    }

    /**
     * If {@link #isAddSupported()} returns false, tests that add operations
     * raise {@link UnsupportedOperationException}.
     */
    @Test
    void testUnsupportedAdd() {
        if (isAddSupported()) {
            return;
        }

        resetEmpty();
        assertThrows(UnsupportedOperationException.class, () -> getCollection().add(getFullNonNullElements()[0]), "Empty collection should not support add.");
        verify();

        assertThrows(UnsupportedOperationException.class, () -> getCollection().addAll(Arrays.asList(getFullElements())), "Empty collection should not support addAll.");
        verify();

        resetFull();
        assertThrows(UnsupportedOperationException.class, () -> getCollection().add(getFullNonNullElements()[0]), "Full collection should not support add.");
        verify();

        assertThrows(UnsupportedOperationException.class, () -> getCollection().addAll(Arrays.asList(getOtherElements())), "Full collection should not support addAll.");
        verify();
    }
}
