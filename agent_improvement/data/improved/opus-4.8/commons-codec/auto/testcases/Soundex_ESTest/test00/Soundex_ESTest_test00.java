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

    /**
     * Encoding with the US English Soundex strips non-letters and ignores the
     * silent letters H and W, so "vyHWF);{" reduces to the consonants V and F.
     * The result keeps the leading letter (upper-cased) followed by the digit
     * codes, padded with zeros to the fixed four-character Soundex length.
     */
    @Test(timeout = 4000)
    public void encodeIgnoresNonLettersAndSilentHW() throws Throwable {
        Soundex usEnglishSoundex = Soundex.US_ENGLISH;

        String soundexCode = usEnglishSoundex.encode("vyHWF);{");

        assertEquals("V100", soundexCode);
    }
}
