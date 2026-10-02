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
public class CharRange_ESTest_test18 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        final char excludedCharacter = '2';
        final char characterOutsideExcludedRange = '~';

        final CharRange rangeExcludingTwo = CharRange.isNot(excludedCharacter);
        final boolean containsCharacterOutsideExcludedRange = rangeExcludingTwo.contains(characterOutsideExcludedRange);

        assertTrue("A negated single-character range should contain other characters",
                containsCharacterOutsideExcludedRange);
        assertEquals("The start should be the excluded character",
                excludedCharacter, rangeExcludingTwo.getStart());
        assertEquals("The end should be the excluded character",
                excludedCharacter, rangeExcludingTwo.getEnd());
    }
}
