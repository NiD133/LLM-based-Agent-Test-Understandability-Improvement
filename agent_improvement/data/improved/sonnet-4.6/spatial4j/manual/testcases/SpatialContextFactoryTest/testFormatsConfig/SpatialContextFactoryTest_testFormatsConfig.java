package org.locationtech.spatial4j.context;

import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.io.ShapeIO;
import org.locationtech.spatial4j.io.WKTReader;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Tests that custom reader classes specified via the "readers" configuration key
 * are registered and returned by the context's format registry.
 */
public class SpatialContextFactoryTest_testFormatsConfig {

    private static final String SYSTEM_PROPERTY_KEY = "SpatialContextFactory";

    @After
    public void tearDown() {
        System.getProperties().remove(SYSTEM_PROPERTY_KEY);
    }

    /**
     * Verifies that when a custom WKT reader class is specified via the "readers" arg,
     * the created context returns an instance of that reader for the WKT format.
     */
    @Test
    public void testFormatsConfig() {
        // Arrange: configure a JTS spatial context with a custom WKT reader
        Map<String, String> args = new HashMap<>();
        args.put("spatialContextFactory", JtsSpatialContextFactory.class.getName());
        args.put("readers", CustomWktShapeParser.class.getName());

        // Act: build the context from the given configuration
        JtsSpatialContext ctx = (JtsSpatialContext)
                SpatialContextFactory.makeSpatialContext(args, getClass().getClassLoader());

        // Assert: the WKT format slot is backed by our custom reader implementation
        assertTrue(ctx.getFormats().getReader(ShapeIO.WKT) instanceof CustomWktShapeParser);
    }

    /** Custom WKT reader used to verify that the "readers" config key is honored. */
    public static class CustomWktShapeParser extends WKTReader {
        public CustomWktShapeParser(JtsSpatialContext ctx, JtsSpatialContextFactory factory) {
            super(ctx, factory);
        }
    }
}
