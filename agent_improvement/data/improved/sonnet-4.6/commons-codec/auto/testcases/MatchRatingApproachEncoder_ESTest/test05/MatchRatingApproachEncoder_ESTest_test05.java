package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test05 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * The complete set of Unicode accented characters recognised by the encoder,
     * mirroring the private UNICODE constant in MatchRatingApproachEncoder.
     * Each group maps to the plain-ASCII letters noted in the inline comments.
     */
    private static final String ALL_ACCENTED_UNICODE_CHARS =
            "ÀàÈèÌìÒòÙù"           // grave:        AaEeIiOoUu
          + "ÁáÉéÍíÓóÚúÝý" // acute:        AaEeIiOoUuYy
          + "ÂâÊêÎîÔôÛûŶŷ" // circumflex:   AaEeIiOoUuYy
          + "ÃãÕõÑñ"                                    // tilde:        AaOoNn
          + "ÄäËëÏïÖöÜüŸÿ" // umlaut:       AaEeIiOoUuYy
          + "Åå"                                                             // ring:         Aa
          + "Çç"                                                             // cedilla:      Cc
          + "ŐőŰű";                                                // double acute: OoUu

    /**
     * Verifies that encoding the full set of Unicode accented characters produces
     * the expected Match Rating Approach (MRA) code "AYYNYC".
     *
     * The encoder strips accents to plain ASCII, uppercases, removes interior
     * vowels, collapses double consonants, then returns first-3 + last-3 letters.
     */
    @Test(timeout = 4000)
    public void test05_encode_allAccentedUnicodeChars_returnsExpectedMRACode() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String encoded = encoder.encode(ALL_ACCENTED_UNICODE_CHARS);

        assertEquals("AYYNYC", encoded);
    }
}
