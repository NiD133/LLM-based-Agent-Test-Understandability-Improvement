package org.locationtech.spatial4j.io;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGeohashUtils_testLookupHashLenForWidthHeight {

    @Test
    public void testLookupHashLenForWidthHeight() {
        assertHashLengthForDegrees(1, 999, 999);
        assertHashLengthForDegrees(1, 999, 46);
        assertHashLengthForDegrees(1, 46, 999);

        assertHashLengthForDegrees(2, 44, 999);
        assertHashLengthForDegrees(2, 999, 44);
        assertHashLengthForDegrees(2, 999, 5.7);
        assertHashLengthForDegrees(2, 11.3, 999);

        assertHashLengthForDegrees(3, 999, 5.5);
        assertHashLengthForDegrees(3, 11.1, 999);

        assertHashLengthForDegrees(GeohashUtils.MAX_PRECISION, 10e-20, 10e-20);
    }

    private void assertHashLengthForDegrees(int expectedHashLength, double lonErr, double latErr) {
        assertEquals(expectedHashLength, GeohashUtils.lookupHashLenForWidthHeight(lonErr, latErr));
    }
}
