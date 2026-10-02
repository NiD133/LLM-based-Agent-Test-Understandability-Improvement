package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test00 extends StringUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link StringUtils#newString(byte[], String)} returns {@code null}
     * when the input byte array is {@code null}, regardless of the charset name.
     */
    @Test(timeout = 4000)
    public void newString_withNullBytes_returnsNull() throws Throwable {
        byte[] nullBytes = null;
        String nullCharsetName = null;

        String decoded = StringUtils.newString(nullBytes, nullCharsetName);

        assertNull(decoded);
    }
}
