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

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // negatedSpaceRange represents all characters EXCEPT space
        CharRange negatedSpaceRange = CharRange.isNot(' ');

        // singleSpaceRange represents exactly the space character
        CharRange singleSpaceRange = CharRange.isIn(' ', ' ');

        // A range excluding space cannot fully contain a range that is only space
        boolean negatedSpaceContainsSingleSpace = negatedSpaceRange.contains(singleSpaceRange);

        // Verify both ranges are correctly bounded to the space character
        assertEquals(' ', singleSpaceRange.getEnd());
        assertEquals(' ', negatedSpaceRange.getStart());
        assertEquals(' ', negatedSpaceRange.getEnd());
        assertEquals(' ', singleSpaceRange.getStart());

        assertFalse(negatedSpaceContainsSingleSpace);
    }
}
