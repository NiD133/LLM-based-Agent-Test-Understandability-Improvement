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
public class RandomStringGenerator_ESTest_test00 extends RandomStringGenerator_ESTest_scaffolding {

    private static final int SELECTED_CHARACTER_COUNT = 2;
    private static final int REQUESTED_CODE_POINT_COUNT = 1114111;

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        RandomStringGenerator.Builder generatorBuilder = new RandomStringGenerator.Builder();
        IntUnaryOperator identityRandomIndex = IntUnaryOperator.identity();
        char[] selectedCharacters = new char[SELECTED_CHARACTER_COUNT];

        RandomStringGenerator.Builder builderWithSelectedCharacters = generatorBuilder.selectFrom(selectedCharacters);
        RandomStringGenerator.Builder builderWithIdentityRandom = builderWithSelectedCharacters.usingRandom(identityRandomIndex);
        RandomStringGenerator generator = builderWithIdentityRandom.get();

        try {
            generator.generate(REQUESTED_CODE_POINT_COUNT);
            fail("Expecting exception: IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
        }
    }
}
