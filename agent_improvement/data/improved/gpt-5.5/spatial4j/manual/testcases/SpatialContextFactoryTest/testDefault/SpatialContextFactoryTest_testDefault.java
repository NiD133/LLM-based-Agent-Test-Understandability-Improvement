package org.locationtech.spatial4j.context;

import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SpatialContextFactoryTest_testDefault {

    private static final String PROP = "SpatialContextFactory";

    @After
    public void tearDown() {
        System.getProperties().remove(PROP);
    }

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
    public void testDefault() {
        SpatialContext expectedContext = SpatialContext.GEO;

        SpatialContext actualContext = call();

        assertEquals(expectedContext.getClass(), actualContext.getClass());
        assertEquals(expectedContext.isGeo(), actualContext.isGeo());
        assertEquals(expectedContext.getDistCalc(), actualContext.getDistCalc());
        assertEquals(expectedContext.getWorldBounds(), actualContext.getWorldBounds());
    }
}
