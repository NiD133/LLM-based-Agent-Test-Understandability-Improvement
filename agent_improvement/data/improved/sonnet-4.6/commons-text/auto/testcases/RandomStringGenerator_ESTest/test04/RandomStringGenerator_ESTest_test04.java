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

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Configure predicates: ASCII alphanumerics and letters (with duplicates, matching original array layout)
        CharacterPredicate[] filters = new CharacterPredicate[] {
            CharacterPredicates.ASCII_ALPHA_NUMERALS,
            CharacterPredicates.LETTERS,
            CharacterPredicates.ASCII_ALPHA_NUMERALS,
            CharacterPredicates.LETTERS,
            CharacterPredicates.LETTERS
        };

        RandomStringGenerator generator = RandomStringGenerator.builder()
            .filteredBy(filters)
            .get();

        // Generate a string whose length is randomly chosen between 2913 and Character.MAX_CODE_POINT
        // Undeclared exception!
        generator.generate(2913, 1114111);
    }
}
