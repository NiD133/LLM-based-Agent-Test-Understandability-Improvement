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

    private static final String THIRTEEN_CHAR_INPUT = "lI\"Y50tcK(~T,";

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        // ISO-8859-1 is a single-byte encoding, so each character maps to exactly one byte
        byte[] encodedBytes = StringUtils.getBytesIso8859_1(THIRTEEN_CHAR_INPUT);
        assertEquals(13, encodedBytes.length);
    }
}
