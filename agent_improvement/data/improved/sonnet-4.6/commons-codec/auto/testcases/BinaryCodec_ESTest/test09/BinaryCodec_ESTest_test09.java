package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test09 extends BinaryCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_fromAsciiChars_nullInput_returnsEmptyByteArray() throws Throwable {
        // Passing null to fromAscii(char[]) should return an empty byte array rather than throwing.
        byte[] result = BinaryCodec.fromAscii((char[]) null);

        byte[] expected = new byte[] {};
        assertArrayEquals(expected, result);
    }
}
