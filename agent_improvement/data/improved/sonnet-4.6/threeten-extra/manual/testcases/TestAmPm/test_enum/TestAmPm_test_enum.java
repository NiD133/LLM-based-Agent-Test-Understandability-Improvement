package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestAmPm_test_enum {

    @Test
    @DisplayName("AmPm.valueOf(\"AM\") resolves to the AmPm.AM constant")
    public void test_valueOfAM_returnsAmConstant() {
        assertEquals(AmPm.AM, AmPm.valueOf("AM"));
    }

    @Test
    @DisplayName("AmPm.values()[0] is AmPm.AM, confirming AM is declared first in the enum")
    public void test_valuesFirstElement_isAM() {
        assertEquals(AmPm.AM, AmPm.values()[0]);
    }
}
