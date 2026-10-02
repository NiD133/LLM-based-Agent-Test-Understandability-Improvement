package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testRemove {

    private final String[] testArray = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(testArray);
    }

    public Iterator<String> makeObject() {
        return new BoundedIterator<>(new ArrayList<>(testList).iterator(), 1, testList.size() - 1);
    }

    public boolean supportsRemove() {
        return true;
    }

    public void verify() {
        // No additional verification required for this extracted test.
    }

    @Test
    void testRemove() {
        final Iterator<String> it = makeObject();
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
