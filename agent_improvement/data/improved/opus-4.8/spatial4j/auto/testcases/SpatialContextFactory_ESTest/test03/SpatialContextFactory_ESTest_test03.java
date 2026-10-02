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
public class SpatialContextFactory_ESTest_test03 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * initField(name) reflectively looks up a public field whose name equals {@code name}.
     * When no such field exists, the underlying NoSuchFieldException is rethrown wrapped
     * in a java.lang.Error. This test passes a name that does not match any field and
     * verifies that the wrapping Error is thrown from SpatialContextFactory.
     */
    @Test(timeout = 4000)
    public void initFieldWithUnknownFieldNameThrowsError() throws Throwable {
        SpatialContextFactory factory = new SpatialContextFactory();
        String unknownFieldName = "BhhavP[EGM<a";

        try {
            factory.initField(unknownFieldName);
            fail("Expected an Error because no field is named '" + unknownFieldName + "'");
        } catch (Error expected) {
            // initField wraps the java.lang.NoSuchFieldException in a java.lang.Error.
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", expected);
        }
    }
}
