package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test22 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    // The MRA encoder treats a bare space as a trivial/empty input and returns "".
    @Test(timeout = 4000)
    public void test_encode_singleSpace_returnsEmpty() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        String encoded = encoder.encode(" ");
        assertEquals("", encoded);
    }
}
