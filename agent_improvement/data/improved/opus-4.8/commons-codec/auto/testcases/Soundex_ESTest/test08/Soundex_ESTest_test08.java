package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test08 extends Soundex_ESTest_scaffolding {

    /**
     * The difference between a string and itself should be 0 when the string
     * contains only digits: SoundexUtils.clean strips all non-letters, leaving
     * an empty encoding for both inputs, so no encoded characters match.
     */
    @Test(timeout = 4000)
    public void differenceOfDigitOnlyStringWithItselfIsZero() throws Throwable {
        Soundex soundex = Soundex.US_ENGLISH;
        String digitsOnly = "01230120022455012623010202";

        int difference = soundex.difference(digitsOnly, digitsOnly);

        assertEquals(0, difference);
    }
}
