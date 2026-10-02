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
public class NullInputStream_ESTest_test10 extends NullInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        NullInputStream nullInputStream0 = new NullInputStream((-30L));
        byte[] byteArray0 = new byte[0];
        int int0 = nullInputStream0.read(byteArray0);
        assertTrue(nullInputStream0.markSupported());
        assertEquals(0, int0);
        assertEquals(0, nullInputStream0.available());
    }
}
