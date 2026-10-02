package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test09 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        CharRange everythingExceptDigitTwo = CharRange.isNot('2');
        CharRange digitTwoThroughUppercaseU = CharRange.isIn('2', 'U');

        boolean rangeContainsNegatedSingleCharacter = digitTwoThroughUppercaseU.contains(everythingExceptDigitTwo);

        assertEquals('2', digitTwoThroughUppercaseU.getStart());
        assertEquals('2', everythingExceptDigitTwo.getEnd());
        assertEquals('U', digitTwoThroughUppercaseU.getEnd());
        assertEquals('2', everythingExceptDigitTwo.getStart());
        assertFalse(rangeContainsNegatedSingleCharacter);
    }
}
