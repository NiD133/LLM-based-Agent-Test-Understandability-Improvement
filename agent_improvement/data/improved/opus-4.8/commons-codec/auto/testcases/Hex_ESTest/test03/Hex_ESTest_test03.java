package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test03 extends Hex_ESTest_scaffolding {

    /**
     * Hex.encode(Object) only accepts a String, ByteBuffer, or byte[]. When given a plain
     * Object it cannot be cast to byte[], so the resulting ClassCastException is wrapped and
     * rethrown as an EncoderException from the Hex class.
     */
    @Test(timeout = 4000)
    public void encodeRejectsNonByteArrayObject() throws Throwable {
        Hex hex = new Hex();
        Object unsupportedInput = new Object();

        try {
            hex.encode(unsupportedInput);
            fail("Expected an exception because java.lang.Object cannot be cast to byte[]");
        } catch (Exception e) {
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
