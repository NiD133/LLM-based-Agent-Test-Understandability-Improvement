package org.locationtech.spatial4j.context;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.impl.ShapeFactoryImpl;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test20 extends SpatialContextFactory_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        HashMap<String, String> emptyContextArguments = new HashMap<String, String>();
        SpatialContext defaultSpatialContext = SpatialContextFactory.makeSpatialContext(emptyContextArguments, (ClassLoader) null);
        JtsSpatialContextFactory jtsFactory = new JtsSpatialContextFactory();

        try {
            jtsFactory.makeFormats(defaultSpatialContext);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // The JTS factory cannot create one of its default format writers for this context.
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
