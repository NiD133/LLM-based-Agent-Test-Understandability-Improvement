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

    private static final String TEXT_WITH_BASE58_EXCLUDED_CHARACTERS = "Invalid character in Base58 string: 0x%02x";

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Base58 base58 = new Base58();

        boolean isInBase58Alphabet = base58.isInAlphabet(TEXT_WITH_BASE58_EXCLUDED_CHARACTERS);

        assertFalse(isInBase58Alphabet);
    }
}
