package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that X5455_ExtendedTimestamp reports the correct ZIP extra-field
 * header ID (0x5455, the "UT" / Unix Timestamp tag).
 */
public class X5455_ExtendedTimestampTest_testGetHeaderId {

    /** Expected header ID: the "UT" extra-field tag defined in the Info-ZIP spec. */
    private static final ZipShort EXPECTED_HEADER_ID = new ZipShort(0x5455);

    private X5455_ExtendedTimestamp xf;

    @BeforeEach
    public void setUp() {
        xf = new X5455_ExtendedTimestamp();
    }

    @Test
    void testGetHeaderId() {
        assertEquals(EXPECTED_HEADER_ID, xf.getHeaderId());
    }
}
