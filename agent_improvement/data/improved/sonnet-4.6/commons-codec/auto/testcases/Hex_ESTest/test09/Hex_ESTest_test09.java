package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test09 extends Hex_ESTest_scaffolding {

    /**
     * Passing a null ByteBuffer to encodeHexString(ByteBuffer, boolean) must throw NullPointerException,
     * because the method dereferences the buffer without a null guard.
     */
    @Test(timeout = 4000)
    public void test_encodeHexString_nullByteBuffer_throwsNullPointerException() throws Throwable {
        try {
            Hex.encodeHexString((ByteBuffer) null, true);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
