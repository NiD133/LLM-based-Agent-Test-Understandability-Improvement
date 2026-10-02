package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NysiisTest_testTrueVariant {

    private static final int STRICT_CODE_MAX_LENGTH = 6;
    private static final String NAME_REQUIRING_STRICT_TRUNCATION = "WESTERLUND";
    private static final String STRICT_NYSIIS_CODE = "WASTAR";

    @Test
    void testTrueVariant() {
        final Nysiis strictEncoder = new Nysiis(true);

        final String encodedName = strictEncoder.encode(NAME_REQUIRING_STRICT_TRUNCATION);

        assertTrue(encodedName.length() <= STRICT_CODE_MAX_LENGTH);
        assertEquals(STRICT_NYSIIS_CODE, encodedName);
    }
}
