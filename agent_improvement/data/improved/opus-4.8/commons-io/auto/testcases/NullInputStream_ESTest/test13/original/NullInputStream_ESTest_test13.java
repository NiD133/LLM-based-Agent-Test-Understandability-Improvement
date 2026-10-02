package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.EOFException;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test13 extends NullInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        NullInputStream nullInputStream0 = new NullInputStream(2480L);
        int int0 = nullInputStream0.available();
        assertEquals(2480, int0);
        assertTrue(nullInputStream0.markSupported());
    }
}
