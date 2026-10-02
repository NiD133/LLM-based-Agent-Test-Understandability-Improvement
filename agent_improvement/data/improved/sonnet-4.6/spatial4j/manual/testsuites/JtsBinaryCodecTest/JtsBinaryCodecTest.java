/*******************************************************************************
 * Copyright (c) 2015 Voyager Search and MITRE
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0 which
 * accompanies this distribution and is available at
 *    http://www.apache.org/licenses/LICENSE-2.0.txt
 ******************************************************************************/

package org.locationtech.spatial4j.io;

import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;
import org.locationtech.spatial4j.shape.jts.JtsGeometry;

/**
 * Tests WKB (binary) round-tripping for JTS-backed geometry shapes.
 * Extends the base {@link BinaryCodecTest} with polygon-specific coverage
 * and uses FLOATING_SINGLE precision to exercise the float read/write path.
 */
public class JtsBinaryCodecTest extends BinaryCodecTest {

  // Full-sphere diameter in degrees, used to size the test circle polygon.
  private static final double CIRCLE_DIAMETER_DEGREES = 180;

  // Probability denominator: JTS polygons are produced roughly 1 in 4 calls.
  private static final int JTS_SHAPE_FREQUENCY = 4;

  /**
   * Returns a JTS spatial context configured with single-precision floating
   * point, which routes binary I/O through the float (not double) code path.
   */
  @Override
  public SpatialContext initContext() {
    JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
    factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
    return factory.newSpatialContext();
  }

  /**
   * Verifies that an arbitrary JTS polygon survives a binary encode/decode
   * round-trip with equality preserved.
   */
  @Test
  public void testPoly() throws Exception {
    JtsSpatialContext jtsCtx = (JtsSpatialContext) super.ctx;
    int vertexCount = randomIntBetween(3, 20);
    JtsGeometry polygon = jtsCtx.makeShape(randomGeometry(vertexCount), false, false);
    assertRoundTrip(polygon);
  }

  /**
   * Overrides the base random-shape factory to include JTS polygons
   * approximately 25 % of the time; the remaining calls delegate to the
   * parent so the full shape variety from {@link BinaryCodecTest} is retained.
   */
  @Override
  protected Shape randomShape() {
    boolean produceJtsPolygon = (randomInt(JTS_SHAPE_FREQUENCY) == 0);
    if (produceJtsPolygon) {
      JtsSpatialContext jtsCtx = (JtsSpatialContext) super.ctx;
      return jtsCtx.makeShape(randomGeometry(randomIntBetween(3, 20)), false, false);
    } else {
      return super.randomShape();
    }
  }

  /**
   * Builds a circle polygon approximated by {@code points} vertices, centered
   * at the origin with a diameter equal to the full geographic range (180°).
   * A circle is chosen because {@link GeometricShapeFactory} produces it as a
   * closed, valid polygon — a convenient arbitrary geometry for round-trip tests.
   */
  Geometry randomGeometry(int points) {
    JtsSpatialContext jtsCtx = (JtsSpatialContext) super.ctx;
    GeometricShapeFactory gsf = new GeometricShapeFactory(jtsCtx.getGeometryFactory());
    gsf.setCentre(new Coordinate(0, 0));
    gsf.setSize(CIRCLE_DIAMETER_DEGREES);
    gsf.setNumPoints(points);
    return gsf.createCircle();
  }

}
