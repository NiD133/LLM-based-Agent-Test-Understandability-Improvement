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
public class CharRange_ESTest_test11 extends CharRange_ESTest_scaffolding {

    private static final char SPACE = ' ';

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        CharRange allCharactersExceptSpace = CharRange.isNot(SPACE);
        CharRange onlySpace = CharRange.isIn(SPACE, SPACE);

        boolean containsOnlySpace = allCharactersExceptSpace.contains(onlySpace);

        assertEquals(SPACE, onlySpace.getEnd());
        assertEquals(SPACE, allCharactersExceptSpace.getStart());
        assertEquals(SPACE, allCharactersExceptSpace.getEnd());
        assertEquals(SPACE, onlySpace.getStart());
        assertFalse(containsOnlySpace);
    }
}
