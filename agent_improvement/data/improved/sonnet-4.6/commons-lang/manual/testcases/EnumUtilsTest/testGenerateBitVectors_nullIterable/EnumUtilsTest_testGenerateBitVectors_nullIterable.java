package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nullIterable extends AbstractLangTest {

    /**
     * Verifies that generateBitVectors throws NullPointerException when both the
     * enum class and the iterable of values are null.
     */
    @Test
    void testGenerateBitVectors_nullIterable() {
        assertNullPointerException(() -> EnumUtils.generateBitVectors(null, (Iterable<Traffic>) null));
    }
}
