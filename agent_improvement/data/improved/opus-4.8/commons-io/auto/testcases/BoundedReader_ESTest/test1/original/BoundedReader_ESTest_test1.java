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
public class BoundedReader_ESTest_test1 extends BoundedReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        StringReader stringReader0 = new StringReader("");
        BoundedReader boundedReader0 = new BoundedReader(stringReader0, 511);
        boundedReader0.reset();
        boundedReader0.skip(511);
        boundedReader0.mark(511);
        int int0 = boundedReader0.read();
        assertEquals((-1), int0);
    }
}
