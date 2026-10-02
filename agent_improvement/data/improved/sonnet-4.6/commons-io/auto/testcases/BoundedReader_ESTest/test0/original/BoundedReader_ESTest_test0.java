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
public class BoundedReader_ESTest_test0 extends BoundedReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        char[] charArray0 = new char[14];
        StringReader stringReader0 = new StringReader("KnLjCdWGnB@(}p3qC");
        BoundedReader boundedReader0 = new BoundedReader(stringReader0, 1);
        int int0 = boundedReader0.read(charArray0, 1, 1);
        assertEquals(1, int0);
    }
}
