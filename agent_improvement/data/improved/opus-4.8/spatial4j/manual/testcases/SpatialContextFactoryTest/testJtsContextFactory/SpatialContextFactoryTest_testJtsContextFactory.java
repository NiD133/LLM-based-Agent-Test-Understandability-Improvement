package org.locationtech.spatial4j.context;

import org.locationtech.spatial4j.context.SpatialContextFactoryTest.CustomWktShapeParser;
import org.locationtech.spatial4j.context.jts.DatelineRule;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.context.jts.ValidationRule;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Verifies that {@link SpatialContextFactory#makeSpatialContext} honours the
 * JTS-specific configuration keys when the configured factory is a
 * {@link JtsSpatialContextFactory}.
 */
public class SpatialContextFactoryTest_testJtsContextFactory {

    /** System property {@link SpatialContextFactory} may read for the factory class name. */
    public static final String PROP = "SpatialContextFactory";

    /** Delta for asserting exact (non-approximate) double equality. */
    private static final double EXACT = 0.0;

    @After
    public void tearDown() {
        System.getProperties().remove(PROP);
    }

    /** Builds a {@link SpatialContext} from the given name-value configuration pairs. */
    private SpatialContext makeContext(Map<String, String> config) {
        return SpatialContextFactory.makeSpatialContext(config, getClass().getClassLoader());
    }

    @Test
    public void testJtsContextFactory() {
        // --- Scenario 1: geographic context configured with custom JTS options ---
        Map<String, String> geoConfig = new HashMap<>();
        geoConfig.put("spatialContextFactory", JtsSpatialContextFactory.class.getName());
        geoConfig.put("geo", "true");
        geoConfig.put("normWrapLongitude", "true");
        geoConfig.put("precisionScale", "2.0");
        geoConfig.put("wktShapeParserClass", CustomWktShapeParser.class.getName());
        geoConfig.put("datelineRule", "ccwRect");
        geoConfig.put("validationRule", "repairConvexHull");
        geoConfig.put("autoIndex", "true");

        JtsSpatialContext ctx = (JtsSpatialContext) makeContext(geoConfig);

        assertTrue(ctx.isNormWrapLongitude());
        assertEquals(2.0, ctx.getGeometryFactory().getPrecisionModel().getScale(), EXACT);
        // CustomWktShapeParser flips this flag in its constructor, so a true value is a
        // cheap proof that the configured WKT parser was actually instantiated.
        assertTrue(CustomWktShapeParser.once);
        assertEquals(DatelineRule.ccwRect, ctx.getDatelineRule());
        assertEquals(ValidationRule.repairConvexHull, ctx.getValidationRule());

        // --- Scenario 2: non-geographic context with explicit world bounds (regression for #72) ---
        Map<String, String> nonGeoConfig = new HashMap<>();
        nonGeoConfig.put("spatialContextFactory", JtsSpatialContextFactory.class.getName());
        nonGeoConfig.put("geo", "false");
        nonGeoConfig.put("worldBounds", "ENVELOPE(-500,500,300,-300)");
        nonGeoConfig.put("normWrapLongitude", "true");
        nonGeoConfig.put("precisionScale", "2.0");
        nonGeoConfig.put("wktShapeParserClass", CustomWktShapeParser.class.getName());
        nonGeoConfig.put("datelineRule", "ccwRect");
        nonGeoConfig.put("validationRule", "repairConvexHull");
        nonGeoConfig.put("autoIndex", "true");

        ctx = (JtsSpatialContext) makeContext(nonGeoConfig);

        // ENVELOPE(xMin, xMax, yMax, yMin) -> the configured maximum latitude is 300.
        assertEquals(300, ctx.getWorldBounds().getMaxY(), EXACT);
    }
}
