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
 */
public class TestGeohashUtils {
  private static final double DEFAULT_DECODE_TOLERANCE = 0.00001D;
  private static final double PRECISE_DECODE_TOLERANCE = 0.000001D;

  private final SpatialContext ctx = SpatialContext.GEO;

  @Test
  public void testEncode() {
    String hash = GeohashUtils.encodeLatLon(42.6, -5.6);
    assertEquals("ezs42e44yx96", hash);

    hash = GeohashUtils.encodeLatLon(57.64911, 10.40744);
    assertEquals("u4pruydqqvj8", hash);
  }

  @Test
  public void testDecodePreciseLongitudeLatitude() {
    String hash = GeohashUtils.encodeLatLon(52.3738007, 4.8909347);

    Point point = GeohashUtils.decode(hash, ctx);

    assertPointEquals(52.3738007, 4.8909347, point, DEFAULT_DECODE_TOLERANCE);
  }

  @Test
  public void testDecodeImpreciseLongitudeLatitude() {
    String hash = GeohashUtils.encodeLatLon(84.6, 10.5);

    Point point = GeohashUtils.decode(hash, ctx);

    assertPointEquals(84.6, 10.5, point, DEFAULT_DECODE_TOLERANCE);
  }

  /*
   * See https://issues.apache.org/jira/browse/LUCENE-1815 for details.
   */
  @Test
  public void testDecodeEncode() {
    String geoHash = "u173zq37x014";
    assertEquals(geoHash, GeohashUtils.encodeLatLon(52.3738007, 4.8909347));
    Point point = GeohashUtils.decode(geoHash, ctx);
    assertPointEquals(52.37380061d, 4.8909343d, point, PRECISE_DECODE_TOLERANCE);

    assertEquals(geoHash, GeohashUtils.encodeLatLon(point.getY(), point.getX()));

    geoHash = "u173";
    point = GeohashUtils.decode("u173", ctx);
    geoHash = GeohashUtils.encodeLatLon(point.getY(), point.getX());
    final Point point2 = GeohashUtils.decode(geoHash, ctx);
    assertEquals(point.getY(), point2.getY(), PRECISE_DECODE_TOLERANCE);
    assertEquals(point.getX(), point2.getX(), PRECISE_DECODE_TOLERANCE);
  }

  /** See the table at http://en.wikipedia.org/wiki/Geohash. */
  @Test
  public void testHashLenToWidth() {
    double[] boxOdd = GeohashUtils.lookupDegreesSizeForHashLen(3);
    assertEquals(1.40625, boxOdd[0], 0.0001);
    assertEquals(1.40625, boxOdd[1], 0.0001);

    double[] boxEven = GeohashUtils.lookupDegreesSizeForHashLen(4);
    assertEquals(0.1757, boxEven[0], 0.0001);
    assertEquals(0.3515, boxEven[1], 0.0001);
  }

  /** See the table at http://en.wikipedia.org/wiki/Geohash. */
  @Test
  public void testLookupHashLenForWidthHeight() {
    assertEquals(1, GeohashUtils.lookupHashLenForWidthHeight(999, 999));

    assertEquals(1, GeohashUtils.lookupHashLenForWidthHeight(999, 46));
    assertEquals(1, GeohashUtils.lookupHashLenForWidthHeight(46, 999));

    assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight(44, 999));
    assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight(999, 44));
    assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight(999, 5.7));
    assertEquals(2, GeohashUtils.lookupHashLenForWidthHeight(11.3, 999));

    assertEquals(3, GeohashUtils.lookupHashLenForWidthHeight(999, 5.5));
    assertEquals(3, GeohashUtils.lookupHashLenForWidthHeight(11.1, 999));

    assertEquals(GeohashUtils.MAX_PRECISION,
        GeohashUtils.lookupHashLenForWidthHeight(10e-20, 10e-20));
  }

  private static void assertPointEquals(
      double expectedLatitude,
      double expectedLongitude,
      Point actualPoint,
      double tolerance) {
    assertEquals(expectedLatitude, actualPoint.getY(), tolerance);
    assertEquals(expectedLongitude, actualPoint.getX(), tolerance);
  }
}
