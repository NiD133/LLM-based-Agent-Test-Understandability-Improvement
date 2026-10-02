package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test07 extends BinaryCodec_ESTest_scaffolding {

    // toAsciiString treats a null input like an empty byte array and returns an empty string
    @Test(timeout = 4000)
    public void test_toAsciiString_withNullInput_returnsEmptyString() throws Throwable {
        String asciiString = BinaryCodec.toAsciiString((byte[]) null);
        assertEquals("", asciiString);
    }
}
