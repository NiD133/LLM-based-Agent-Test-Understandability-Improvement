package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectGraphIteratorTest_testRemove {

    private List<String> list1;
    private List<String> list2;
    private List<String> list3;
    private List<Iterator<String>> iteratorList;

    @BeforeEach
    public void setUp() {
        list1 = new ArrayList<>();
        list1.add("One");
        list1.add("Two");
        list1.add("Three");

        list2 = new ArrayList<>();
        list2.add("Four");

        list3 = new ArrayList<>();
        list3.add("Five");
        list3.add("Six");

        iteratorList = new ArrayList<>();
        iteratorList.add(list1.iterator());
        iteratorList.add(list2.iterator());
        iteratorList.add(list3.iterator());
    }

    public ObjectGraphIterator<Object> makeObject() {
        setUp();
        return new ObjectGraphIterator<>(iteratorList.iterator());
    }

    public boolean supportsRemove() {
        return true;
    }

    public void verify() {
        // No additional cross-checks are needed for this concrete fixture.
    }

    @Test
    void testRemove() {
        final Iterator<Object> it = makeObject();
        if (!supportsRemove()) {
            assertThrows(UnsupportedOperationException.class, it::remove);
            return;
        }

        assertThrows(IllegalStateException.class, it::remove);
        verify();

        it.next();
        it.remove();

        assertThrows(IllegalStateException.class, it::remove);
    }
}
