package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test20 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Encoding a name made up only of punctuation (a comma and a hyphen) should
     * yield an empty code: cleanName() strips those characters, leaving an empty
     * string, so encode() short-circuits and returns "".
     */
    @Test(timeout = 4000)
    public void encodePunctuationOnlyNameReturnsEmptyString() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String encoded = encoder.encode(",-");

        assertEquals("", encoded);
    }
}
