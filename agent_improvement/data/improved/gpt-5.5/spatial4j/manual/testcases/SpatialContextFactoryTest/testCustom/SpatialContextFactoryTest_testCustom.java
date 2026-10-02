package org.locationtech.spatial4j.context;

import org.junit.After;
import org.junit.Test;
import org.locationtech.spatial4j.distance.CartesianDistCalc;
import org.locationtech.spatial4j.distance.GeodesicSphereDistCalc;
import org.locationtech.spatial4j.shape.impl.RectangleImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SpatialContextFactoryTest_testCustom {

    public static final String PROP = "SpatialContextFactory";

    @After
    public void tearDown() {
        System.getProperties().remove(PROP);
    }

    private SpatialContext makeContext(String... keyValuePairs) {
        Map<String, String> args = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            args.put(keyValuePairs[i], keyValuePairs[i + 1]);
        }
        return SpatialContextFactory.makeSpatialContext(args, getClass().getClassLoader());
    }

    @Test
    public void testCustom() {
        SpatialContext cartesianContext = makeContext("geo", "false");
        assertTrue(!cartesianContext.isGeo());
        assertEquals(new CartesianDistCalc(), cartesianContext.getDistCalc());

        SpatialContext squaredCartesianContext = makeContext(
                "geo", "false",
                "distCalculator", "cartesian^2",
                "worldBounds", "ENVELOPE(-100, 75, 200, 0)");
        assertEquals(new CartesianDistCalc(true), squaredCartesianContext.getDistCalc());
        assertEquals(
                new RectangleImpl(-100, 75, 0, 200, squaredCartesianContext),
                squaredCartesianContext.getWorldBounds());

        SpatialContext lawOfCosinesContext = makeContext(
                "geo", "true",
                "distCalculator", "lawOfCosines");
        assertTrue(lawOfCosinesContext.isGeo());
        assertEquals(new GeodesicSphereDistCalc.LawOfCosines(), lawOfCosinesContext.getDistCalc());
    }
}
