package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test24 extends IOCase_ESTest_scaffolding {

    /**
     * The static {@link IOCase#isCaseSensitive(IOCase)} is null-safe: a null
     * argument must yield {@code false} rather than throwing.
     */
    @Test(timeout = 4000)
    public void isCaseSensitiveReturnsFalseForNullIOCase() throws Throwable {
        boolean caseSensitive = IOCase.isCaseSensitive((IOCase) null);

        assertFalse(caseSensitive);
    }
}
