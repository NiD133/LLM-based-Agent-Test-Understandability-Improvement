package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test25 extends IOCase_ESTest_scaffolding {

    /**
     * IOCase.forName only accepts the human-readable constant names
     * ("Sensitive", "Insensitive", "System"). Passing any other string -
     * here the synthetic enum field name "$VALUES" - must be rejected with
     * an IllegalArgumentException thrown by IOCase itself.
     */
    @Test(timeout = 4000)
    public void forName_withUnknownName_throwsIllegalArgumentException() throws Throwable {
        try {
            IOCase.forName("$VALUES");
            fail("Expected IllegalArgumentException for unknown IOCase name '$VALUES'");
        } catch (IllegalArgumentException e) {
            // Message: "Illegal IOCase name: $VALUES"
            verifyException("org.apache.commons.io.IOCase", e);
        }
    }
}
