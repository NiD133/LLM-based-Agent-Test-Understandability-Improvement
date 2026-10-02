package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test26 extends CharRange_ESTest_scaffolding {

    /**
     * A range whose start and end are the same single character should render
     * as just that character, without a "start-end" dash notation.
     */
    @Test(timeout = 4000)
    public void toString_singleCharacterRange_returnsThatCharacter() throws Throwable {
        CharRange singleCharRange = CharRange.isIn('O', 'O');

        String rangeText = singleCharRange.toString();

        assertNotNull(rangeText);
        assertEquals("O", rangeText);
    }
}
