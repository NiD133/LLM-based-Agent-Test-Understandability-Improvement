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
public class Options_ESTest_test08 extends Options_ESTest_scaffolding {

    /**
     * Verifies that getMatchingOptions returns the long name of an option
     * whose long name exactly matches the requested string. A required option
     * with both short name "j" and long name "j" is registered, then looked up
     * by "j"; the matching list should contain that long name.
     */
    @Test(timeout = 4000)
    public void getMatchingOptionsReturnsExactLongNameMatch() throws Throwable {
        Options options = new Options();

        options.addRequiredOption("j", "j", false, "j");
        List<String> matchingOptions = options.getMatchingOptions("j");

        assertTrue(matchingOptions.contains("j"));
    }
}
