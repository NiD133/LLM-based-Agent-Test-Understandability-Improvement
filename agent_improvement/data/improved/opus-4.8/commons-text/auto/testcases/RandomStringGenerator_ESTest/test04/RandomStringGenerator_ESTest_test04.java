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
public class RandomStringGenerator_ESTest_test04 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Builds a generator that only accepts ASCII alphanumeric characters and
     * letters, then asks it to produce a string whose length is randomly chosen
     * from the very wide range [2913, 1114111]. Exercising {@code generate} with
     * such a large length bound drives the generator's code-point sampling loop.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // The same predicate is repeated across the array; only the two distinct
        // values (ASCII_ALPHA_NUMERALS and LETTERS) end up as inclusive filters.
        CharacterPredicate[] inclusiveFilters = new CharacterPredicate[5];
        inclusiveFilters[0] = CharacterPredicates.ASCII_ALPHA_NUMERALS;
        inclusiveFilters[1] = CharacterPredicates.LETTERS;
        inclusiveFilters[2] = CharacterPredicates.ASCII_ALPHA_NUMERALS;
        inclusiveFilters[3] = CharacterPredicates.LETTERS;
        inclusiveFilters[4] = CharacterPredicates.LETTERS;

        RandomStringGenerator generator = RandomStringGenerator.builder()
                .filteredBy(inclusiveFilters)
                .get();

        int minLengthInclusive = 2913;
        int maxLengthInclusive = 1114111;
        generator.generate(minLengthInclusive, maxLengthInclusive);
    }
}
