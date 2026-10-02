package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test00 extends Soundex_ESTest_scaffolding {

    // Soundex strips non-alpha characters, ignores H/W after the first letter,
    // and does not encode vowels — so "vyHWF);{" reduces to "VYHWF",
    // yielding code V (first letter) + 1 (F) + 00 (padding) = "V100".
    @Test(timeout = 4000)
    public void test_encode_stringWithSpecialCharsHWAndVowel_returnsExpectedSoundexCode() throws Throwable {
        Soundex soundex = Soundex.US_ENGLISH;
        String soundexCode = soundex.encode("vyHWF);{");
        assertEquals("V100", soundexCode);
    }
}
