package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testAbbreviateUpperLessThanLowerValues {

    @Test
    void testAbbreviateUpperLessThanLowerValues() {
        // upper (2) < lower (5): abbreviate must reject this as an illegal argument
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate("0123456789", 5, 2, ""));
    }
}
