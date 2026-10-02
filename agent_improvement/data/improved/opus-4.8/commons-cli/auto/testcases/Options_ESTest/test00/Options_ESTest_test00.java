package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test00 extends Options_ESTest_scaffolding {

    /**
     * A required option added with a null short name is still registered under
     * the (null) short-name key, so hasShortOption(null) reports that it exists.
     */
    @Test(timeout = 4000)
    public void hasShortOptionFindsRequiredOptionAddedWithNullShortName() throws Throwable {
        Options options = new Options();
        String shortName = null;
        String longName = null;
        boolean hasArg = true;
        String description = "Y6.E))P%{qV#g";

        options.addRequiredOption(shortName, longName, hasArg, description);

        boolean found = options.hasShortOption(shortName);

        assertTrue(found);
    }
}
