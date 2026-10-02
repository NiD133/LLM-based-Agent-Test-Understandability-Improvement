package org.locationtech.spatial4j.io;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link GeohashUtils#lookupHashLenForWidthHeight(double, double)}, which returns the
 * shortest geohash length whose cell width and height are both strictly smaller than the
 * requested longitude error (width) and latitude error (height).
 *
 * <p>The expected geohash lengths below correspond to the cell-size table at
 * <a href="http://en.wikipedia.org/wiki/Geohash">http://en.wikipedia.org/wiki/Geohash</a>:
 * <ul>
 *   <li>length 1 &rarr; cell is 45&deg; tall &times; 45&deg; wide</li>
 *   <li>length 2 &rarr; cell is ~5.6&deg; tall &times; ~11.25&deg; wide</li>
 *   <li>length 3 &rarr; cell is ~1.4&deg; tall &times; ~1.4&deg; wide</li>
 * </ul>
 *
 * <p>Note the parameter order: {@code lookupHashLenForWidthHeight(lonErr, latErr)}, i.e.
 * the longitude/width tolerance comes first and the latitude/height tolerance second.
 */
public class TestGeohashUtils_testLookupHashLenForWidthHeight {

    @Test
    public void testLookupHashLenForWidthHeight() {
        // A tolerance larger than the whole world (length-1 cell: 45 wide x 45 tall)
        // is satisfied by the shortest geohash, length 1.
        assertEquals(1, lookupLen(/* lonErr */ 999, /* latErr */ 999));
        assertEquals(1, lookupLen(/* lonErr */ 999, /* latErr */ 46));
        assertEquals(1, lookupLen(/* lonErr */ 46, /* latErr */ 999));

        // Just below the length-1 cell size (45) on either axis forces length 2.
        assertEquals(2, lookupLen(/* lonErr */ 44, /* latErr */ 999));
        assertEquals(2, lookupLen(/* lonErr */ 999, /* latErr */ 44));

        // The length-2 cell height is ~5.6, so a height tolerance just above it
        // (5.7) still fits in length 2, but just below it (5.5) needs length 3.
        assertEquals(2, lookupLen(/* lonErr */ 999, /* latErr */ 5.7));
        assertEquals(3, lookupLen(/* lonErr */ 999, /* latErr */ 5.5));

        // The length-2 cell width is ~11.25, so a width tolerance just above it
        // (11.3) still fits in length 2, but just below it (11.1) needs length 3.
        assertEquals(2, lookupLen(/* lonErr */ 11.3, /* latErr */ 999));
        assertEquals(3, lookupLen(/* lonErr */ 11.1, /* latErr */ 999));

        // A vanishingly small tolerance can never be met, so the lookup saturates
        // at the maximum supported precision.
        assertEquals(GeohashUtils.MAX_PRECISION, lookupLen(/* lonErr */ 10e-20, /* latErr */ 10e-20));
    }

    /** Readable alias for the method under test, keeping the (lonErr, latErr) order. */
    private static int lookupLen(double lonErr, double latErr) {
        return GeohashUtils.lookupHashLenForWidthHeight(lonErr, latErr);
    }
}
