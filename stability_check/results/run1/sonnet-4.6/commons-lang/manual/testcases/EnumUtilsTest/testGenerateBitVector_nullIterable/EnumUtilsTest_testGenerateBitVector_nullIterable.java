package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nullIterable extends AbstractLangTest {

    @Test
    void testGenerateBitVector_nullIterable() {
        // generateBitVector must throw NullPointerException when the values iterable is null
        assertNullPointerException(() -> EnumUtils.generateBitVector(Traffic.class, (Iterable<Traffic>) null));
    }
}
