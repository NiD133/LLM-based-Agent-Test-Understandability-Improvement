package org.locationtech.spatial4j.context;

import org.junit.After;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Verifies that {@link SpatialContextFactory#makeSpatialContext(Map, ClassLoader)},
 * when given no configuration, builds a context equivalent to the built-in
 * {@link SpatialContext#GEO} default.
 */
public class SpatialContextFactoryTest_testDefault {

    /**
     * System property the factory consults to pick a custom factory class.
     * Cleared after each test so a leftover value cannot leak between tests.
     */
    private static final String FACTORY_SYSTEM_PROPERTY = "SpatialContextFactory";

    @After
    public void clearFactorySystemProperty() {
        System.getProperties().remove(FACTORY_SYSTEM_PROPERTY);
    }

    /**
     * Builds a SpatialContext from the given configuration, using this test's
     * class loader (needed only when a config value names a class to load).
     */
    private SpatialContext makeContext(Map<String, String> config) {
        return SpatialContextFactory.makeSpatialContext(config, getClass().getClassLoader());
    }

    @Test
    public void testDefault() {
        SpatialContext expectedDefault = SpatialContext.GEO;

        // No configuration supplied -> the factory should fall back to all defaults.
        SpatialContext actual = makeContext(Collections.<String, String>emptyMap());

        assertEquals(expectedDefault.getClass(), actual.getClass());
        assertEquals(expectedDefault.isGeo(), actual.isGeo());
        assertEquals(expectedDefault.getDistCalc(), actual.getDistCalc());
        assertEquals(expectedDefault.getWorldBounds(), actual.getWorldBounds());
    }
}
