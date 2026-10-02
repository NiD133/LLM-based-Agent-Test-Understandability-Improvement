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
public class CharRange_ESTest_test00 extends CharRange_ESTest_scaffolding {

    /**
     * A negated CharRange's toString should return "^start-end" format,
     * and repeated calls should return the same cached string value.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Create a negated range: all characters NOT between 'G' and 'j' inclusive
        CharRange negatedGToJ = CharRange.isNotIn('G', 'j');

        // First call populates the internal toString cache
        negatedGToJ.toString();

        // Second call returns the cached result; '^' prefix indicates negation
        String stringRepresentation = negatedGToJ.toString();

        assertNotNull(stringRepresentation);
        assertEquals("^G-j", stringRepresentation);
    }
}
