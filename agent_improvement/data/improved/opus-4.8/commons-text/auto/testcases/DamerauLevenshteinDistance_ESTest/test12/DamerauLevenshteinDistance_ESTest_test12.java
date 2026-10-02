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
public class DamerauLevenshteinDistance_ESTest_test12 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * The no-argument constructor selects the unlimited variant of the algorithm,
     * which means no threshold is configured. Verifies that {@code getThreshold()}
     * therefore reports {@code null}.
     */
    @Test(timeout = 4000)
    public void defaultConstructorLeavesThresholdUnset() throws Throwable {
        DamerauLevenshteinDistance distanceWithoutThreshold = new DamerauLevenshteinDistance();

        Integer threshold = distanceWithoutThreshold.getThreshold();

        assertNull("Default instance should have no threshold", threshold);
    }
}
