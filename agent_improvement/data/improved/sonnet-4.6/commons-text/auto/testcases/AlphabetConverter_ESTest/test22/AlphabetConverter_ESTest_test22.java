package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test22 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * Verifies that a converter built from empty alphabets defaults to an encoded
     * character length of 1, and that toString() completes without error.
     */
    @Test(timeout = 4000)
    public void test_emptyAlphabets_defaultEncodedCharLengthIsOne() throws Throwable {
        Integer[] emptyAlphabet = new Integer[0];

        AlphabetConverter converter = AlphabetConverter.createConverter(
                emptyAlphabet,  // original alphabet (empty)
                emptyAlphabet,  // encoding alphabet (empty)
                emptyAlphabet   // do-not-encode set (empty)
        );

        // Should not throw even with an empty mapping
        converter.toString();

        // When no encoding characters exist the length defaults to 1
        assertEquals(1, converter.getEncodedCharLength());
    }
}
