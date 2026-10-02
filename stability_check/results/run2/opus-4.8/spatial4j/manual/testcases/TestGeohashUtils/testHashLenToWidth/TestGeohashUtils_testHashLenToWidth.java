package org.locationtech.spatial4j.io;

import org.locationtech.spatial4j.context.SpatialContext;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Verifies {@link GeohashUtils#lookupDegreesSizeForHashLen(int)}, which returns
 * the geographic cell size for a geohash of a given length as a
 * {@code [latitudeHeight, longitudeWidth]} pair (both in degrees).
 *
 * <p>Because a geohash alternates between splitting longitude and latitude at
 * each character, odd- and even-length hashes yield different cell shapes.
 * Expected values follow the reference table at
 * <a href="http://en.wikipedia.org/wiki/Geohash">Wikipedia: Geohash</a>.
 */
public class TestGeohashUtils_testHashLenToWidth {

    /** Index of the latitude height within the returned size pair. */
    private static final int LAT_HEIGHT = 0;
    /** Index of the longitude width within the returned size pair. */
    private static final int LON_WIDTH = 1;
    /** Tolerance for comparing degree values. */
    private static final double DEGREES_TOLERANCE = 0.0001;

    SpatialContext ctx = SpatialContext.GEO;

    @Test
    public void oddLengthHashHasSquareCell() {
        double[] cellSize = GeohashUtils.lookupDegreesSizeForHashLen(3);

        assertEquals(1.40625, cellSize[LAT_HEIGHT], DEGREES_TOLERANCE);
        assertEquals(1.40625, cellSize[LON_WIDTH], DEGREES_TOLERANCE);
    }

    @Test
    public void evenLengthHashHasTallerThanWideCell() {
        double[] cellSize = GeohashUtils.lookupDegreesSizeForHashLen(4);

        assertEquals(0.1757, cellSize[LAT_HEIGHT], DEGREES_TOLERANCE);
        assertEquals(0.3515, cellSize[LON_WIDTH], DEGREES_TOLERANCE);
    }
}
