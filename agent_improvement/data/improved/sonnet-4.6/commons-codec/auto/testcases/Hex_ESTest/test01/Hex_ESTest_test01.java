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
public class Hex_ESTest_test01 extends Hex_ESTest_scaffolding {

    /**
     * Verifies that Hex.decode(Object) throws a DecoderException when the supplied
     * object cannot be cast to char[], String, byte[], or ByteBuffer.
     * Internally, Hex tries to cast the object to char[] and the resulting
     * ClassCastException is wrapped and rethrown as a DecoderException.
     */
    @Test(timeout = 4000)
    public void test_decodeObject_withUnsupportedType_throwsException() throws Throwable {
        Hex hex = new Hex();
        Object unsupportedInput = new Object();
        try {
            hex.decode(unsupportedInput);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // java.lang.Object cannot be cast to [C
            //
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
