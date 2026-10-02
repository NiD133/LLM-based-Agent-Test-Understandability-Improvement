package org.locationtech.spatial4j.context;

import static org.evosuite.runtime.EvoAssertions.verifyException;
import static org.junit.Assert.fail;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.impl.ShapeFactoryImpl;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test01 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * A JTS factory expects a JTS-aware {@link org.locationtech.spatial4j.shape.ShapeFactory}.
     * Forcing the plain {@link ShapeFactoryImpl} as the shape factory class makes
     * {@code newSpatialContext()} fail while reflectively instantiating the shape factory,
     * and the underlying error is rethrown as a {@link RuntimeException} from
     * {@code SpatialContextFactory}.
     */
    @Test(timeout = 4000)
    public void newSpatialContextWithIncompatibleShapeFactoryThrows() throws Throwable {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.shapeFactoryClass = ShapeFactoryImpl.class;

        try {
            factory.newSpatialContext();
            fail("Expected a RuntimeException because ShapeFactoryImpl is not compatible with the JTS factory");
        } catch (RuntimeException expected) {
            // Thrown by SpatialContextFactory while building the spatial context.
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", expected);
        }
    }
}
