package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test11 extends Base58_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Base58 codec = new Base58();
        // This string is the error message format used internally when an invalid character
        // is encountered during decoding. It contains characters outside the Base58 alphabet
        // (e.g. space, colon, '0', '%', 'x'), so isInAlphabet must return false.
        String invalidCharErrorFormat = "Invalid character in Base58 string: 0x%02x";
        boolean isValidBase58 = codec.isInAlphabet(invalidCharErrorFormat);
        assertFalse("Error message format string should not be recognised as valid Base58", isValidBase58);
    }
}
