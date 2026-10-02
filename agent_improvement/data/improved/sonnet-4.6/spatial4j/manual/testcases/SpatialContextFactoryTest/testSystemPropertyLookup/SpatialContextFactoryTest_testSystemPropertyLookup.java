package org.locationtech.spatial4j.context;

import org.junit.After;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertFalse;

/**
 * Tests that SpatialContextFactory picks up a custom factory class name
 * supplied via the "SpatialContextFactory" JVM system property.
 */
public class SpatialContextFactoryTest_testSystemPropertyLookup {

    /** Name of the system property that overrides the default factory class. */
    private static final String FACTORY_PROPERTY = "SpatialContextFactory";

    /**
     * A minimal factory whose only purpose is to produce a non-geographic
     * (Cartesian) SpatialContext.  The system-property lookup test sets the
     * "SpatialContextFactory" property to this class name so that
     * makeSpatialContext() instantiates it instead of the default factory.
     */
    public static class DSCF extends SpatialContextFactory {
        public DSCF() {
            geo = false; // Cartesian (non-geographic) context
        }
    }

    @After
    public void removeSystemProperty() {
        System.getProperties().remove(FACTORY_PROPERTY);
    }

    /**
     * When the "SpatialContextFactory" system property is set to a custom
     * factory class, makeSpatialContext() must delegate to that factory.
     * DSCF creates a non-geo context, so isGeo() must return false.
     */
    @Test
    public void testSystemPropertyLookup() {
        System.setProperty(FACTORY_PROPERTY, DSCF.class.getName());

        SpatialContext ctx = SpatialContextFactory.makeSpatialContext(
                Collections.<String, String>emptyMap(), getClass().getClassLoader());

        assertFalse("Factory loaded via system property should produce a non-geo context",
                ctx.isGeo());
    }
}
