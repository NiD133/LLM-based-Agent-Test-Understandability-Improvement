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
public class RandomStringGenerator_ESTest_test19 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that filteredBy can be called multiple times on the same builder
     * (each call replaces the previous predicates), and that the default length
     * constant is zero.
     */
    @Test(timeout = 4000)
    public void testFilteredByIsCallableMultipleTimesAndDefaultLengthIsZero() throws Throwable {
        RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();

        // A one-element array whose single entry is null — exercises the null-predicate path
        CharacterPredicate[] predicatesWithNull = new CharacterPredicate[1];

        // First call sets inclusivePredicates
        builder.filteredBy(predicatesWithNull);

        // Second call clears and replaces the previously stored predicates
        RandomStringGenerator.Builder builderAfterSecondFilter = builder.filteredBy(predicatesWithNull);

        // The builder constant for the default generated-string length must be 0
        assertEquals(0, RandomStringGenerator.Builder.DEFAULT_LENGTH);
    }
}
