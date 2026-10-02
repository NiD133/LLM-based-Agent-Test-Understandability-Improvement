package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test33 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing with a null char array is handled gracefully and
     * that the default escape character '$' is preserved afterward.
     */
    @Test(timeout = 4000)
    public void test_replaceNullCharArray_defaultEscapeCharIsPreserved() throws Throwable {
        StrSubstitutor substitutor = new StrSubstitutor();

        // Replacing a null char array should not throw and should leave the substitutor state intact
        substitutor.replace((char[]) null);

        // The default escape character must remain '$' after a no-op replace call
        assertEquals('$', substitutor.getEscapeChar());
    }
}
