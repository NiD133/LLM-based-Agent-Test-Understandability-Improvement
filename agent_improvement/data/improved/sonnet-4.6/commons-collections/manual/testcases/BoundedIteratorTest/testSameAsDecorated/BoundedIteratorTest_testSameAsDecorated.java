package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testSameAsDecorated {

    /** 7-element list used as the underlying data source. */
    private final String[] testArray = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(testArray);
    }

    /**
     * A BoundedIterator with offset=0 and max=list.size() must behave identically
     * to the underlying iterator: return every element in order, then report
     * hasNext()=false and throw NoSuchElementException on the next call to next().
     */
    @Test
    void testSameAsDecorated() {
        final Iterator<String> iter = new BoundedIterator<>(testList.iterator(), 0, testList.size());

        assertTrue(iter.hasNext());
        assertEquals("a", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("b", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("c", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("d", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("e", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("f", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("g", iter.next());

        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());
    }
}
