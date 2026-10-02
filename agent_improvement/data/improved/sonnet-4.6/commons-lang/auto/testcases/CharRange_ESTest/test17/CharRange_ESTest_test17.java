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

    // A single-character range (start == end) should always contain itself.
    @Test(timeout = 4000)
    public void singleCharRange_containsItself() throws Throwable {
        CharRange singleCharO = CharRange.isIn('O', 'O');

        boolean containsItself = singleCharO.contains(singleCharO);

        assertTrue("A range should contain itself", containsItself);
        assertEquals("Start character should be 'O'", 'O', singleCharO.getStart());
        assertEquals("End character should be 'O'", 'O', singleCharO.getEnd());
    }
}
