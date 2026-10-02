package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test19 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Calling {@link RandomStringGenerator.Builder#filteredBy} twice with the same
     * (single-element) predicate array should be supported and return the same builder
     * for fluent chaining. The builder's DEFAULT_LENGTH constant remains 0.
     */
    @Test(timeout = 4000)
    public void filteredByCanBeCalledRepeatedly() throws Throwable {
        RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();
        CharacterPredicate[] predicates = new CharacterPredicate[1];

        builder.filteredBy(predicates);
        RandomStringGenerator.Builder returnedBuilder = builder.filteredBy(predicates);

        assertEquals(0, RandomStringGenerator.Builder.DEFAULT_LENGTH);
    }
}
