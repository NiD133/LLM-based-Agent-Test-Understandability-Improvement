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
public class Options_ESTest_test06 extends Options_ESTest_scaffolding {

    /**
     * Verifies that getMatchingOptions("") returns all long-named options
     * (since every string starts with the empty prefix) but does not include
     * the empty string itself as a match.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Options options = new Options();
        // Add a required option with short name "j" and long name "NR1W*T"
        options.addRequiredOption("j", "NR1W*T", false, "j");

        // An empty prefix matches all long options because every string starts with ""
        List<String> matchingOptions = options.getMatchingOptions("");

        // The list should contain the long option "NR1W*T", not the empty string
        assertFalse(matchingOptions.contains(""));
        assertFalse(matchingOptions.isEmpty());
    }
}
