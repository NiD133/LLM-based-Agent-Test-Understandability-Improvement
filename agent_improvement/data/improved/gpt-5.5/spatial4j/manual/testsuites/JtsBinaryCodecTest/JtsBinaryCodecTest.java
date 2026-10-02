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

public class JtsBinaryCodecTest extends BinaryCodecTest {

  private static final int MIN_CIRCLE_POINTS = 3;
  private static final int MAX_CIRCLE_POINTS = 20;
  private static final int RANDOM_JTS_SHAPE_CHANCE = 3;
  private static final Coordinate CIRCLE_CENTER = new Coordinate(0, 0);
  private static final int CIRCLE_DIAMETER = 180;

  @Override
  public SpatialContext initContext() {
    JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
    factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
    return factory.newSpatialContext();
  }

  @Test
  public void testPoly() throws Exception {
    JtsGeometry shape = makeRandomJtsGeometryShape();
    assertRoundTrip(shape);
  }

  @Override
  protected Shape randomShape() {
    if (randomInt(RANDOM_JTS_SHAPE_CHANCE) == 0) {
      return makeRandomJtsGeometryShape();
    }
    return super.randomShape();
  }

  private JtsGeometry makeRandomJtsGeometryShape() {
    JtsSpatialContext jtsContext = (JtsSpatialContext) super.ctx;
    return jtsContext.makeShape(randomGeometry(randomCirclePointCount()), false, false);
  }

  private int randomCirclePointCount() {
    return randomIntBetween(MIN_CIRCLE_POINTS, MAX_CIRCLE_POINTS);
  }

  Geometry randomGeometry(int points) {
    JtsSpatialContext jtsContext = (JtsSpatialContext) super.ctx;
    GeometricShapeFactory shapeFactory = new GeometricShapeFactory(jtsContext.getGeometryFactory());
    shapeFactory.setCentre(CIRCLE_CENTER);
    shapeFactory.setSize(CIRCLE_DIAMETER);
    shapeFactory.setNumPoints(points);
    return shapeFactory.createCircle();
  }
}
