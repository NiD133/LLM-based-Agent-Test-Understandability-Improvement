package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test23 extends IOCase_ESTest_scaffolding {

    /**
     * Verifies that {@link IOCase#value(IOCase, IOCase)} returns the primary value
     * when it is non-null (ignoring the default), and that the resulting SENSITIVE
     * constant is reported as case-sensitive by {@link IOCase#isCaseSensitive(IOCase)}.
     */
    @Test(timeout = 4000)
    public void valueReturnsNonNullPrimaryAndReportsCaseSensitive() throws Throwable {
        IOCase defaultCase = IOCase.INSENSITIVE;
        IOCase primaryCase = IOCase.SENSITIVE;

        // value() returns the primary value because it is non-null, so the default is unused.
        IOCase selectedCase = IOCase.value(primaryCase, defaultCase);
        assertEquals(IOCase.SENSITIVE, selectedCase);

        // The selected SENSITIVE constant is case-sensitive.
        boolean caseSensitive = IOCase.isCaseSensitive(selectedCase);
        assertTrue(caseSensitive);
    }
}
