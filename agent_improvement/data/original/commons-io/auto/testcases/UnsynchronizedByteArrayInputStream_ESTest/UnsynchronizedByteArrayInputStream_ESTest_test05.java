package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test05 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        byte[] byteArray0 = new byte[8];
        UnsynchronizedByteArrayInputStream unsynchronizedByteArrayInputStream0 = new UnsynchronizedByteArrayInputStream(byteArray0, 358);
        assertEquals(0, unsynchronizedByteArrayInputStream0.available());
        int int0 = unsynchronizedByteArrayInputStream0.read(byteArray0);
        assertEquals((-1), int0);
    }
}
