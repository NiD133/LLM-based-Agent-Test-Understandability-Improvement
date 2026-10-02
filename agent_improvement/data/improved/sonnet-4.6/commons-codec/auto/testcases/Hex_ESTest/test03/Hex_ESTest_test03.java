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
public class Hex_ESTest_test03 extends Hex_ESTest_scaffolding {

    /**
     * Hex.encode(Object) only accepts byte[], String, or ByteBuffer.
     * Passing a plain Object triggers a ClassCastException internally,
     * which is re-thrown as an EncoderException from Hex.
     */
    @Test(timeout = 4000)
    public void test03_encodeThrowsExceptionForUnsupportedObjectType() throws Throwable {
        Hex hex = new Hex();
        Object unsupportedInput = new Object();
        try {
            hex.encode(unsupportedInput);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // java.lang.Object cannot be cast to [B
            //
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
