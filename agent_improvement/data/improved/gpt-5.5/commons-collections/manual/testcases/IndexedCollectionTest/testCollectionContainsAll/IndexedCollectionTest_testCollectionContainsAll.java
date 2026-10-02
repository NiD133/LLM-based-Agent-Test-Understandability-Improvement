package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionContainsAll {

    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };
    private static final String[] OTHER_ELEMENTS = { "9", "88", "678", "87", "98", "78", "99" };

    private Collection<String> collection;
    private Collection<String> confirmed;

    protected Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    protected IndexedCollection<Integer, String> decorateUniqueCollection(final Collection<String> collection) {
        return IndexedCollection.uniqueIndexedCollection(collection, new IntegerTransformer());
    }

    public String[] getFullElements() {
        return FULL_ELEMENTS.clone();
    }

    public String[] getOtherElements() {
        return OTHER_ELEMENTS.clone();
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

    /**
     * Verifies that the indexed collection still has the same contents as the
     * plain confirmed collection after read-only operations.
     */
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
     * Tests {@link Collection#containsAll(Collection)}.
     */
    @Test
    void testCollectionContainsAll() {
        resetEmpty();
        Collection<String> col = new HashSet<>();
        assertTrue(getCollection().containsAll(col), "Every Collection should contain all elements of an empty Collection.");
        col.addAll(Arrays.asList(getOtherElements()));
        assertFalse(getCollection().containsAll(col), "Empty Collection shouldn't contain all elements of a non-empty Collection.");
        verify();

        resetFull();
        assertFalse(getCollection().containsAll(col), "Full collection shouldn't contain other elements");
        col.clear();
        col.addAll(Arrays.asList(getFullElements()));
        assertTrue(getCollection().containsAll(col), "Full collection should containAll full elements");
        verify();

        final int min = getFullElements().length < 4 ? 0 : 2;
        final int max = getFullElements().length == 1 ? 1 : getFullElements().length <= 5 ? getFullElements().length - 1 : 5;
        col = Arrays.asList(getFullElements()).subList(min, max);
        assertTrue(getCollection().containsAll(col), "Full collection should containAll partial full elements");
        assertTrue(getCollection().containsAll(getCollection()), "Full collection should containAll itself");
        verify();

        col = new ArrayList<>(Arrays.asList(getFullElements()));
        col.addAll(Arrays.asList(getFullElements()));
        assertTrue(getCollection().containsAll(col), "Full collection should containAll duplicate full elements");
        verify();
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
