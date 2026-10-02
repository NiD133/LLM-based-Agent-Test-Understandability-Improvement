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
public class JtsBinaryCodec_ESTest_test0 extends JtsBinaryCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        JtsSpatialContextFactory jtsSpatialContextFactory0 = new JtsSpatialContextFactory();
        JtsSpatialContext jtsSpatialContext0 = jtsSpatialContextFactory0.newSpatialContext();
        JtsBinaryCodec jtsBinaryCodec0 = new JtsBinaryCodec(jtsSpatialContext0, jtsSpatialContextFactory0);
        MockPrintStream mockPrintStream0 = new MockPrintStream("Expected initial read of one byte, not: ");
        ObjectOutputStream objectOutputStream0 = new ObjectOutputStream(mockPrintStream0);
        PointImpl pointImpl0 = new PointImpl(10.0, 3877.7633, jtsSpatialContext0);
        jtsBinaryCodec0.writePoint(objectOutputStream0, pointImpl0);
        assertEquals(10.0, pointImpl0.getLon(), 0.01);
    }
}
