package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test01 extends Hex_ESTest_scaffolding {

    /**
     * {@link Hex#decode(Object)} only accepts a String, byte[], ByteBuffer, or char[].
     * When handed a plain Object it falls through to a {@code (char[]) object} cast,
     * which fails with a ClassCastException that Hex re-throws (wrapped in a
     * DecoderException). This test confirms that such an unsupported input is rejected.
     */
    @Test(timeout = 4000)
    public void decodeRejectsUnsupportedObjectType() throws Throwable {
        Hex hex = new Hex();
        Object unsupportedInput = new Object();

        try {
            hex.decode(unsupportedInput);
            fail("Expected an exception: java.lang.Object cannot be cast to char[]");
        } catch (Exception e) {
            // The exception must originate from the Hex class.
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
