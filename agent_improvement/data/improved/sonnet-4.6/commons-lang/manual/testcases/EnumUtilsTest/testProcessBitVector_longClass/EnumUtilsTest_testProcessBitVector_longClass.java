package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testProcessBitVector_longClass extends AbstractLangTest {

    @Test
    @DisplayName("processBitVector throws IllegalArgumentException when enum has more than 64 constants")
    void testProcessBitVector_longClass() {
        // TooMany has more than 64 enum constants, which exceeds the capacity of a single long bit vector
        assertIllegalArgumentException(() -> EnumUtils.processBitVector(TooMany.class, 0L));
    }
}
