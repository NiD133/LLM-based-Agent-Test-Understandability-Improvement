package org.apache.commons.io;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import static org.junit.Assert.assertNotNull;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class HexDump_ESTest_test6 extends HexDump_ESTest_scaffolding {

    /**
     * Verifies that the public {@link HexDump} constructor can be invoked
     * and yields a non-null instance.
     */
    @Test(timeout = 4000)
    public void constructorCreatesInstance() throws Throwable {
        HexDump hexDump = new HexDump();

        assertNotNull(hexDump);
    }
}
