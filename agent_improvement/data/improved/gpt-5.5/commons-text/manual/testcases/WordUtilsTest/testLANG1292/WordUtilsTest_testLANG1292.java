package org.apache.commons.text;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testLANG1292 {

    private static final int WRAP_LENGTH = 70;
    private static final String LANG_1292_REGRESSION_INPUT =
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    @Test
    void testLANG1292() {
        // Prior to the LANG-1292 fix, this call threw StringIndexOutOfBoundsException.
        WordUtils.wrap(LANG_1292_REGRESSION_INPUT, WRAP_LENGTH);
    }
}
