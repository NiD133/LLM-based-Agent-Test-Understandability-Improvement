package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.PipedOutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileOutputStream;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;
import org.locationtech.spatial4j.shape.impl.PointImpl;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test8 extends JtsBinaryCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test8() throws Throwable {
        JtsSpatialContextFactory spatialContextFactory = new JtsSpatialContextFactory();
        PrecisionModel.Type floatingSinglePrecision = PrecisionModel.FLOATING_SINGLE;
        PrecisionModel precisionModel = new PrecisionModel(floatingSinglePrecision);
        spatialContextFactory.precisionModel = precisionModel;

        JtsSpatialContext spatialContext = spatialContextFactory.newSpatialContext();
        JtsBinaryCodec codec = new JtsBinaryCodec(spatialContext, spatialContextFactory);

        try {
            codec.writeDim((DataOutput) null, 0);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.locationtech.spatial4j.io.jts.JtsBinaryCodec", e);
        }
    }
}
