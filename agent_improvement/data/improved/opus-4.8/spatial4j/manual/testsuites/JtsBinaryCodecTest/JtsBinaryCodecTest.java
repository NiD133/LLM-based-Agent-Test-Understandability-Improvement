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
 * Exercises the binary round-trip (write then read) of the JTS-backed codec.
 *
 * <p>This reuses the round-trip assertions defined in {@link BinaryCodecTest} but
 * swaps in a {@link JtsSpatialContext} so that arbitrary JTS geometries (here, polygons
 * approximating a circle) can also be encoded and decoded.
 */
public class JtsBinaryCodecTest extends BinaryCodecTest {

  /** Smallest number of vertices used when generating a random circle-like polygon. */
  private static final int MIN_CIRCLE_POINTS = 3;

  /** Largest number of vertices used when generating a random circle-like polygon. */
  private static final int MAX_CIRCLE_POINTS = 20;

  /** Diameter, in degrees, of the generated circle. */
  private static final int CIRCLE_DIAMETER_DEGREES = 180;

  /**
   * Builds the spatial context for every test in this class: a JTS context configured to
   * store coordinates as single-precision floats (matching the codec's {@code useFloat} path).
   */
  @Override
  public SpatialContext initContext() {
    JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
    factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
    return factory.newSpatialContext();
  }

  /**
   * Verifies that a JTS polygon survives a binary write/read round-trip unchanged.
   */
  @Test
  public void testPoly() throws Exception {
    JtsGeometry polygon = randomCirclePolygonShape();
    assertRoundTrip(polygon);
  }

  /**
   * Supplies the shapes that the inherited round-trip tests operate on. Roughly one time in four
   * a JTS polygon is returned so the JTS-specific encoding path gets exercised; otherwise the
   * superclass's default shapes are used.
   */
  @Override
  protected Shape randomShape() {
    boolean useJtsPolygon = (randomInt(3) == 0);
    if (useJtsPolygon) {
      return randomCirclePolygonShape();
    }
    return super.randomShape();
  }

  /**
   * Creates a JTS shape that is a circle-approximating polygon with a random number of vertices.
   * The {@code false, false} flags disable dateline-crossing and multi-polygon overlap validation.
   */
  private JtsGeometry randomCirclePolygonShape() {
    JtsSpatialContext ctx = (JtsSpatialContext) super.ctx;
    int points = randomIntBetween(MIN_CIRCLE_POINTS, MAX_CIRCLE_POINTS);
    return ctx.makeShape(randomGeometry(points), false, false);
  }

  /**
   * Builds a circle centred at the origin, approximated by the given number of vertices.
   *
   * @param points number of points used to trace the circle's outline
   */
  Geometry randomGeometry(int points) {
    JtsSpatialContext ctx = (JtsSpatialContext) super.ctx;
    GeometricShapeFactory gsf = new GeometricShapeFactory(ctx.getGeometryFactory());
    gsf.setCentre(new Coordinate(0, 0));
    gsf.setSize(CIRCLE_DIAMETER_DEGREES);
    gsf.setNumPoints(points);
    return gsf.createCircle();
  }

}
