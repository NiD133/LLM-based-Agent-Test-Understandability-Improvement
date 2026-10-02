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


public class SpatialContextFactoryTest {

  /** System property key that overrides the default SpatialContextFactory class. */
  public static final String PROP = "SpatialContextFactory";

  @After
  public void tearDown() {
    System.getProperties().remove(PROP);
  }

  /**
   * Creates a {@link SpatialContext} by passing alternating key-value string pairs to
   * {@link SpatialContextFactory#makeSpatialContext}.  For example:
   * {@code createContext("geo", "false", "distCalculator", "cartesian")}.
   */
  private SpatialContext createContext(String... keyValuePairs) {
    Map<String, String> args = new HashMap<>();
    for (int i = 0; i < keyValuePairs.length; i += 2) {
      args.put(keyValuePairs[i], keyValuePairs[i + 1]);
    }
    return SpatialContextFactory.makeSpatialContext(args, getClass().getClassLoader());
  }

  /** Verifies that creating a context with no arguments produces the canonical GEO context. */
  @Test
  public void testDefault() {
    SpatialContext geoCtx = SpatialContext.GEO;
    SpatialContext defaultCtx = createContext(); // no arguments → default factory
    assertEquals(geoCtx.getClass(), defaultCtx.getClass());
    assertEquals(geoCtx.isGeo(), defaultCtx.isGeo());
    assertEquals(geoCtx.getDistCalc(), defaultCtx.getDistCalc());
    assertEquals(geoCtx.getWorldBounds(), defaultCtx.getWorldBounds());
  }

  /** Verifies that geo flag, distance calculator, and world bounds can all be customised. */
  @Test
  public void testCustom() {
    // geo=false → non-geographic (Cartesian) context with default Cartesian calculator
    SpatialContext cartesianCtx = createContext("geo", "false");
    assertFalse(cartesianCtx.isGeo());
    assertEquals(new CartesianDistCalc(), cartesianCtx.getDistCalc());

    // geo=false with squared Cartesian calculator and a restricted world bounding box
    SpatialContext cartesianSquaredCtx = createContext(
        "geo", "false",
        "distCalculator", "cartesian^2",
        "worldBounds", "ENVELOPE(-100, 75, 200, 0)"); // xMin, xMax, yMax, yMin
    assertEquals(new CartesianDistCalc(true), cartesianSquaredCtx.getDistCalc());
    assertEquals(new RectangleImpl(-100, 75, 0, 200, cartesianSquaredCtx),
        cartesianSquaredCtx.getWorldBounds());

    // geo=true with law-of-cosines calculator
    SpatialContext lawOfCosinesCtx = createContext(
        "geo", "true",
        "distCalculator", "lawOfCosines");
    assertTrue(lawOfCosinesCtx.isGeo());
    assertEquals(new GeodesicSphereDistCalc.LawOfCosines(), lawOfCosinesCtx.getDistCalc());
  }

  /** Verifies JTS-specific options (precision, dateline rule, validation rule, auto-index). */
  @Test
  public void testJtsContextFactory() {
    JtsSpatialContext jtsGeoCtx = (JtsSpatialContext) createContext(
        "spatialContextFactory", JtsSpatialContextFactory.class.getName(),
        "geo", "true",
        "normWrapLongitude", "true",
        "precisionScale", "2.0",
        "wktShapeParserClass", CustomWktShapeParser.class.getName(),
        "datelineRule", "ccwRect",
        "validationRule", "repairConvexHull",
        "autoIndex", "true");

    assertTrue(jtsGeoCtx.isNormWrapLongitude());
    assertEquals(2.0, jtsGeoCtx.getGeometryFactory().getPrecisionModel().getScale(), 0.0);
    assertTrue(CustomWktShapeParser.wasInstantiated); // confirms the custom parser was wired in
    assertEquals(DatelineRule.ccwRect, jtsGeoCtx.getDatelineRule());
    assertEquals(ValidationRule.repairConvexHull, jtsGeoCtx.getValidationRule());

    // Regression test for issue #72: geo=false combined with a custom worldBounds must not crash
    JtsSpatialContext jtsNonGeoCtx = (JtsSpatialContext) createContext(
        "spatialContextFactory", JtsSpatialContextFactory.class.getName(),
        "geo", "false",
        "worldBounds", "ENVELOPE(-500,500,300,-300)",
        "normWrapLongitude", "true",
        "precisionScale", "2.0",
        "wktShapeParserClass", CustomWktShapeParser.class.getName(),
        "datelineRule", "ccwRect",
        "validationRule", "repairConvexHull",
        "autoIndex", "true");
    assertEquals(300, jtsNonGeoCtx.getWorldBounds().getMaxY(), 0.0);
  }

  /** Verifies that a custom reader class supplied via "readers" is registered for WKT. */
  @Test
  public void testFormatsConfig() {
    JtsSpatialContext ctx = (JtsSpatialContext) createContext(
        "spatialContextFactory", JtsSpatialContextFactory.class.getName(),
        "readers", CustomWktShapeParser.class.getName());

    assertTrue(ctx.getFormats().getReader(ShapeIO.WKT) instanceof CustomWktShapeParser);
  }

  /** Verifies that the {@value PROP} system property selects an alternative factory class. */
  @Test
  public void testSystemPropertyLookup() {
    System.setProperty(PROP, NonGeoContextFactory.class.getName());
    assertFalse(createContext().isGeo()); // NonGeoContextFactory always returns a non-geo context
  }

  // ---------------------------------------------------------------------------
  // Helper classes used by the tests above
  // ---------------------------------------------------------------------------

  /** A minimal factory that always produces a non-geographic context, used to verify
   *  system-property-based factory selection. */
  public static class NonGeoContextFactory extends SpatialContextFactory {
    @Override
    public SpatialContext newSpatialContext() {
      geo = false;
      return new SpatialContext(this);
    }
  }

  /** A WKT reader subclass that records whether it has ever been instantiated.
   *  Used to confirm that factory configuration wires up the custom reader. */
  public static class CustomWktShapeParser extends WKTReader {
    static boolean wasInstantiated = false;

    public CustomWktShapeParser(JtsSpatialContext ctx, JtsSpatialContextFactory factory) {
      super(ctx, factory);
      wasInstantiated = true;
    }
  }
}
