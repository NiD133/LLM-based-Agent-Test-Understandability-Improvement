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

    private static final String ISO_8859_1_INPUT = "lI\"Y50tcK(~T,";
    private static final int EXPECTED_BYTE_COUNT = 13;

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        byte[] encodedBytes = StringUtils.getBytesIso8859_1(ISO_8859_1_INPUT);

        assertEquals(EXPECTED_BYTE_COUNT, encodedBytes.length);
    }
}
