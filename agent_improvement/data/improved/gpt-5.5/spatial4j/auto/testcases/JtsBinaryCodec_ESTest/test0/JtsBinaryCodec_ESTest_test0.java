package org.locationtech.spatial4j.io.jts;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import java.io.ObjectOutputStream;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.impl.PointImpl;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test0 extends JtsBinaryCodec_ESTest_scaffolding {

    private static final String MOCK_STREAM_NAME = "Expected initial read of one byte, not: ";
    private static final double POINT_LONGITUDE = 10.0;
    private static final double POINT_LATITUDE = 3877.7633;
    private static final double LONGITUDE_TOLERANCE = 0.01;

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        JtsSpatialContextFactory contextFactory = new JtsSpatialContextFactory();
        JtsSpatialContext spatialContext = contextFactory.newSpatialContext();
        JtsBinaryCodec codec = new JtsBinaryCodec(spatialContext, contextFactory);

        MockPrintStream printStream = new MockPrintStream(MOCK_STREAM_NAME);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(printStream);
        PointImpl point = new PointImpl(POINT_LONGITUDE, POINT_LATITUDE, spatialContext);

        codec.writePoint(objectOutputStream, point);

        assertEquals(POINT_LONGITUDE, point.getLon(), LONGITUDE_TOLERANCE);
    }
}
