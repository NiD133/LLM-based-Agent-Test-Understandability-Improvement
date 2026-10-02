package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortByteArray extends AbstractLangTest {

    @Test
    void testSortByteArray() {
        final byte[] expectedSortedBytes = { 2, 1 };
        final byte[] bytesToSort = expectedSortedBytes.clone();

        Arrays.sort(expectedSortedBytes);

        assertArrayEquals(expectedSortedBytes, ArraySorter.sort(bytesToSort));
        assertNull(ArraySorter.sort((byte[]) null));
    }
}
