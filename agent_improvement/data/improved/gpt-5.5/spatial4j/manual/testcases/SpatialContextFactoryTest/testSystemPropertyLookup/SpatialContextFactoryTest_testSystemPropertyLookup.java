package org.locationtech.spatial4j.context;

import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class SpatialContextFactoryTest_testSystemPropertyLookup {

    private static final String FACTORY_PROPERTY = "SpatialContextFactory";

    @After
    public void tearDown() {
        System.getProperties().remove(FACTORY_PROPERTY);
    }

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
    public void testSystemPropertyLookup() {
        System.setProperty(FACTORY_PROPERTY, DSCF.class.getName());

        assertTrue(!makeContext().isGeo());
    }
}

class DSCF extends SpatialContextFactory {

    public DSCF() {
        geo = false;
    }
}
