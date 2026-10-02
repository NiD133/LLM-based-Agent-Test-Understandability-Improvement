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
     * Verifies that getMatchingOptions("") returns all registered long options,
     * since every long option name starts with the empty string prefix.
     * The result must not be empty and must not contain the empty string itself.
     */
    @Test(timeout = 4000)
    public void test06_getMatchingOptionsWithEmptyPrefix_returnsAllLongOptions() throws Throwable {
        // Arrange: register one required option with long name "NR1W*T"
        Options options = new Options();
        options.addRequiredOption("j", "NR1W*T", false, "j");

        // Act: query with an empty prefix — all long option names start with ""
        List<String> matchingOptions = options.getMatchingOptions("");

        // Assert: the result contains matching option names, not the empty string
        assertFalse(matchingOptions.contains(""));
        assertFalse(matchingOptions.isEmpty());
    }
}
