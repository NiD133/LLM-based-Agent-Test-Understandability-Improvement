package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test11 extends Hex_ESTest_scaffolding {

    private static final String INPUT_WITH_NON_HEX_LEADING_CHARACTER = "J{oCkug!H5VpjEa+";

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        try {
            Hex.decodeHex(INPUT_WITH_NON_HEX_LEADING_CHARACTER);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // The first character, 'J' (0x4A), is not a valid hexadecimal digit.
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
