package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.PipedReader;
import java.io.PipedWriter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test00 extends ReaderInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        PipedWriter connectedWriter = new PipedWriter();
        PipedReader connectedReader = new PipedReader(connectedWriter, 2);
        ReaderInputStream stream = new ReaderInputStream(connectedReader);

        stream.close();

        assertTrue(stream.isClosed());
    }
}
