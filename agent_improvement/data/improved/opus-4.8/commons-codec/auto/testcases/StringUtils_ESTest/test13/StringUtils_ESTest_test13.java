package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test13 extends StringUtils_ESTest_scaffolding {

    /**
     * Encoding an empty string as UTF-16 should yield an empty byte array
     * (no bytes are produced, not even a byte-order mark, for empty input).
     */
    @Test(timeout = 4000)
    public void testGetBytesUtf16WithEmptyStringReturnsEmptyArray() throws Throwable {
        byte[] encodedBytes = StringUtils.getBytesUtf16("");

        assertEquals(0, encodedBytes.length);
    }
}
