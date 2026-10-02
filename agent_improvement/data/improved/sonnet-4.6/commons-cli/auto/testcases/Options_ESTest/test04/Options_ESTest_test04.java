package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test04 extends Options_ESTest_scaffolding {

    // Verifies that hasLongOption returns false when no long options have been registered.
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Options emptyOptions = new Options();
        boolean hasLongOptionT = emptyOptions.hasLongOption("T");
        assertFalse(hasLongOptionT);
    }
}
