package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testProcessBitVector_nullClass extends AbstractLangTest {

    @Test
    void testProcessBitVector_nullClass() {
        // processBitVector must reject a null enum class with NullPointerException
        final Class<Traffic> nullEnumClass = null;
        assertNullPointerException(() -> EnumUtils.processBitVector(nullEnumClass, 0L));
    }
}
