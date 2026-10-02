package org.locationtech.spatial4j.io;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestGeohashUtils_testHashLenToWidth {

    // lookupDegreesSizeForHashLen returns {latHeight, lonWidth}
    private static final int LAT_HEIGHT_IDX = 0;
    private static final int LON_WIDTH_IDX  = 1;

    private static final double DELTA = 0.0001;

    SpatialContext ctx = SpatialContext.GEO;

    /**
     * Verifies geohash cell dimensions against the reference table at
     * http://en.wikipedia.org/wiki/Geohash for both odd-length (3) and
     * even-length (4) hashes.
     *
     * Odd-length (3): the cell is square — 1.40625° × 1.40625°.
     * Even-length (4): the cell is rectangular — 0.1757° tall × 0.3515° wide.
     */
    @Test
    public void testHashLenToWidth() {
        // Hash length 3 (odd) produces a square cell
        double[] oddLenCell = GeohashUtils.lookupDegreesSizeForHashLen(3);
        assertEquals("odd hash len 3: expected lat height ~1.40625°",
                1.40625, oddLenCell[LAT_HEIGHT_IDX], DELTA);
        assertEquals("odd hash len 3: expected lon width ~1.40625°",
                1.40625, oddLenCell[LON_WIDTH_IDX], DELTA);

        // Hash length 4 (even) produces a rectangular cell (wider than tall)
        double[] evenLenCell = GeohashUtils.lookupDegreesSizeForHashLen(4);
        assertEquals("even hash len 4: expected lat height ~0.1757°",
                0.1757, evenLenCell[LAT_HEIGHT_IDX], DELTA);
        assertEquals("even hash len 4: expected lon width ~0.3515°",
                0.3515, evenLenCell[LON_WIDTH_IDX], DELTA);
    }
}
