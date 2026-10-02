package org.locationtech.spatial4j.context;

import org.junit.After;
import org.junit.Test;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.io.ShapeIO;
import org.locationtech.spatial4j.io.WKTReader;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class SpatialContextFactoryTest_testFormatsConfig {

    private static final String SPATIAL_CONTEXT_FACTORY_PROPERTY = "SpatialContextFactory";
    private static final String FACTORY_ARGUMENT = "spatialContextFactory";
    private static final String READERS_ARGUMENT = "readers";

    @After
    public void tearDown() {
        System.getProperties().remove(SPATIAL_CONTEXT_FACTORY_PROPERTY);
    }

    private SpatialContext createSpatialContext(String... alternatingKeysAndValues) {
        Map<String, String> arguments = new HashMap<>();
        for (int i = 0; i < alternatingKeysAndValues.length; i += 2) {
            String key = alternatingKeysAndValues[i];
            String value = alternatingKeysAndValues[i + 1];
            arguments.put(key, value);
        }
        return SpatialContextFactory.makeSpatialContext(arguments, getClass().getClassLoader());
    }

    @Test
    public void testFormatsConfig() {
        JtsSpatialContext context = (JtsSpatialContext) createSpatialContext(
                FACTORY_ARGUMENT, JtsSpatialContextFactory.class.getName(),
                READERS_ARGUMENT, CustomWktShapeParser.class.getName());

        assertTrue(context.getFormats().getReader(ShapeIO.WKT) instanceof CustomWktShapeParser);
    }
}

class CustomWktShapeParser extends WKTReader {

    public CustomWktShapeParser(SpatialContext context, SpatialContextFactory factory) {
        super(context, factory);
    }
}
