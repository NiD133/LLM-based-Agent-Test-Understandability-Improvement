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
public class StringUtils_ESTest_test21 extends StringUtils_ESTest_scaffolding {

    private static final String ASCII_INPUT = "[NQ.38g~9u=YGOxnW";
    private static final int EXPECTED_ASCII_BYTE_COUNT = 17;

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        byte[] asciiBytes = StringUtils.getBytesUsAscii(ASCII_INPUT);

        assertEquals(EXPECTED_ASCII_BYTE_COUNT, asciiBytes.length);
    }
}
