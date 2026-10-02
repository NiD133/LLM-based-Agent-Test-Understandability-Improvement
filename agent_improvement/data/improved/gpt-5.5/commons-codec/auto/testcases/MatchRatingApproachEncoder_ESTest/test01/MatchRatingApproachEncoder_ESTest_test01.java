package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test01 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        String identicalEncodedName = "u96p[In]=-vK={K5";

        int similarityScore = encoder.leftToRightThenRightToLeftProcessing(identicalEncodedName, identicalEncodedName);

        assertEquals(6, similarityScore);
    }
}
