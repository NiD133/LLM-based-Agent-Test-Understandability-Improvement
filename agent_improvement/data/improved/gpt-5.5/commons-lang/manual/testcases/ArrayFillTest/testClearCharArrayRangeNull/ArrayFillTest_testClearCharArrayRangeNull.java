package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearCharArrayRangeNull extends AbstractLangTest {

    @Test
    void testClearCharArrayRangeNull() {
        final char[] clearedArray = ArrayFill.clear(null, 0, 0);

        assertNull(clearedArray);
    }
}
