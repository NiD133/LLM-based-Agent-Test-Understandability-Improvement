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
public class StringUtils_ESTest_test17 extends StringUtils_ESTest_scaffolding {

    /**
     * Verifies that encoding an ASCII-only string with ISO-8859-1 produces one
     * byte per character, so the resulting array length matches the input length.
     */
    @Test(timeout = 4000)
    public void getBytesIso8859_1_encodesEachCharAsSingleByte() throws Throwable {
        String asciiInput = "lI\"Y50tcK(~T,";

        byte[] encodedBytes = StringUtils.getBytesIso8859_1(asciiInput);

        assertEquals(asciiInput.length(), encodedBytes.length);
        assertEquals(13, encodedBytes.length);
    }
}
