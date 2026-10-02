package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link X5455_ExtendedTimestamp} reports the header ID that
 * identifies it as the "UT" extended-timestamp extra field, i.e. the ZIP tag
 * {@code 0x5455}.
 */
public class X5455_ExtendedTimestampTest_testGetHeaderId {

    /** The "UT" extended-timestamp tag that {@code getHeaderId()} must return. */
    private static final ZipShort EXPECTED_HEADER_ID = new ZipShort(0x5455);

    /** The extended-timestamp field under test. */
    private X5455_ExtendedTimestamp extendedTimestamp;

    @BeforeEach
    public void before() {
        extendedTimestamp = new X5455_ExtendedTimestamp();
    }

    @Test
    void testGetHeaderId() {
        assertEquals(EXPECTED_HEADER_ID, extendedTimestamp.getHeaderId());
    }
}
