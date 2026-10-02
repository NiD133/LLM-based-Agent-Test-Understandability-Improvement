package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test06 extends Options_ESTest_scaffolding {

    /**
     * Verifies that getMatchingOptions("") matches every registered long option,
     * because every long name starts with the empty prefix.
     */
    @Test(timeout = 4000)
    public void getMatchingOptions_withEmptyPrefix_matchesRegisteredLongOption() throws Throwable {
        Options options = new Options();
        options.addRequiredOption("j", "NR1W*T", false, "j");

        List<String> matchingOptions = options.getMatchingOptions("");

        // The empty prefix is not itself a long option name.
        assertFalse(matchingOptions.contains(""));
        // But the registered long option "NR1W*T" starts with the empty prefix.
        assertFalse(matchingOptions.isEmpty());
    }
}
