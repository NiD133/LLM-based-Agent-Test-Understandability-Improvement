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
 * Tests for {@link GeohashUtils}
 */
public class TestGeohashUtils {
  SpatialContext ctx = SpatialContext.GEO;

  // Known encode inputs and their expected geohash strings
  private static final double ENCODE_LAT_1   = 42.6;
  private static final double ENCODE_LON_1   = -5.6;
  private static final String EXPECTED_HASH_1 = "ezs42e44yx96";

  private static final double ENCODE_LAT_2   = 57.64911;
  private static final double ENCODE_LON_2   = 10.40744;
  private static final String EXPECTED_HASH_2 = "u4pruydqqvj8";

  // Coordinate pair used across several round-trip tests
  private static final double ROUNDTRIP_LAT = 52.3738007;
  private static final double ROUNDTRIP_LON = 4.8909347;

  // The precise 12-char hash that ROUNDTRIP_LAT/LON encodes to
  private static final String FULL_PRECISION_HASH = "u173zq37x014";

  // Tolerance used for decoded coordinate comparisons
  private static final double COARSE_DELTA = 0.00001D;
  private static final double FINE_DELTA   = 0.000001D;

  /**
   * Verifies that two well-known coordinate pairs encode to their expected geohash strings.
   */
  @Test
  public void testEncode() {
    String hash1 = GeohashUtils.encodeLatLon(ENCODE_LAT_1, ENCODE_LON_1);
    assertEquals(EXPECTED_HASH_1, hash1);

    String hash2 = GeohashUtils.encodeLatLon(ENCODE_LAT_2, ENCODE_LON_2);
    assertEquals(EXPECTED_HASH_2, hash2);
  }

  /**
   * Verifies that encoding then decoding a coordinate in a high-density area
   * (Amsterdam) recovers the original values within 0.00001 degrees.
   */
  @Test
  public void testDecodePreciseLongitudeLatitude() {
    String hash = GeohashUtils.encodeLatLon(ROUNDTRIP_LAT, ROUNDTRIP_LON);
    Point decoded = GeohashUtils.decode(hash, ctx);

    assertEquals(ROUNDTRIP_LAT, decoded.getY(), COARSE_DELTA);
    assertEquals(ROUNDTRIP_LON, decoded.getX(), COARSE_DELTA);
  }

  /**
   * Verifies that encoding then decoding a high-latitude coordinate (near the
   * Arctic) recovers the original values within 0.00001 degrees.
   */
  @Test
  public void testDecodeImpreciseLongitudeLatitude() {
    double highLat = 84.6;
    double lon     = 10.5;

    String hash = GeohashUtils.encodeLatLon(highLat, lon);
    Point decoded = GeohashUtils.decode(hash, ctx);

    assertEquals(highLat, decoded.getY(), COARSE_DELTA);
    assertEquals(lon,     decoded.getX(), COARSE_DELTA);
  }

  /**
   * Tests encode/decode round-trips for both a full-precision (12-char) and a
   * short (4-char) geohash.
   *
   * see https://issues.apache.org/jira/browse/LUCENE-1815 for details
   */
  @Test
  public void testDecodeEncode() {
    // --- Full-precision round-trip ---
    // The coordinates should encode to exactly FULL_PRECISION_HASH.
    assertEquals(FULL_PRECISION_HASH, GeohashUtils.encodeLatLon(ROUNDTRIP_LAT, ROUNDTRIP_LON));

    // Decoding that hash should recover values very close to the originals.
    Point decodedFull = GeohashUtils.decode(FULL_PRECISION_HASH, ctx);
    assertEquals(52.37380061d, decodedFull.getY(), FINE_DELTA);
    assertEquals(4.8909343d,   decodedFull.getX(), FINE_DELTA);

    // Re-encoding the decoded point must reproduce the same hash (idempotency).
    assertEquals(FULL_PRECISION_HASH, GeohashUtils.encodeLatLon(decodedFull.getY(), decodedFull.getX()));

    // --- Short hash round-trip ---
    // Decode a 4-char hash, re-encode it, then decode again; the two decoded
    // points must be equal (decode → encode → decode is stable).
    String shortHash = "u173";
    Point decodedShort     = GeohashUtils.decode(shortHash, ctx);
    String reEncodedShort  = GeohashUtils.encodeLatLon(decodedShort.getY(), decodedShort.getX());
    Point reDecodedShort   = GeohashUtils.decode(reEncodedShort, ctx);

    assertEquals(decodedShort.getY(), reDecodedShort.getY(), FINE_DELTA);
    assertEquals(decodedShort.getX(), reDecodedShort.getX(), FINE_DELTA);
  }

  /**
   * Verifies the lat/lon cell sizes returned for a given hash length against the
   * reference table at http://en.wikipedia.org/wiki/Geohash.
   * Odd-length hashes produce equal-sided cells; even-length hashes are wider than tall.
   */
  @Test
  public void testHashLenToWidth() {
    // Odd hash length (3): cell height == cell width == ~1.40625°
    double[] oddLenBox  = GeohashUtils.lookupDegreesSizeForHashLen(3);
    double oddLatHeight = oddLenBox[0];
    double oddLonWidth  = oddLenBox[1];
    assertEquals(1.40625, oddLatHeight, 0.0001);
    assertEquals(1.40625, oddLonWidth,  0.0001);

    // Even hash length (4): cell is taller (~0.1757°) than it is wide (~0.3515°)
    double[] evenLenBox  = GeohashUtils.lookupDegreesSizeForHashLen(4);
    double evenLatHeight = evenLenBox[0];
    double evenLonWidth  = evenLenBox[1];
    assertEquals(0.1757, evenLatHeight, 0.0001);
    assertEquals(0.3515, evenLonWidth,  0.0001);
  }

  /**
   * Verifies that the shortest hash length sufficient to cover a given
   * width×height bounding box is returned correctly.
   *
   * see the table at http://en.wikipedia.org/wiki/Geohash
   */
  @Test
  public void testLookupHashLenForWidthHeight() {
    // Very large bounding boxes fit within a single-character hash
    assertEquals(1, GeohashUtils.lookupHashLenForWidthHeight(999, 999));
    assertEquals(1, GeohashUtils.lookupHashLenForWidthHeight(999,  46));
    assertEquals(1, GeohashUtils.lookupHashLenForWidthHeight( 46, 999));

    // Boxes just below the level-1 threshold require a 2-character hash
    assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight( 44, 999));
    assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight(999,  44));
    assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight(999, 5.7));
    assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight(11.3, 999));

    // Boxes just below the level-2 threshold require a 3-character hash
    assertEquals(3, GeohashUtils.lookupHashLenForWidthHeight(999, 5.5));
    assertEquals(3, GeohashUtils.lookupHashLenForWidthHeight(11.1, 999));

    // An extremely small box requires maximum precision
    assertEquals(GeohashUtils.MAX_PRECISION, GeohashUtils.lookupHashLenForWidthHeight(10e-20, 10e-20));
  }
}
