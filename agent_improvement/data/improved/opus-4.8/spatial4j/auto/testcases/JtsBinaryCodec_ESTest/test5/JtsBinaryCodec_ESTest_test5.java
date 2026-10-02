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

    /**
     * A null shape is not handled by the superclass codec, so JtsBinaryCodec
     * falls back to its generic geometry type (TYPE_GEOM == 5).
     */
    @Test(timeout = 4000)
    public void typeForNullShapeFallsBackToGeometryType() throws Throwable {
        JtsSpatialContext geoContext = JtsSpatialContext.GEO;
        JtsSpatialContextFactory contextFactory = new JtsSpatialContextFactory();
        JtsBinaryCodec codec = new JtsBinaryCodec(geoContext, contextFactory);

        byte shapeType = codec.typeForShape((Shape) null);

        assertEquals((byte) 5, shapeType);
    }
}
