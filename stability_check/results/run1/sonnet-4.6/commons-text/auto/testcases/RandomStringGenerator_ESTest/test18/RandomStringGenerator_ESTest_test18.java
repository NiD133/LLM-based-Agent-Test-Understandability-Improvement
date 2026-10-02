package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.function.IntUnaryOperator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test18 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that calling filteredBy(null) does not throw and that the default
     * minimum code point constant has the expected value of 0.
     */
    @Test(timeout = 4000)
    public void test_filteredByNull_leavesDefaultMinimumCodePointUnchanged() throws Throwable {
        RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();

        // Passing null to filteredBy should reset predicates without throwing
        builder.filteredBy((CharacterPredicate[]) null);

        assertEquals(0, RandomStringGenerator.Builder.DEFAULT_MINIMUM_CODE_POINT);
    }
}
