package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test08 extends Options_ESTest_scaffolding {

    /**
     * Verifies that getMatchingOptions returns the long option name when a required
     * option with that long name has been registered and an exact match is queried.
     */
    @Test(timeout = 4000)
    public void test_getMatchingOptions_containsLongOptionName_afterAddRequiredOption() throws Throwable {
        Options options = new Options();
        // addRequiredOption registers a required option with short name "j",
        // long name "j", no argument, and description "j"; it returns the same Options instance.
        Options optionsWithRequiredJ = options.addRequiredOption("j", "j", false, "j");

        // getMatchingOptions searches the long-option map; an exact match returns a
        // singleton list containing the long option name.
        List<String> matchingOptions = optionsWithRequiredJ.getMatchingOptions("j");

        assertTrue(matchingOptions.contains("j"));
    }
}
