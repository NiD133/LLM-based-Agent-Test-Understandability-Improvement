/*******************************************************************************
 * Copyright (c) 2015 Voyager Search and MITRE
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0 which
 * accompanies this distribution and is available at
 *    http://www.apache.org/licenses/LICENSE-2.0.txt
 ******************************************************************************/

package org.locationtech.spatial4j.io;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link GeohashUtils}.
 *
 * <p>Geohash reference tables and worked examples come from
 * <a href="http://en.wikipedia.org/wiki/Geohash">the Geohash Wikipedia page</a>.
 */
public class TestGeohashUtils {

  /** Geohashes decode to longitude (X) / latitude (Y) within this spatial context. */
  private final SpatialContext geoContext = SpatialContext.GEO;

  /**
   * Encoding a coordinate must yield the well-known geohash string for that point.
   */
  @Test
  public void encodeLatLon_producesExpectedGeohashStrings() {
    assertEquals("ezs42e44yx96", GeohashUtils.encodeLatLon(42.6, -5.6));
    assertEquals("u4pruydqqvj8", GeohashUtils.encodeLatLon(57.64911, 10.40744));
  }

  /**
   * For a high-precision coordinate, encoding then decoding must round-trip back
   * to the original latitude/longitude (within a tight tolerance).
   */
  @Test
  public void encodeThenDecode_roundTripsPreciseCoordinate() {
    double expectedLatitude = 52.3738007;
    double expectedLongitude = 4.8909347;
    double tolerance = 0.00001D;

    String geohash = GeohashUtils.encodeLatLon(expectedLatitude, expectedLongitude);
    Point decoded = GeohashUtils.decode(geohash, geoContext);

    assertEquals(expectedLatitude, decoded.getY(), tolerance);
    assertEquals(expectedLongitude, decoded.getX(), tolerance);
  }

  /**
   * The round-trip also holds for a coarser coordinate near the poles.
   */
  @Test
  public void encodeThenDecode_roundTripsImpreciseCoordinate() {
    double expectedLatitude = 84.6;
    double expectedLongitude = 10.5;
    double tolerance = 0.00001D;

    String geohash = GeohashUtils.encodeLatLon(expectedLatitude, expectedLongitude);
    Point decoded = GeohashUtils.decode(geohash, geoContext);

    assertEquals(expectedLatitude, decoded.getY(), tolerance);
    assertEquals(expectedLongitude, decoded.getX(), tolerance);
  }

  /**
   * Regression test for a known fixed geohash. See
   * <a href="https://issues.apache.org/jira/browse/LUCENE-1815">LUCENE-1815</a>.
   *
   * <p>Verifies that encoding yields the exact expected hash, that decoding lands
   * on the expected coordinate, and that re-encoding the decoded point is stable.
   */
  @Test
  public void encodeDecode_isStableForKnownGeohash() {
    double tolerance = 0.000001D;

    // A full 12-character geohash encodes to a fixed, known string.
    String fullGeohash = "u173zq37x014";
    assertEquals(fullGeohash, GeohashUtils.encodeLatLon(52.3738007, 4.8909347));

    Point decoded = GeohashUtils.decode(fullGeohash, geoContext);
    assertEquals(52.37380061d, decoded.getY(), tolerance);
    assertEquals(4.8909343d, decoded.getX(), tolerance);

    // Re-encoding the decoded point reproduces the same geohash.
    assertEquals(fullGeohash, GeohashUtils.encodeLatLon(decoded.getY(), decoded.getX()));

    // For a truncated (lower-precision) geohash, decode->encode->decode is stable:
    // the second decode lands on the same point as the first.
    Point decodedShort = GeohashUtils.decode("u173", geoContext);
    String reEncoded = GeohashUtils.encodeLatLon(decodedShort.getY(), decodedShort.getX());
    Point reDecoded = GeohashUtils.decode(reEncoded, geoContext);

    assertEquals(decodedShort.getY(), reDecoded.getY(), tolerance);
    assertEquals(decodedShort.getX(), reDecoded.getX(), tolerance);
  }

  /**
   * The latitude-height / longitude-width of a geohash cell for a given hash length
   * must match the published Geohash size table, for both odd and even lengths.
   *
   * <p>{@code lookupDegreesSizeForHashLen} returns {@code [latHeight, lonWidth]}.
   * See the table at <a href="http://en.wikipedia.org/wiki/Geohash">Wikipedia</a>.
   */
  @Test
  public void lookupDegreesSizeForHashLen_matchesPublishedCellSizes() {
    double tolerance = 0.0001;

    // Odd hash length (3): latitude height and longitude width are equal.
    double[] oddLenCell = GeohashUtils.lookupDegreesSizeForHashLen(3);
    assertEquals(1.40625, oddLenCell[0], tolerance); // latitude height
    assertEquals(1.40625, oddLenCell[1], tolerance); // longitude width

    // Even hash length (4): latitude height is half the longitude width.
    double[] evenLenCell = GeohashUtils.lookupDegreesSizeForHashLen(4);
    assertEquals(0.1757, evenLenCell[0], tolerance); // latitude height
    assertEquals(0.3515, evenLenCell[1], tolerance); // longitude width
  }

  /**
   * {@code lookupHashLenForWidthHeight(lonErr, latErr)} returns the shortest hash
   * length whose cell is strictly smaller than the requested width and height.
   *
   * <p>See the table at <a href="http://en.wikipedia.org/wiki/Geohash">Wikipedia</a>.
   */
  @Test
  public void lookupHashLenForWidthHeight_returnsShortestSufficientLength() {
    // Very coarse tolerances are satisfied by the shortest length.
    assertEquals(1, GeohashUtils.lookupHashLenForWidthHeight(999, 999));
    assertEquals(1, GeohashUtils.lookupHashLenForWidthHeight(999, 46));
    assertEquals(1, GeohashUtils.lookupHashLenForWidthHeight(46, 999));

    // Tightening either dimension forces a longer hash.
    assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight(44, 999));
    assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight(999, 44));
    assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight(999, 5.7));
    assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight(11.3, 999));

    assertEquals(3, GeohashUtils.lookupHashLenForWidthHeight(999, 5.5));
    assertEquals(3, GeohashUtils.lookupHashLenForWidthHeight(11.1, 999));

    // An effectively-zero tolerance demands the maximum supported precision.
    assertEquals(GeohashUtils.MAX_PRECISION,
        GeohashUtils.lookupHashLenForWidthHeight(10e-20, 10e-20));
  }
}
