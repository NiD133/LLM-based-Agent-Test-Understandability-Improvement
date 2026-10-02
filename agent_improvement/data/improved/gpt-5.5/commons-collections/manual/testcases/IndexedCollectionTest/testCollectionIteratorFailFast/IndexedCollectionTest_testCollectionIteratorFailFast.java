package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionIteratorFailFast {

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

    public Collection<String> getCollection() {
        return collection;
    }

    public Collection<String> getConfirmed() {
        return confirmed;
    }

    public boolean isAddSupported() {
        return true;
    }

    public boolean isFailFastSupported() {
        return false;
    }

    public boolean isRemoveSupported() {
        return true;
    }

    public Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(getFullElements()));
    }

    public Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(getFullElements())));
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

        final Object[] confirmedValues = getConfirmed().toArray();
        final boolean[] matched = new boolean[confirmedSize];
        for (final String value : getCollection()) {
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
     * Tests that the collection's iterator is fail-fast.
     */
    @Test
    void testCollectionIteratorFailFast() {
        if (!isFailFastSupported()) {
            return;
        }
        if (isAddSupported()) {
            resetFull();
            final Iterator<String> iter0 = getCollection().iterator();
            final String o = getOtherElements()[0];
            getCollection().add(o);
            getConfirmed().add(o);
            assertThrows(ConcurrentModificationException.class, () -> iter0.next(),
                    "next after add should raise ConcurrentModification");
            verify();

            resetFull();
            final Iterator<String> iter = getCollection().iterator();
            getCollection().addAll(Arrays.asList(getOtherElements()));
            getConfirmed().addAll(Arrays.asList(getOtherElements()));
            assertThrows(ConcurrentModificationException.class, () -> iter.next(),
                    "next after addAll should raise ConcurrentModification");
            verify();
        }
        if (!isRemoveSupported()) {
            return;
        }

        resetFull();
        final Iterator<String> iter = getCollection().iterator();
        getCollection().clear();
        try {
            iter.next();
            fail("next after clear should raise ConcurrentModification");
        } catch (final ConcurrentModificationException | NoSuchElementException e) {
            // Both exceptions are legal for this case.
        }

        resetFull();
        final Iterator<String> iter0 = getCollection().iterator();
        getCollection().remove(getFullElements()[0]);
        assertThrows(ConcurrentModificationException.class, () -> iter0.next(),
                "next after remove should raise ConcurrentModification");

        resetFull();
        final Iterator<String> iter1 = getCollection().iterator();
        getCollection().removeIf(e -> false);
        assertThrows(ConcurrentModificationException.class, () -> iter1.next(),
                "next after removeIf should raise ConcurrentModification");

        resetFull();
        final Iterator<String> iter2 = getCollection().iterator();
        final List<String> sublist = Arrays.asList(getFullElements()).subList(2, 5);
        getCollection().removeAll(sublist);
        assertThrows(ConcurrentModificationException.class, () -> iter2.next(),
                "next after removeAll should raise ConcurrentModification");

        resetFull();
        final Iterator<String> iter3 = getCollection().iterator();
        final List<String> sublist3 = Arrays.asList(getFullElements()).subList(2, 5);
        getCollection().retainAll(sublist3);
        assertThrows(ConcurrentModificationException.class, () -> iter3.next(),
                "next after retainAll should raise ConcurrentModification");
    }

    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
