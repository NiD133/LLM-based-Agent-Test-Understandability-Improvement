package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testProcessBitVectors_nullClass extends AbstractLangTest {

    @Test
    @DisplayName("processBitVectors throws NullPointerException when enum class is null")
    void testProcessBitVectors_nullClass() {
        final Class<Traffic> nullEnumClass = null;
        assertNullPointerException(() -> EnumUtils.processBitVectors(nullEnumClass, 0L));
    }
}
