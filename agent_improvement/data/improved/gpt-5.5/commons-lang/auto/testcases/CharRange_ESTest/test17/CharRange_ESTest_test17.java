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
public class CharRange_ESTest_test17 extends CharRange_ESTest_scaffolding {

    private static final char ONLY_CHARACTER_IN_RANGE = 'O';

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        CharRange singleCharacterRange = CharRange.isIn(ONLY_CHARACTER_IN_RANGE, ONLY_CHARACTER_IN_RANGE);

        boolean rangeContainsItself = singleCharacterRange.contains(singleCharacterRange);

        assertTrue("A range should contain an identical range", rangeContainsItself);
        assertEquals("Start character should match the requested inclusive lower bound",
                ONLY_CHARACTER_IN_RANGE, singleCharacterRange.getStart());
        assertEquals("End character should match the requested inclusive upper bound",
                ONLY_CHARACTER_IN_RANGE, singleCharacterRange.getEnd());
    }
}
