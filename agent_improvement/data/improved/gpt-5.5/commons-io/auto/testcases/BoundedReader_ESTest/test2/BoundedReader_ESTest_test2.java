package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.PipedReader;
import java.io.StringReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedReader_ESTest_test2 extends BoundedReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        PipedReader emptyReader = new PipedReader();
        BoundedReader readerWithNegativeLimit = new BoundedReader(emptyReader, (-1));
        char[] targetBuffer = new char[14];

        int charsRead = readerWithNegativeLimit.read(targetBuffer, 1, 1);

        assertEquals((-1), charsRead);
    }
}
