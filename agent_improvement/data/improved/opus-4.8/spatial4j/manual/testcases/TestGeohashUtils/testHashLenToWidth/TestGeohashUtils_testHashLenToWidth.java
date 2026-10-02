package org.locationtech.spatial4j.io;

import org.locationtech.spatial4j.context.SpatialContext;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Verifies {@link GeohashUtils#lookupDegreesSizeForHashLen(int)}, which maps a geohash
 * length to the size, in degrees, of the cell it represents.
 * <p>
 * The returned array follows the convention {@code [latitudeHeight, longitudeWidth]}.
 * Because geohash bits alternate between longitude and latitude, odd and even lengths
 * behave differently, so both cases are checked.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Geohash">Geohash reference table</a>
 */
public class TestGeohashUtils_testHashLenToWidth {

    /** Index of the latitude height within the array returned by lookupDegreesSizeForHashLen. */
    private static final int LAT_HEIGHT = 0;
    /** Index of the longitude width within the array returned by lookupDegreesSizeForHashLen. */
    private static final int LON_WIDTH = 1;

    /** Tolerance for comparing the floating-point cell dimensions. */
    private static final double TOLERANCE = 0.0001;

    SpatialContext ctx = SpatialContext.GEO;

    @Test
    public void testHashLenToWidth() {
        // Odd length: latitude and longitude cells are square (equal degrees).
        double[] oddLengthCell = GeohashUtils.lookupDegreesSizeForHashLen(3);
        assertEquals(1.40625, oddLengthCell[LAT_HEIGHT], TOLERANCE);
        assertEquals(1.40625, oddLengthCell[LON_WIDTH], TOLERANCE);

        // Even length: the extra bit makes the longitude cell twice as wide as it is tall.
        double[] evenLengthCell = GeohashUtils.lookupDegreesSizeForHashLen(4);
        assertEquals(0.1757, evenLengthCell[LAT_HEIGHT], TOLERANCE);
        assertEquals(0.3515, evenLengthCell[LON_WIDTH], TOLERANCE);
    }
}
