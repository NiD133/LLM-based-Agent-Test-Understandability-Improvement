package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test07 extends BinaryCodec_ESTest_scaffolding {

    /**
     * Verifies that converting a null byte array to an ASCII string yields an
     * empty string, since a null input has no bits to represent.
     */
    @Test(timeout = 4000)
    public void toAsciiString_withNullInput_returnsEmptyString() throws Throwable {
        String result = BinaryCodec.toAsciiString((byte[]) null);

        assertEquals("", result);
    }
}
