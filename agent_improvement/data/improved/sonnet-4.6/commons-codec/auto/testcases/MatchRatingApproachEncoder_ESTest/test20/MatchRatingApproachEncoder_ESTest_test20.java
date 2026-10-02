package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test20 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    // Encoding a string that consists solely of punctuation characters removed during
    // name cleaning (comma and hyphen) should produce an empty string, because after
    // stripping those characters no letters remain to encode.
    @Test(timeout = 4000)
    public void test20_encodePunctuationOnlyInput_returnsEmptyString() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        String encodedResult = encoder.encode(",-");
        assertEquals("", encodedResult);
    }
}
