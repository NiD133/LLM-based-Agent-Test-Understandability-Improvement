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
public class CharRange_ESTest_test04 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        CharRange negatedSingleCharacterRange = CharRange.isNot('2');
        CharRange inclusiveRange = CharRange.isIn('2', 'U');

        boolean rangesAreEqual = inclusiveRange.equals(negatedSingleCharacterRange);

        assertTrue(negatedSingleCharacterRange.isNegated());
        assertEquals('2', inclusiveRange.getStart());
        assertEquals('U', inclusiveRange.getEnd());
        assertFalse(rangesAreEqual);
        assertEquals('2', negatedSingleCharacterRange.getStart());
        assertEquals('2', negatedSingleCharacterRange.getEnd());
    }
}
