package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test06 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Verifies that a name containing punctuation and lowercase letters is considered
     * phonetically equivalent to a name with a double consonant.
     *
     * ",-l" is cleaned to "L" (punctuation stripped, uppercased).
     * "RR" is reduced to "R" (double consonant collapsed to single).
     * Both encode to single-letter consonants whose combined length (2) yields a
     * minimum rating of 5; the similarity comparison also scores 5, so they match.
     */
    @Test(timeout = 4000)
    public void test06_punctuatedNameMatchesDoubleConsonantName() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        // ",-l" cleans to "L"; "RR" reduces to "R" — both score at the minimum rating threshold
        boolean namesArePhoneticallySimilar = encoder.isEncodeEquals(",-l", "RR");

        assertTrue(namesArePhoneticallySimilar);
    }
}
