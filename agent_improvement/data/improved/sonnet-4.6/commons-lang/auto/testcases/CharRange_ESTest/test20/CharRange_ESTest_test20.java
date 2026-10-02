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
public class CharRange_ESTest_test20 extends CharRange_ESTest_scaffolding {

    /**
     * A single-character range created with CharRange.is('~') should have
     * '~' as both its start and end, and must not contain any other character.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        // Create a range covering only the tilde character '~'
        CharRange tildeRange = CharRange.is('~');

        // '~' is the sole character in this range, so 'U' must not be contained
        boolean containsU = tildeRange.contains('U');
        assertFalse(containsU);

        // A single-character range has the same start and end
        assertEquals('~', tildeRange.getStart());
        assertEquals('~', tildeRange.getEnd());
    }
}
