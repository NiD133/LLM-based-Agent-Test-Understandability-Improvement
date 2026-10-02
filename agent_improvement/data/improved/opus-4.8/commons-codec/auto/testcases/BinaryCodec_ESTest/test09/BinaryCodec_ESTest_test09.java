package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test09 extends BinaryCodec_ESTest_scaffolding {

    /**
     * Decoding a null char array should yield an empty byte array,
     * since {@link BinaryCodec#fromAscii(char[])} treats null as "no input".
     */
    @Test(timeout = 4000)
    public void fromAsciiWithNullCharArrayReturnsEmptyByteArray() throws Throwable {
        char[] nullChars = null;

        byte[] decoded = BinaryCodec.fromAscii(nullChars);

        assertArrayEquals(new byte[] {}, decoded);
    }
}
