package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test18 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Two names made up only of punctuation symbols are not considered
     * homophonous, so {@link MatchRatingApproachEncoder#isEncodeEquals} returns false.
     */
    @Test(timeout = 4000)
    public void isEncodeEqualsReturnsFalseForPunctuationOnlyNames() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        boolean namesAreHomophonous = encoder.isEncodeEquals("-}_", "Jj~(");

        assertFalse(namesAreHomophonous);
    }
}
