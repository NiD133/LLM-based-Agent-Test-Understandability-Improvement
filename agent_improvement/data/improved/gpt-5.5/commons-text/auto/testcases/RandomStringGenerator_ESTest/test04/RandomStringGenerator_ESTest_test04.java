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

    private static final int MINIMUM_GENERATED_LENGTH = 2913;
    private static final int MAXIMUM_GENERATED_LENGTH = 1114111;

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        RandomStringGenerator.Builder generatorBuilder = RandomStringGenerator.builder();
        CharacterPredicate[] acceptedCharacterPredicates = createAcceptedCharacterPredicates();

        RandomStringGenerator.Builder filteredBuilder = generatorBuilder.filteredBy(acceptedCharacterPredicates);
        RandomStringGenerator generator = filteredBuilder.get();

        generator.generate(MINIMUM_GENERATED_LENGTH, MAXIMUM_GENERATED_LENGTH);
    }

    private CharacterPredicate[] createAcceptedCharacterPredicates() {
        CharacterPredicate[] acceptedCharacterPredicates = new CharacterPredicate[5];
        CharacterPredicates alphaNumerals = CharacterPredicates.ASCII_ALPHA_NUMERALS;
        CharacterPredicates letters = CharacterPredicates.LETTERS;

        acceptedCharacterPredicates[0] = (CharacterPredicate) alphaNumerals;
        acceptedCharacterPredicates[1] = (CharacterPredicate) letters;
        acceptedCharacterPredicates[2] = (CharacterPredicate) alphaNumerals;
        acceptedCharacterPredicates[3] = (CharacterPredicate) letters;
        acceptedCharacterPredicates[4] = (CharacterPredicate) letters;
        return acceptedCharacterPredicates;
    }
}
