package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testProcessBitVector_longClass extends AbstractLangTest {

    private enum TooMany {
        VALUE_01, VALUE_02, VALUE_03, VALUE_04, VALUE_05, VALUE_06, VALUE_07, VALUE_08,
        VALUE_09, VALUE_10, VALUE_11, VALUE_12, VALUE_13, VALUE_14, VALUE_15, VALUE_16,
        VALUE_17, VALUE_18, VALUE_19, VALUE_20, VALUE_21, VALUE_22, VALUE_23, VALUE_24,
        VALUE_25, VALUE_26, VALUE_27, VALUE_28, VALUE_29, VALUE_30, VALUE_31, VALUE_32,
        VALUE_33, VALUE_34, VALUE_35, VALUE_36, VALUE_37, VALUE_38, VALUE_39, VALUE_40,
        VALUE_41, VALUE_42, VALUE_43, VALUE_44, VALUE_45, VALUE_46, VALUE_47, VALUE_48,
        VALUE_49, VALUE_50, VALUE_51, VALUE_52, VALUE_53, VALUE_54, VALUE_55, VALUE_56,
        VALUE_57, VALUE_58, VALUE_59, VALUE_60, VALUE_61, VALUE_62, VALUE_63, VALUE_64,
        VALUE_65
    }

    @Test
    void testProcessBitVector_longClass() {
        assertIllegalArgumentException(() -> EnumUtils.processBitVector(TooMany.class, 0L));
    }
}
