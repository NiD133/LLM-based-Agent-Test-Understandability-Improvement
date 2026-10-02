package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.function.FailableIntFunction;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearCharArrayRangeNull extends AbstractLangTest {

    @Test
    void testClearCharArrayRangeNull() {
        final char[] actual = ArrayFill.clear(null, 0, 0);
        assertNull(actual);
    }
}
