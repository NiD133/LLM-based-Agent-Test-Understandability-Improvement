/*******************************************************************************
 * Copyright (c) 2015 Voyager Search and MITRE
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0 which
 * accompanies this distribution and is available at
 *    http://www.apache.org/licenses/LICENSE-2.0.txt
 ******************************************************************************/

package org.locationtech.spatial4j.context;

import org.locationtech.spatial4j.context.jts.DatelineRule;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.context.jts.ValidationRule;
import org.locationtech.spatial4j.distance.CartesianDistCalc;
import org.locationtech.spatial4j.distance.GeodesicSphereDistCalc;
import org.locationtech.spatial4j.io.ShapeIO;
import org.locationtech.spatial4j.io.WKTReader;
import org.locationtech.spatial4j.shape.impl.RectangleImpl;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link SpatialContextFactory#makeSpatialContext(Map, ClassLoader)}, which builds a
 * {@link SpatialContext} from a map of String name/value configuration pairs.
 */
public class SpatialContextFactoryTest {

  /**
   * The system property that {@code makeSpatialContext} consults to discover the factory class
   * when the "spatialContextFactory" argument is absent.
   */
  public static final String PROP = "SpatialContextFactory";

  @After
  public void tearDown() {
    // Remove the system property so factory-lookup tests don't leak into one another.
    System.getProperties().remove(PROP);
  }

  /**
   * Builds a {@link SpatialContext} from a flat list of alternating config keys and values.
   * For example {@code makeContext("geo", "false")} is turned into the single-entry map
   * {@code {geo=false}} before being passed to the factory.
   *
   * @param keyValuePairs alternating key, value, key, value, ... configuration entries
   */
  private SpatialContext makeContext(String... keyValuePairs) {
    Map<String, String> args = new HashMap<>();
    for (int i = 0; i < keyValuePairs.length; i += 2) {
      String key = keyValuePairs[i];
      String value = keyValuePairs[i + 1];
      args.put(key, value);
    }
    return SpatialContextFactory.makeSpatialContext(args, getClass().getClassLoader());
  }

  @Test
  public void testDefault() {
    // With no arguments, the factory should reproduce the built-in geodetic context (SpatialContext.GEO).
    SpatialContext expected = SpatialContext.GEO;
    SpatialContext actual = makeContext();

    assertEquals(expected.getClass(), actual.getClass());
    assertEquals(expected.isGeo(), actual.isGeo());
    assertEquals(expected.getDistCalc(), actual.getDistCalc());
    assertEquals(expected.getWorldBounds(), actual.getWorldBounds());
  }

  @Test
  public void testCustom() {
    // geo=false alone gives a non-geodetic context with the default Cartesian distance calculator.
    SpatialContext ctx = makeContext("geo", "false");
    assertFalse(ctx.isGeo());
    assertEquals(new CartesianDistCalc(), ctx.getDistCalc());

    // A squared Cartesian calculator plus explicit world bounds.
    // The ENVELOPE argument order is (xMin, xMax, yMax, yMin).
    ctx = makeContext(
        "geo", "false",
        "distCalculator", "cartesian^2",
        "worldBounds", "ENVELOPE(-100, 75, 200, 0)");
    assertEquals(new CartesianDistCalc(true), ctx.getDistCalc());
    // RectangleImpl takes (minX, maxX, minY, maxY): -100..75 in X, 0..200 in Y.
    assertEquals(new RectangleImpl(-100, 75, 0, 200, ctx), ctx.getWorldBounds());

    // geo=true with a named geodetic calculator.
    ctx = makeContext(
        "geo", "true",
        "distCalculator", "lawOfCosines");
    assertTrue(ctx.isGeo());
    assertEquals(new GeodesicSphereDistCalc.LawOfCosines(), ctx.getDistCalc());
  }

  @Test
  public void testJtsContextFactory() {
    // Build a JTS-backed context and confirm each configured option is applied.
    JtsSpatialContext ctx = (JtsSpatialContext) makeContext(
        "spatialContextFactory", JtsSpatialContextFactory.class.getName(),
        "geo", "true",
        "normWrapLongitude", "true",
        "precisionScale", "2.0",
        "wktShapeParserClass", CustomWktShapeParser.class.getName(),
        "datelineRule", "ccwRect",
        "validationRule", "repairConvexHull",
        "autoIndex", "true");
    assertTrue(ctx.isNormWrapLongitude());
    assertEquals(2.0, ctx.getGeometryFactory().getPrecisionModel().getScale(), 0.0);
    // CustomWktShapeParser flips a static flag in its constructor, so this proves it was instantiated.
    assertTrue(CustomWktShapeParser.once);
    assertEquals(DatelineRule.ccwRect, ctx.getDatelineRule());
    assertEquals(ValidationRule.repairConvexHull, ctx.getValidationRule());

    // Regression for issue #72: geo=false combined with explicit worldBounds must work.
    ctx = (JtsSpatialContext) makeContext(
        "spatialContextFactory", JtsSpatialContextFactory.class.getName(),
        "geo", "false",
        "worldBounds", "ENVELOPE(-500,500,300,-300)",
        "normWrapLongitude", "true",
        "precisionScale", "2.0",
        "wktShapeParserClass", CustomWktShapeParser.class.getName(),
        "datelineRule", "ccwRect",
        "validationRule", "repairConvexHull",
        "autoIndex", "true");
    // ENVELOPE(xMin, xMax, yMax, yMin) -> the maximum Y is 300.
    assertEquals(300, ctx.getWorldBounds().getMaxY(), 0.0);
  }

  @Test
  public void testFormatsConfig() {
    // A custom reader registered via "readers" should be returned as the WKT reader.
    JtsSpatialContext ctx = (JtsSpatialContext) makeContext(
        "spatialContextFactory", JtsSpatialContextFactory.class.getName(),
        "readers", CustomWktShapeParser.class.getName());

    assertTrue(ctx.getFormats().getReader(ShapeIO.WKT) instanceof CustomWktShapeParser);
  }

  @Test
  public void testSystemPropertyLookup() {
    // When the factory class is only given via the system property, it must still be picked up.
    System.setProperty(PROP, DSCF.class.getName());
    assertFalse(makeContext().isGeo());// DSCF always builds a non-geo context
  }

  /** A factory that always produces a non-geodetic context, used by {@link #testSystemPropertyLookup()}. */
  public static class DSCF extends SpatialContextFactory {

    @Override
    public SpatialContext newSpatialContext() {
      geo = false;
      return new SpatialContext(this);
    }
  }

  /** A WKT reader that records, via a static flag, whether it was ever instantiated. */
  public static class CustomWktShapeParser extends WKTReader {
    static boolean once = false;// cheap way to test it was created
    public CustomWktShapeParser(JtsSpatialContext ctx, JtsSpatialContextFactory factory) {
      super(ctx, factory);
      once = true;
    }
  }
}
