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

public class SpatialContextFactoryTest_testJtsContextFactory {

    public static final String PROP = "SpatialContextFactory";

    @After
    public void tearDown() {
        System.getProperties().remove(PROP);
    }

    private SpatialContext call(String... argsStr) {
        Map<String, String> args = new HashMap<>();
        for (int i = 0; i < argsStr.length; i += 2) {
            String key = argsStr[i];
            String val = argsStr[i + 1];
            args.put(key, val);
        }
        return SpatialContextFactory.makeSpatialContext(args, getClass().getClassLoader());
    }

    @Test
    public void testJtsContextFactory() {
        // Build a JTS spatial context with geo=true and various JTS-specific settings
        JtsSpatialContext ctx = (JtsSpatialContext) call(
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
        assertTrue(CustomWktShapeParser.once); // cheap way to test it was created
        assertEquals(DatelineRule.ccwRect, ctx.getDatelineRule());
        assertEquals(ValidationRule.repairConvexHull, ctx.getValidationRule());

        // Ensure geo=false with worldBounds works -- fixes #72
        ctx = (JtsSpatialContext) call(
                "spatialContextFactory", JtsSpatialContextFactory.class.getName(),
                "geo", "false", // set to false
                "worldBounds", "ENVELOPE(-500,500,300,-300)",
                "normWrapLongitude", "true",
                "precisionScale", "2.0",
                "wktShapeParserClass", CustomWktShapeParser.class.getName(),
                "datelineRule", "ccwRect",
                "validationRule", "repairConvexHull",
                "autoIndex", "true");

        assertEquals(300, ctx.getWorldBounds().getMaxY(), 0.0);
    }

    public static class CustomWktShapeParser extends WKTReader {
        static boolean once = false; // cheap way to test it was created

        public CustomWktShapeParser(JtsSpatialContext ctx, JtsSpatialContextFactory factory) {
            super(ctx, factory);
            once = true;
        }
    }
}
