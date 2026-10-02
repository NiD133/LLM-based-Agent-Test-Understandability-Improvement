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
public class SpatialContextFactory_ESTest_test01 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that assigning a plain (non-JTS) ShapeFactoryImpl to a JtsSpatialContextFactory
     * and then calling newSpatialContext() throws a RuntimeException.
     *
     * JtsSpatialContextFactory expects a JTS-aware ShapeFactory whose constructor accepts
     * (SpatialContext, SpatialContextFactory). ShapeFactoryImpl lacks such a constructor,
     * so reflective instantiation inside SpatialContextFactory fails and is wrapped in
     * a RuntimeException.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Arrange: create a JTS factory and override shapeFactoryClass with a non-JTS implementation
        JtsSpatialContextFactory jtsFactory = new JtsSpatialContextFactory();
        jtsFactory.shapeFactoryClass = ShapeFactoryImpl.class;

        // Act & Assert: building the context must fail because ShapeFactoryImpl is incompatible
        // with JtsSpatialContextFactory (it does not satisfy the required JTS constructor contract)
        try {
            jtsFactory.newSpatialContext();
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // The exception is expected to originate from SpatialContextFactory's
            // reflective class-instantiation logic
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
