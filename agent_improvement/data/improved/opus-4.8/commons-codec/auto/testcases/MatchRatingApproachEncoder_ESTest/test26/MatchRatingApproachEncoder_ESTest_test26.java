package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test26 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Encoding a short lower-case word keeps the leading vowel and produces an
     * upper-cased MRA code: "ec" -> "EC".
     */
    @Test(timeout = 4000)
    public void encodeShortWordPreservesLeadingVowelAndUpperCases() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String encoded = encoder.encode("ec");

        assertEquals("EC", encoded);
    }
}
