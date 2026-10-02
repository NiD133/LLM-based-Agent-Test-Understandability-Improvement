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
public class StringUtils_ESTest_test18 extends StringUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        byte[] utf16EncodedTwoNullCharacters = new byte[4];

        String decodedString = StringUtils.newStringUtf16(utf16EncodedTwoNullCharacters);

        assertEquals("\u0000\u0000", decodedString);
    }
}
