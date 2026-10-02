package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillObjectArrayNull extends AbstractLangTest {

    @Test
    void testFillObjectArrayNull() {
        final Object[] array = null;
        final Object val = null;
        final Object[] actual = ArrayFill.fill(array, val);
        assertSame(array, actual);
    }
}
