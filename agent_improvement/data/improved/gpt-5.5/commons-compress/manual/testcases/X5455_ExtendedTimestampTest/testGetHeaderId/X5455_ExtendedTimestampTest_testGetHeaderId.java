package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class X5455_ExtendedTimestampTest_testGetHeaderId {

    private static final ZipShort X5455_HEADER_ID = new ZipShort(0x5455);

    private X5455_ExtendedTimestamp extendedTimestamp;

    @BeforeEach
    public void before() {
        extendedTimestamp = new X5455_ExtendedTimestamp();
    }

    @Test
    void testGetHeaderId() {
        assertEquals(X5455_HEADER_ID, extendedTimestamp.getHeaderId());
    }
}
