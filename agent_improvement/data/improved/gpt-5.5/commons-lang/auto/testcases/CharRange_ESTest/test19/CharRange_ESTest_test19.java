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
public class CharRange_ESTest_test19 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        final char onlyCharacterInRange = 'O';

        CharRange singleCharacterRange = CharRange.isIn(onlyCharacterInRange, onlyCharacterInRange);
        boolean containsOnlyCharacter = singleCharacterRange.contains(onlyCharacterInRange);

        assertEquals(onlyCharacterInRange, singleCharacterRange.getStart());
        assertTrue(containsOnlyCharacter);
        assertEquals(onlyCharacterInRange, singleCharacterRange.getEnd());
    }
}
