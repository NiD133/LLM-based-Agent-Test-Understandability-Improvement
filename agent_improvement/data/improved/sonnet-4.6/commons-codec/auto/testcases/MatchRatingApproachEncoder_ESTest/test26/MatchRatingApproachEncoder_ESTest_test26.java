package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test26 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    // "ec" encodes to "EC": the input is uppercased, the leading vowel 'E' is preserved,
    // and 'C' (a consonant) is retained, yielding "EC".
    @Test(timeout = 4000)
    public void test_encodeShortWordWithLeadingVowel_preservesLeadingVowelAndConsonant() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        String encoded = encoder.encode("ec");
        assertEquals("EC", encoded);
    }
}
