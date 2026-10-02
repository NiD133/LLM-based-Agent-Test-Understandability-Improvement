package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test04 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Encoding a long, multi-word string reduces it to the Match Rating Approach
     * code formed from the first three and last three letters of the cleaned,
     * de-voweled name. For "org.apache.commons.codec.EncoderException" the
     * resulting code is "ORGPTN".
     */
    @Test(timeout = 4000)
    public void encodeLongNameReturnsFirstThreeAndLastThreeLetters() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String encoded = encoder.encode("org.apache.commons.codec.EncoderException");

        assertEquals("ORGPTN", encoded);
    }
}
