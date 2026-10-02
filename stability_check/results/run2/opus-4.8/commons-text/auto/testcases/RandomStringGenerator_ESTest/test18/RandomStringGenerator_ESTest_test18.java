package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test18 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Passing a {@code null} predicate array to {@link RandomStringGenerator.Builder#filteredBy}
     * is allowed (it reverts to the default "allow any character" behavior) and the method
     * still returns the same builder for chaining. The default minimum code point stays at zero.
     */
    @Test(timeout = 4000)
    public void filteredByNullPredicatesKeepsDefaultMinimumCodePoint() throws Throwable {
        RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();

        builder.filteredBy((CharacterPredicate[]) null);

        assertEquals(0, RandomStringGenerator.Builder.DEFAULT_MINIMUM_CODE_POINT);
    }
}
