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
 * Verifies that a custom shape reader supplied through the "readers" configuration
 * argument is honoured by {@link SpatialContextFactory#makeSpatialContext}.
 */
public class SpatialContextFactoryTest_testFormatsConfig {

    /** System property that {@link SpatialContextFactory} consults as a fallback factory name. */
    private static final String FACTORY_SYSTEM_PROPERTY = "SpatialContextFactory";

    @After
    public void clearFactorySystemProperty() {
        System.getProperties().remove(FACTORY_SYSTEM_PROPERTY);
    }

    /**
     * Builds a {@link SpatialContext} from the given {@code key, value, key, value, ...}
     * configuration pairs, mirroring how callers pass a name-value map to the factory.
     */
    private SpatialContext makeContext(String... configPairs) {
        Map<String, String> config = new HashMap<>();
        for (int i = 0; i < configPairs.length; i += 2) {
            config.put(configPairs[i], configPairs[i + 1]);
        }
        return SpatialContextFactory.makeSpatialContext(config, getClass().getClassLoader());
    }

    @Test
    public void testFormatsConfig() {
        // Configure a JTS context whose WKT reader is overridden by our custom parser.
        JtsSpatialContext ctx = (JtsSpatialContext) makeContext(
                "spatialContextFactory", JtsSpatialContextFactory.class.getName(),
                "readers", CustomWktShapeParser.class.getName());

        assertTrue(ctx.getFormats().getReader(ShapeIO.WKT) instanceof CustomWktShapeParser);
    }

    /** Custom WKT reader used to confirm the "readers" argument replaces the default WKT reader. */
    public static class CustomWktShapeParser extends WKTReader {
        public CustomWktShapeParser(JtsSpatialContext ctx, JtsSpatialContextFactory factory) {
            super(ctx, factory);
        }
    }
}
