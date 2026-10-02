package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testProcessBitVectors_nullClass extends AbstractLangTest {

    @Test
    void testProcessBitVectors_nullClass() {
        final Class<java.util.concurrent.TimeUnit> nullEnumClass = null;

        assertNullPointerException(() -> EnumUtils.processBitVectors(nullEnumClass, 0L));
    }
}
