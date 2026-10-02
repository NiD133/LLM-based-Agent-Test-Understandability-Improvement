package org.locationtech.spatial4j.context;

import org.junit.After;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Tests that {@link SpatialContextFactory#makeSpatialContext} returns a context
 * whose class, geo mode, distance calculator, and world bounds all match those
 * of the canonical {@link SpatialContext#GEO} singleton when no configuration
 * arguments are supplied.
 */
public class SpatialContextFactoryTest_testDefault {

    /** System property key used by {@link SpatialContextFactory} to select a factory class. */
    private static final String SPATIAL_CONTEXT_FACTORY_PROPERTY = "SpatialContextFactory";

    @After
    public void clearSpatialContextFactorySystemProperty() {
        System.getProperties().remove(SPATIAL_CONTEXT_FACTORY_PROPERTY);
    }

    /**
     * Builds a {@link SpatialContext} via {@link SpatialContextFactory#makeSpatialContext}
     * using the supplied key-value pairs as configuration arguments.
     *
     * @param keyValuePairs alternating key, value strings passed to the factory
     * @return the constructed {@link SpatialContext}
     */
    private SpatialContext makeSpatialContext(String... keyValuePairs) {
        Map<String, String> args = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            args.put(keyValuePairs[i], keyValuePairs[i + 1]);
        }
        return SpatialContextFactory.makeSpatialContext(args, getClass().getClassLoader());
    }

    /**
     * Verifies that a factory created with no arguments produces a {@link SpatialContext}
     * that is equivalent to {@link SpatialContext#GEO} in terms of concrete class,
     * geo mode, distance calculator, and world bounds.
     */
    @Test
    public void testDefault() {
        SpatialContext defaultGeoContext = SpatialContext.GEO;

        // No arguments → factory should fall back to all defaults
        SpatialContext factoryDefaultContext = makeSpatialContext();

        assertEquals("Context class should match GEO singleton's class",
                defaultGeoContext.getClass(), factoryDefaultContext.getClass());
        assertEquals("Geo mode should match GEO singleton",
                defaultGeoContext.isGeo(), factoryDefaultContext.isGeo());
        assertEquals("Distance calculator should match GEO singleton's calculator",
                defaultGeoContext.getDistCalc(), factoryDefaultContext.getDistCalc());
        assertEquals("World bounds should match GEO singleton's bounds",
                defaultGeoContext.getWorldBounds(), factoryDefaultContext.getWorldBounds());
    }
}
