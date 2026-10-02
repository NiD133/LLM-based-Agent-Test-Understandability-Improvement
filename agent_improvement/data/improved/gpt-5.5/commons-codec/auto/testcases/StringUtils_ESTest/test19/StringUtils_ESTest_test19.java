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
public class StringUtils_ESTest_test19 extends StringUtils_ESTest_scaffolding {

    private static final int TWO_UTF16_CODE_UNITS_IN_BYTES = 4;
    private static final String TWO_NULL_CHARACTERS = "\u0000\u0000";

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        byte[] zeroFilledUtf16BigEndianBytes = new byte[TWO_UTF16_CODE_UNITS_IN_BYTES];

        String decodedString = StringUtils.newStringUtf16Be(zeroFilledUtf16BigEndianBytes);

        assertEquals(TWO_NULL_CHARACTERS, decodedString);
    }
}
