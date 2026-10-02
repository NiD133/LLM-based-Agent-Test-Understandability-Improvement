package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test19 extends CharRange_ESTest_scaffolding {

    /**
     * A single-character range {@code isIn('O', 'O')} should report 'O' as both
     * its start and end, and should report that it contains the character 'O'.
     */
    @Test(timeout = 4000)
    public void singleCharacterRangeContainsThatCharacter() throws Throwable {
        CharRange singleCharRange = CharRange.isIn('O', 'O');

        assertTrue(singleCharRange.contains('O'));
        assertEquals('O', singleCharRange.getStart());
        assertEquals('O', singleCharRange.getEnd());
    }
}
