package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test26 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#toString()} returns the constant's display name,
     * which for {@link IOCase#INSENSITIVE} is "Insensitive".
     */
    @Test(timeout = 4000)
    public void toString_forInsensitive_returnsInsensitiveName() throws Throwable {
        IOCase insensitiveCase = IOCase.INSENSITIVE;

        String description = insensitiveCase.toString();

        assertEquals("Insensitive", description);
    }
}
