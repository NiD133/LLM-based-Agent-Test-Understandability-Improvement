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
        RandomStringGenerator.Builder randomStringGenerator_Builder0 = RandomStringGenerator.builder();
        CharacterPredicate[] characterPredicateArray0 = new CharacterPredicate[5];
        CharacterPredicates characterPredicates0 = CharacterPredicates.ASCII_ALPHA_NUMERALS;
        characterPredicateArray0[0] = (CharacterPredicate) characterPredicates0;
        CharacterPredicates characterPredicates1 = CharacterPredicates.LETTERS;
        characterPredicateArray0[1] = (CharacterPredicate) characterPredicates1;
        characterPredicateArray0[2] = (CharacterPredicate) characterPredicates0;
        characterPredicateArray0[3] = (CharacterPredicate) characterPredicates1;
        characterPredicateArray0[4] = (CharacterPredicate) characterPredicates1;
        RandomStringGenerator.Builder randomStringGenerator_Builder1 = randomStringGenerator_Builder0.filteredBy(characterPredicateArray0);
        RandomStringGenerator randomStringGenerator0 = randomStringGenerator_Builder1.get();
        // Undeclared exception!
        randomStringGenerator0.generate(2913, 1114111);
    }
}
