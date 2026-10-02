package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RefinedSoundex_ESTest_test0 extends RefinedSoundex_ESTest_scaffolding {

    /**
     * Encoding an input that contains no letters should yield an empty Soundex
     * code: {@code soundex} strips every non-letter character, leaving nothing
     * to encode. Here the input is made up entirely of digits.
     */
    @Test(timeout = 4000)
    public void encodeDigitsOnlyStringReturnsEmptyCode() throws Throwable {
        RefinedSoundex refinedSoundex = new RefinedSoundex();
        String digitsOnlyInput = "01360240043788015936020505";

        Object encoded = refinedSoundex.US_ENGLISH.encode((Object) digitsOnlyInput);

        assertEquals("", encoded);
    }
}
