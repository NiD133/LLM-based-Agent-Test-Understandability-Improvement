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

    /**
     * Verifies that {@link Base58#isInAlphabet(String)} returns {@code false} when the
     * input contains characters outside the Base58 alphabet. The string used here
     * ("Invalid character in Base58 string: 0x%02x") includes spaces, '0', ':', '%'
     * and other symbols that are not valid Base58 characters.
     */
    @Test(timeout = 4000)
    public void isInAlphabet_withNonBase58Characters_returnsFalse() throws Throwable {
        Base58 base58 = new Base58();

        boolean allCharactersInAlphabet =
                base58.isInAlphabet("Invalid character in Base58 string: 0x%02x");

        assertFalse(allCharactersInAlphabet);
    }
}
