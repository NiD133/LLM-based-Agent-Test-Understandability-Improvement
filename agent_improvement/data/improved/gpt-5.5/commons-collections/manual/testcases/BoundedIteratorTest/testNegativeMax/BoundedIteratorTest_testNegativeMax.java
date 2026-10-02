package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testNegativeMax {

    private static final String[] TEST_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() throws Exception {
        testList = Arrays.asList(TEST_ARRAY);
    }

    /**
     * A negative {@code max} value is rejected by the constructor.
     */
    @Test
    void testNegativeMax() {
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> new BoundedIterator<>(testList.iterator(), 3, -1));

        assertEquals("Max parameter must not be negative.", thrown.getMessage());
    }
}
