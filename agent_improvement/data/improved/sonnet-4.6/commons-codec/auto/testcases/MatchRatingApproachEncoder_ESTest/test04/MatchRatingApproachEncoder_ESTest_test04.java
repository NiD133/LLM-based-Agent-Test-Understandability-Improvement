package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test04 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Verifies that encoding a long dot-separated fully-qualified class name produces the correct
     * MRA phonetic code. The encoder strips vowels (keeping the leading 'O'), removes double
     * consonants, then takes the first 3 and last 3 characters of the result, yielding "ORGPTN".
     */
    @Test(timeout = 4000)
    public void test_encodeLongQualifiedClassName_returnsFirst3AndLast3PhoneticCode() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String encodedResult = encoder.encode("org.apache.commons.codec.EncoderException");

        assertEquals("ORGPTN", encodedResult);
    }
}
