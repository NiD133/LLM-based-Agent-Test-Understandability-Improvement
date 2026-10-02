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
public class JtsBinaryCodec_ESTest_test1 extends JtsBinaryCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        JtsSpatialContextFactory jtsSpatialContextFactory0 = new JtsSpatialContextFactory();
        JtsSpatialContext jtsSpatialContext0 = new JtsSpatialContext(jtsSpatialContextFactory0);
        JtsBinaryCodec jtsBinaryCodec0 = new JtsBinaryCodec(jtsSpatialContext0, jtsSpatialContextFactory0);
        PipedOutputStream pipedOutputStream0 = new PipedOutputStream();
        DataOutputStream dataOutputStream0 = new DataOutputStream(pipedOutputStream0);
        boolean boolean0 = jtsBinaryCodec0.writeShapeByTypeIfSupported(dataOutputStream0, (Shape) null, (byte) 0);
        assertFalse(boolean0);
    }
}
