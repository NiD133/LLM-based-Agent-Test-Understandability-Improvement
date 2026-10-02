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
import static org.junit.Assert.assertTrue;

public class SpatialContextFactoryTest {
  public static final String PROP = "SpatialContextFactory";

  private static final String SPATIAL_CONTEXT_FACTORY = "spatialContextFactory";
  private static final String GEO = "geo";
  private static final String DIST_CALCULATOR = "distCalculator";
  private static final String WORLD_BOUNDS = "worldBounds";
  private static final String NORM_WRAP_LONGITUDE = "normWrapLongitude";
  private static final String PRECISION_SCALE = "precisionScale";
  private static final String WKT_SHAPE_PARSER_CLASS = "wktShapeParserClass";
  private static final String DATELINE_RULE = "datelineRule";
  private static final String VALIDATION_RULE = "validationRule";
  private static final String AUTO_INDEX = "autoIndex";
  private static final String READERS = "readers";

  @After
  public void tearDown() {
    System.getProperties().remove(PROP);
  }

  private SpatialContext makeContext(String... configPairs) {
    return SpatialContextFactory.makeSpatialContext(
        configMap(configPairs), getClass().getClassLoader());
  }

  private Map<String, String> configMap(String... configPairs) {
    Map<String, String> config = new HashMap<>();
    for (int i = 0; i < configPairs.length; i += 2) {
      String key = configPairs[i];
      String value = configPairs[i + 1];
      config.put(key, value);
    }
    return config;
  }

  @Test
  public void testDefault() {
    SpatialContext expectedContext = SpatialContext.GEO;
    SpatialContext actualContext = makeContext();

    assertEquals(expectedContext.getClass(), actualContext.getClass());
    assertEquals(expectedContext.isGeo(), actualContext.isGeo());
    assertEquals(expectedContext.getDistCalc(), actualContext.getDistCalc());
    assertEquals(expectedContext.getWorldBounds(), actualContext.getWorldBounds());
  }

  @Test
  public void testCustom() {
    SpatialContext ctx = makeContext(GEO, "false");
    assertTrue(!ctx.isGeo());
    assertEquals(new CartesianDistCalc(), ctx.getDistCalc());

    ctx = makeContext(
        GEO, "false",
        DIST_CALCULATOR, "cartesian^2",
        WORLD_BOUNDS, "ENVELOPE(-100, 75, 200, 0)");
    assertEquals(new CartesianDistCalc(true), ctx.getDistCalc());
    assertEquals(new RectangleImpl(-100, 75, 0, 200, ctx), ctx.getWorldBounds());

    ctx = makeContext(
        GEO, "true",
        DIST_CALCULATOR, "lawOfCosines");
    assertTrue(ctx.isGeo());
    assertEquals(new GeodesicSphereDistCalc.LawOfCosines(), ctx.getDistCalc());
  }

  @Test
  public void testJtsContextFactory() {
    JtsSpatialContext ctx = (JtsSpatialContext) makeContext(
        SPATIAL_CONTEXT_FACTORY, JtsSpatialContextFactory.class.getName(),
        GEO, "true",
        NORM_WRAP_LONGITUDE, "true",
        PRECISION_SCALE, "2.0",
        WKT_SHAPE_PARSER_CLASS, CustomWktShapeParser.class.getName(),
        DATELINE_RULE, "ccwRect",
        VALIDATION_RULE, "repairConvexHull",
        AUTO_INDEX, "true");

    assertTrue(ctx.isNormWrapLongitude());
    assertEquals(2.0, ctx.getGeometryFactory().getPrecisionModel().getScale(), 0.0);
    assertTrue(CustomWktShapeParser.once);
    assertEquals(DatelineRule.ccwRect, ctx.getDatelineRule());
    assertEquals(ValidationRule.repairConvexHull, ctx.getValidationRule());

    ctx = (JtsSpatialContext) makeContext(
        SPATIAL_CONTEXT_FACTORY, JtsSpatialContextFactory.class.getName(),
        GEO, "false",
        WORLD_BOUNDS, "ENVELOPE(-500,500,300,-300)",
        NORM_WRAP_LONGITUDE, "true",
        PRECISION_SCALE, "2.0",
        WKT_SHAPE_PARSER_CLASS, CustomWktShapeParser.class.getName(),
        DATELINE_RULE, "ccwRect",
        VALIDATION_RULE, "repairConvexHull",
        AUTO_INDEX, "true");
    assertEquals(300, ctx.getWorldBounds().getMaxY(), 0.0);
  }

  @Test
  public void testFormatsConfig() {
    JtsSpatialContext ctx = (JtsSpatialContext) makeContext(
        SPATIAL_CONTEXT_FACTORY, JtsSpatialContextFactory.class.getName(),
        READERS, CustomWktShapeParser.class.getName());

    assertTrue(ctx.getFormats().getReader(ShapeIO.WKT) instanceof CustomWktShapeParser);
  }

  @Test
  public void testSystemPropertyLookup() {
    System.setProperty(PROP, DSCF.class.getName());
    assertTrue(!makeContext().isGeo());
  }

  public static class DSCF extends SpatialContextFactory {

    @Override
    public SpatialContext newSpatialContext() {
      geo = false;
      return new SpatialContext(this);
    }
  }

  public static class CustomWktShapeParser extends WKTReader {
    static boolean once = false;

    public CustomWktShapeParser(JtsSpatialContext ctx, JtsSpatialContextFactory factory) {
      super(ctx, factory);
      once = true;
    }
  }
}
