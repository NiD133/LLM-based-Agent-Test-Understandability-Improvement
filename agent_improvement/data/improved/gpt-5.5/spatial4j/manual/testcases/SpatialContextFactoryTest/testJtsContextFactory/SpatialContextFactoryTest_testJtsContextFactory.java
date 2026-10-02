package org.locationtech.spatial4j.context;

import org.junit.After;
import org.junit.Test;
import org.locationtech.spatial4j.context.jts.DatelineRule;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.context.jts.ValidationRule;
import org.locationtech.spatial4j.io.WKTReader;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SpatialContextFactoryTest_testJtsContextFactory {

    public static final String PROP = "SpatialContextFactory";

    private static final String SPATIAL_CONTEXT_FACTORY = "spatialContextFactory";
    private static final String JTS_FACTORY_CLASS = JtsSpatialContextFactory.class.getName();
    private static final String GEO = "geo";
    private static final String NORM_WRAP_LONGITUDE = "normWrapLongitude";
    private static final String PRECISION_SCALE = "precisionScale";
    private static final String CUSTOM_WKT_SHAPE_PARSER = "wktShapeParserClass";
    private static final String CUSTOM_WKT_SHAPE_PARSER_CLASS = CustomWktShapeParser.class.getName();
    private static final String DATELINE_RULE = "datelineRule";
    private static final String VALIDATION_RULE = "validationRule";
    private static final String AUTO_INDEX = "autoIndex";
    private static final String WORLD_BOUNDS = "worldBounds";

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
        JtsSpatialContext ctx = (JtsSpatialContext) call(
                SPATIAL_CONTEXT_FACTORY, JTS_FACTORY_CLASS,
                GEO, "true",
                NORM_WRAP_LONGITUDE, "true",
                PRECISION_SCALE, "2.0",
                CUSTOM_WKT_SHAPE_PARSER, CUSTOM_WKT_SHAPE_PARSER_CLASS,
                DATELINE_RULE, "ccwRect",
                VALIDATION_RULE, "repairConvexHull",
                AUTO_INDEX, "true");

        assertStandardJtsConfiguration(ctx);
        assertTrue(CustomWktShapeParser.once);

        ctx = (JtsSpatialContext) call(
                SPATIAL_CONTEXT_FACTORY, JTS_FACTORY_CLASS,
                GEO, "false",
                WORLD_BOUNDS, "ENVELOPE(-500,500,300,-300)",
                NORM_WRAP_LONGITUDE, "true",
                PRECISION_SCALE, "2.0",
                CUSTOM_WKT_SHAPE_PARSER, CUSTOM_WKT_SHAPE_PARSER_CLASS,
                DATELINE_RULE, "ccwRect",
                VALIDATION_RULE, "repairConvexHull",
                AUTO_INDEX, "true");

        assertEquals(300, ctx.getWorldBounds().getMaxY(), 0.0);
    }

    private void assertStandardJtsConfiguration(JtsSpatialContext ctx) {
        assertTrue(ctx.isNormWrapLongitude());
        assertEquals(2.0, ctx.getGeometryFactory().getPrecisionModel().getScale(), 0.0);
        assertEquals(DatelineRule.ccwRect, ctx.getDatelineRule());
        assertEquals(ValidationRule.repairConvexHull, ctx.getValidationRule());
    }

    public static class CustomWktShapeParser extends WKTReader {

        static boolean once = false;

        public CustomWktShapeParser(SpatialContext ctx, SpatialContextFactory factory) {
            super(ctx, factory);
            once = true;
        }
    }
}
