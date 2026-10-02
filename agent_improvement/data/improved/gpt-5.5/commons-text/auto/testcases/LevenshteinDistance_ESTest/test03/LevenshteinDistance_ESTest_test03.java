package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDistance_ESTest_test03 extends LevenshteinDistance_ESTest_scaffolding {

    private static final CharSequence EMPTY_LEFT_SEQUENCE = "";
    private static final CharSequence EMPTY_RIGHT_SEQUENCE = "";
    private static final int EXPECTED_DISTANCE_BETWEEN_EMPTY_SEQUENCES = 0;

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        LevenshteinDistance defaultDistance = LevenshteinDistance.getDefaultInstance();

        Integer actualDistance = defaultDistance.apply(EMPTY_LEFT_SEQUENCE, EMPTY_RIGHT_SEQUENCE);

        assertEquals(EXPECTED_DISTANCE_BETWEEN_EMPTY_SEQUENCES, (int) actualDistance);
    }
}
