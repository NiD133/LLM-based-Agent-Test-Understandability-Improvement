package org.locationtech.spatial4j.context;

import org.junit.After;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertFalse;

/**
 * Verifies the system-property fallback in
 * {@link SpatialContextFactory#makeSpatialContext(Map, ClassLoader)}.
 * <p>
 * When the args map does not contain a "spatialContextFactory" entry, the factory should
 * fall back to the {@code SpatialContextFactory} Java system property to find the factory
 * class to instantiate.
 */
public class SpatialContextFactoryTest_testSystemPropertyLookup {

    /** The Java system property consulted by the factory when no factory arg is supplied. */
    private static final String FACTORY_SYSTEM_PROPERTY = "SpatialContextFactory";

    @After
    public void clearFactorySystemProperty() {
        System.getProperties().remove(FACTORY_SYSTEM_PROPERTY);
    }

    @Test
    public void testSystemPropertyLookup() {
        // Point the system property at a custom factory that always builds a non-geo context.
        System.setProperty(FACTORY_SYSTEM_PROPERTY, NonGeoSpatialContextFactory.class.getName());

        SpatialContext context = makeSpatialContextWithoutFactoryArg();

        // The context is non-geo only if our custom factory was resolved via the system property.
        assertFalse(context.isGeo());
    }

    /**
     * Builds a {@link SpatialContext} from an empty args map, forcing the factory class to be
     * resolved from the system property rather than from a "spatialContextFactory" argument.
     */
    private SpatialContext makeSpatialContextWithoutFactoryArg() {
        Map<String, String> noArgs = Collections.emptyMap();
        return SpatialContextFactory.makeSpatialContext(noArgs, getClass().getClassLoader());
    }

    /** Custom factory whose contexts are always non-geo, used to prove the lookup happened. */
    public static class NonGeoSpatialContextFactory extends SpatialContextFactory {
        @Override
        public SpatialContext newSpatialContext() {
            geo = false;
            return new SpatialContext(this);
        }
    }
}
