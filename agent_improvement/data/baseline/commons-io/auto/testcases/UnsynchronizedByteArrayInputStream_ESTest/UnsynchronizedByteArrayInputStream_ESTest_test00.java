package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test00 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        UnsynchronizedByteArrayInputStream.Builder unsynchronizedByteArrayInputStream_Builder0 = UnsynchronizedByteArrayInputStream.builder();
        byte[] byteArray0 = new byte[5];
        unsynchronizedByteArrayInputStream_Builder0.setByteArray(byteArray0);
        UnsynchronizedByteArrayInputStream unsynchronizedByteArrayInputStream0 = unsynchronizedByteArrayInputStream_Builder0.get();
        assertEquals(5, unsynchronizedByteArrayInputStream0.available());
    }
}
