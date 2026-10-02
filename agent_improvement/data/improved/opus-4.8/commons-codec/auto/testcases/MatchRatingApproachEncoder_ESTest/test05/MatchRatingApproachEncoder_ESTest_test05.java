package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test05 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Encodes a name made up entirely of accented letters (the full set of
     * characters the encoder knows how to de-accent). Every character is first
     * mapped to its plain ASCII equivalent, after which the standard Match
     * Rating Approach steps (drop vowels, collapse double consonants, keep the
     * first and last three letters) reduce the word to its six-letter code.
     */
    @Test(timeout = 4000)
    public void encodingAllAccentedLettersStripsAccentsAndReturnsCode() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String allAccentedLetters =
                "ÀàÈèÌìÒòÙù" +       // grave
                "ÁáÉéÍíÓóÚúÝý" + // acute
                "ÂâÊêÎîÔôÛûŶŷÃãÕõÑñ" + // circumflex + tilde
                "ÄäËëÏïÖöÜüŸÿÅåÇçŐőŰű"; // umlaut, ring, cedilla, double acute

        String code = encoder.encode(allAccentedLetters);

        assertEquals("AYYNYC", code);
    }
}
