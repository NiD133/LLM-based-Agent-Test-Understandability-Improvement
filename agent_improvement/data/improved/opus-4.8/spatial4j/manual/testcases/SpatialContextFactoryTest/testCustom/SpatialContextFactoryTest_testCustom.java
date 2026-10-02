package org.locationtech.spatial4j.context;

import org.locationtech.spatial4j.distance.CartesianDistCalc;
import org.locationtech.spatial4j.distance.GeodesicSphereDistCalc;
import org.locationtech.spatial4j.shape.impl.RectangleImpl;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Verifies that {@link SpatialContextFactory#makeSpatialContext(Map, ClassLoader)}
 * honours the custom configuration keys ("geo", "distCalculator", "worldBounds")
 * passed in as String name-value pairs.
 */
public class SpatialContextFactoryTest_testCustom {

    /** System property the factory consults to pick a factory class; cleared after each test. */
    public static final String PROP = "SpatialContextFactory";

    @After
    public void tearDown() {
        System.getProperties().remove(PROP);
    }

    /**
     * Builds a {@link SpatialContext} from a flat list of alternating key/value
     * strings, e.g. {@code makeContext("geo", "false", "distCalculator", "cartesian")}.
     */
    private SpatialContext makeContext(String... keyValuePairs) {
        Map<String, String> args = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            String key = keyValuePairs[i];
            String value = keyValuePairs[i + 1];
            args.put(key, value);
        }
        return SpatialContextFactory.makeSpatialContext(args, getClass().getClassLoader());
    }

    @Test
    public void testCustom() {
        // geo=false alone => non-geo context with a default cartesian distance calculator.
        SpatialContext nonGeoCtx = makeContext("geo", "false");
        assertFalse(nonGeoCtx.isGeo());
        assertEquals(new CartesianDistCalc(), nonGeoCtx.getDistCalc());

        // Custom squared-cartesian calculator plus explicit world bounds.
        // worldBounds format is ENVELOPE(xMin, xMax, yMax, yMin) = ENVELOPE(-100, 75, 200, 0).
        SpatialContext boundedCtx = makeContext(
                "geo", "false",
                "distCalculator", "cartesian^2",
                "worldBounds", "ENVELOPE(-100, 75, 200, 0)");
        assertEquals(new CartesianDistCalc(true), boundedCtx.getDistCalc());
        // RectangleImpl(minX, maxX, minY, maxY) = (-100, 75, 0, 200).
        assertEquals(new RectangleImpl(-100, 75, 0, 200, boundedCtx), boundedCtx.getWorldBounds());

        // geo=true with the law-of-cosines geodesic calculator.
        SpatialContext geoCtx = makeContext("geo", "true", "distCalculator", "lawOfCosines");
        assertTrue(geoCtx.isGeo());
        assertEquals(new GeodesicSphereDistCalc.LawOfCosines(), geoCtx.getDistCalc());
    }
}
