package org.locationtech.spatial4j.io;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests for GeohashUtils.lookupHashLenForWidthHeight(lonErr, latErr), which returns
 * the shortest geohash precision whose cell dimensions fit within the given lon/lat bounds.
 *
 * A cell at precision P satisfies the request when:
 *   cell_lon_width(P) < lonErr  AND  cell_lat_height(P) < latErr
 *
 * Geohash cell dimensions by precision (see http://en.wikipedia.org/wiki/Geohash):
 *   Precision 1: lon ~45°  × lat ~45°
 *   Precision 2: lon ~11.25° × lat ~5.625°
 *   Precision 3: lon ~1.406° × lat ~1.406°
 */
public class TestGeohashUtils_testLookupHashLenForWidthHeight {

    // A value larger than any geohash cell dimension (max lon width is 360°),
    // used to leave one axis unconstrained so only the other axis drives the result.
    private static final double UNCONSTRAINED = 999;

    // Approximate cell dimensions at precision 1 (degrees)
    private static final double P1_LON_WIDTH   = 45.0;
    private static final double P1_LAT_HEIGHT  = 45.0;

    // Approximate cell dimensions at precision 2 (degrees)
    private static final double P2_LON_WIDTH   = 11.25;
    private static final double P2_LAT_HEIGHT  = 5.625;

    /** see the table at http://en.wikipedia.org/wiki/Geohash */
    @Test
    public void testLookupHashLenForWidthHeight() {

        // --- Precision 1 ---
        // Both axes unconstrained: trivially satisfied by precision 1.
        assertEquals(1, GeohashUtils.lookupHashLenForWidthHeight(UNCONSTRAINED, UNCONSTRAINED));

        // lat bound just above the precision-1 cell height (~45°): precision 1 still satisfies it.
        assertEquals(1, GeohashUtils.lookupHashLenForWidthHeight(UNCONSTRAINED, 46));
        // lon bound just above the precision-1 cell width (~45°): precision 1 still satisfies it.
        assertEquals(1, GeohashUtils.lookupHashLenForWidthHeight(46, UNCONSTRAINED));

        // --- Precision 2 ---
        // lon bound just below the precision-1 cell width (~45°): precision 1 fails, precision 2 satisfies.
        assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight(44, UNCONSTRAINED));
        // lat bound just below the precision-1 cell height (~45°): precision 1 fails, precision 2 satisfies.
        assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight(UNCONSTRAINED, 44));

        // lat bound just above the precision-2 cell height (~5.625°): precision 2 satisfies it.
        assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight(UNCONSTRAINED, 5.7));
        // lon bound just above the precision-2 cell width (~11.25°): precision 2 satisfies it.
        assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight(11.3, UNCONSTRAINED));

        // --- Precision 3 ---
        // lat bound just below the precision-2 cell height (~5.625°): precision 2 fails, precision 3 satisfies.
        assertEquals(3, GeohashUtils.lookupHashLenForWidthHeight(UNCONSTRAINED, 5.5));
        // lon bound just below the precision-2 cell width (~11.25°): precision 2 fails, precision 3 satisfies.
        assertEquals(3, GeohashUtils.lookupHashLenForWidthHeight(11.1, UNCONSTRAINED));

        // --- MAX_PRECISION ---
        // Dimensions far below any standard precision cell: returns the maximum precision level.
        assertEquals(GeohashUtils.MAX_PRECISION, GeohashUtils.lookupHashLenForWidthHeight(10e-20, 10e-20));
    }
}
