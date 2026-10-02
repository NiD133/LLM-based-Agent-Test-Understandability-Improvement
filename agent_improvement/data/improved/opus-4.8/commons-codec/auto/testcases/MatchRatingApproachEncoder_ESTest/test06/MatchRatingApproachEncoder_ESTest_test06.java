package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test06 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Verifies that two names which both reduce to a single-letter MRA code are
     * still treated as a match.
     *
     * <p>After cleaning and encoding:</p>
     * <ul>
     *   <li>",-l" -> punctuation stripped and upper-cased -> "L"</li>
     *   <li>"RR"  -> double consonant collapsed -> "R"</li>
     * </ul>
     *
     * <p>Both codes have length 1, so their length difference is 0 and the
     * combined length (2) yields the maximum minimum-rating of 5. The
     * left-to-right/right-to-left comparison leaves both single letters intact,
     * producing a similarity count of 5, which meets the required rating.
     * Hence {@code isEncodeEquals} returns {@code true}.</p>
     */
    @Test(timeout = 4000)
    public void encodesEqualWhenBothNamesReduceToShortCodes() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        boolean namesMatch = encoder.isEncodeEquals(",-l", "RR");

        assertTrue(namesMatch);
    }
}
