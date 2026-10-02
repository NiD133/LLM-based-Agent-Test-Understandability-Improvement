package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link EnumUtils#processBitVectors(Class, long...)} rejects a {@code null}
 * enum class by throwing a {@link NullPointerException}.
 */
public class EnumUtilsTest_testProcessBitVectors_nullClass extends AbstractLangTest {

    @Test
    void testProcessBitVectors_nullClass() {
        final Class<Traffic> nullEnumClass = null;

        assertNullPointerException(() -> EnumUtils.processBitVectors(nullEnumClass, 0L));
    }
}
