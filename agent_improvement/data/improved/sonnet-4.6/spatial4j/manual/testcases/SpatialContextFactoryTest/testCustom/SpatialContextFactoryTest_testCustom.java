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
 * Tests that {@link SpatialContextFactory#makeSpatialContext} correctly honours
 * the configuration arguments passed to it.
 */
public class SpatialContextFactoryTest_testCustom {

    /** System-property key used to override the factory class. */
    public static final String PROP = "SpatialContextFactory";

    @After
    public void tearDown() {
        System.getProperties().remove(PROP);
    }

    /**
     * Builds a {@link SpatialContext} from an even-length list of key/value pairs
     * and the current class-loader.
     */
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
    public void testCustom() {
        // Scenario 1: non-geographic context uses a plain Cartesian distance calculator.
        SpatialContext ctx = call("geo", "false");
        assertFalse("Context should be non-geographic", ctx.isGeo());
        assertEquals(new CartesianDistCalc(), ctx.getDistCalc());

        // Scenario 2: non-geographic context with squared Cartesian distances and custom world bounds.
        // ENVELOPE argument order: xMin, xMax, yMax, yMin
        ctx = call(
                "geo",            "false",
                "distCalculator", "cartesian^2",
                "worldBounds",    "ENVELOPE(-100, 75, 200, 0)");
        assertEquals(new CartesianDistCalc(true), ctx.getDistCalc());
        assertEquals(new RectangleImpl(-100, 75, 0, 200, ctx), ctx.getWorldBounds());

        // Scenario 3: geographic context uses the Law-of-Cosines spherical distance formula.
        ctx = call("geo", "true", "distCalculator", "lawOfCosines");
        assertTrue("Context should be geographic", ctx.isGeo());
        assertEquals(new GeodesicSphereDistCalc.LawOfCosines(), ctx.getDistCalc());
    }
}
