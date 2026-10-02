package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test21 extends StringUtils_ESTest_scaffolding {

    /**
     * Verifies that encoding an ASCII string with {@link StringUtils#getBytesUsAscii(String)}
     * yields one byte per character, since each US-ASCII character maps to a single byte.
     */
    @Test(timeout = 4000)
    public void getBytesUsAscii_withAsciiString_returnsOneBytePerCharacter() throws Throwable {
        String asciiInput = "[NQ.38g~9u=YGOxnW";

        byte[] encodedBytes = StringUtils.getBytesUsAscii(asciiInput);

        assertEquals(asciiInput.length(), encodedBytes.length);
        assertEquals(17, encodedBytes.length);
    }
}
