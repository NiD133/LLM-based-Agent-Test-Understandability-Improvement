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

    private static final String INPUT_WITH_TRAILING_NON_LETTERS = "vyHWF);{\u007F";
    private static final String EXPECTED_US_ENGLISH_SOUNDEX = "V100";

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Soundex usEnglishSoundex = Soundex.US_ENGLISH;

        String encodedSoundex = usEnglishSoundex.encode(INPUT_WITH_TRAILING_NON_LETTERS);

        assertEquals(EXPECTED_US_ENGLISH_SOUNDEX, encodedSoundex);
    }
}
