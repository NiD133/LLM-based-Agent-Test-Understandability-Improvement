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

public class IndexedCollectionTest_testCollectionToArray {

    private Collection<String> collection;
    private Collection<String> confirmed;

    protected Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    public Collection<String> getCollection() {
        return collection;
    }

    public Collection<String> getConfirmed() {
        return confirmed;
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

    /**
     * Tests {@link Collection#toArray()}.
     */
    @Test
    void testCollectionToArray() {
        resetEmpty();
        assertEquals(0, getCollection().toArray().length, "Empty Collection should return empty array for toArray");

        resetFull();
        final Object[] array = getCollection().toArray();
        assertEquals(array.length, getCollection().size(), "Full collection toArray should be same size as collection");

        final Object[] confirmedArray = getConfirmed().toArray();
        assertEquals(confirmedArray.length, array.length, "length of array from confirmed collection should match the length of the collection's array");

        final boolean[] matched = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            assertTrue(getCollection().contains(array[i]), "Collection should contain element in toArray");
            assertArrayElementMatchesConfirmedElement(array, confirmedArray, matched, i);
        }

        for (final boolean element : matched) {
            assertTrue(element, "Collection should return all its elements in toArray");
        }
    }

    private static void assertArrayElementMatchesConfirmedElement(final Object[] array, final Object[] confirmedArray,
            final boolean[] matched, final int arrayIndex) {
        for (int confirmedIndex = 0; confirmedIndex < confirmedArray.length; confirmedIndex++) {
            if (!matched[confirmedIndex] && Objects.equals(array[arrayIndex], confirmedArray[confirmedIndex])) {
                matched[confirmedIndex] = true;
                return;
            }
        }
        fail("element " + arrayIndex + " in returned array should be found in the confirmed collection's array");
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
