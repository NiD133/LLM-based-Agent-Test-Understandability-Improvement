package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test5 extends JtsBinaryCodec_ESTest_scaffolding {

    // TYPE_GEOM = 5 is returned as a fallback when the superclass cannot identify the shape type
    private static final byte TYPE_GEOM = (byte) 5;

    @Test(timeout = 4000)
    public void typeForShape_withNullShape_returnsTypeGeom() throws Throwable {
        JtsSpatialContext jtsSpatialContext0 = JtsSpatialContext.GEO;
        JtsSpatialContextFactory jtsSpatialContextFactory0 = new JtsSpatialContextFactory();
        JtsBinaryCodec jtsBinaryCodec0 = new JtsBinaryCodec(jtsSpatialContext0, jtsSpatialContextFactory0);

        byte shapeType = jtsBinaryCodec0.typeForShape((Shape) null);

        assertEquals(TYPE_GEOM, shapeType);
    }
}
