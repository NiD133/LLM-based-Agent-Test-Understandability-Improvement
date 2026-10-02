package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test17 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        // Original alphabet: unique chars are 'Z' and '=' (duplicates are ignored by createConverterFromChars)
        Character[] original = new Character[] {
            'Z', 'Z', 'Z', 'Z', '=', 'Z', '=', 'Z'
        };

        // Encoding alphabet: only contains 'Z'
        Character[] encoding = new Character[] {
            'Z', 'Z', 'Z', 'Z', 'Z', 'Z', 'Z', 'Z'
        };

        // Passing encoding as doNotEncode removes all encoding chars from consideration.
        // encodingCopy.size() - doNotEncodeCopy.size() = 1 - 1 = 0, which is below the
        // required minimum of 2, so an IllegalArgumentException must be thrown.
        try {
            AlphabetConverter.createConverterFromChars(original, encoding, encoding);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
