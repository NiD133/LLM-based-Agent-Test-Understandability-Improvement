package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillCharArrayRangeNull extends AbstractLangTest {

    @Test
    void testFillCharArrayRangeNull() {
        final char[] filledArray = ArrayFill.fill(null, 0, 0, 'Z');

        assertNull(filledArray);
    }
}
