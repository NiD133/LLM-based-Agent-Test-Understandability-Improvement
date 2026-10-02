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
public class Options_ESTest_test02 extends Options_ESTest_scaffolding {

    /**
     * Verifies that hasOption() returns true for a long option name that was
     * registered via addOption(), even when the long option name contains
     * non-alphanumeric characters (e.g., " ]").
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Options options = new Options();
        // Register an option with short name "v" and long name " ]" (no argument required)
        options.addOption("v", " ]", false, "MBMwU(V1:l*[\"cE");

        // The long option name " ]" should be recognized by hasOption()
        boolean optionExists = options.hasOption(" ]");
        assertTrue(optionExists);
    }
}
