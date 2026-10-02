package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testSnad {

    private static final String NAME_WITH_SCH_PREFIX = "Schmidt";
    private static final String EXPECTED_NYSIIS_CODE = "SNAD";
    private final Nysiis nysiis = new Nysiis();

    @Test
    void testSnad() {
        // Data Quality and Record Linkage Techniques P.121 claims this is SNAT,
        // but the NYSIIS implementation should encode it as SNAD.
        assertEquals(
                EXPECTED_NYSIIS_CODE,
                nysiis.encode(NAME_WITH_SCH_PREFIX),
                "Problem with " + NAME_WITH_SCH_PREFIX);
    }
}
