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

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // An empty-string prefix should match all registered long options via prefix matching
        Options options = new Options();
        options.addRequiredOption("j", "NR1W*T", false, "j");

        List<String> matchingOptions = options.getMatchingOptions("");

        // The result should contain actual long option names, not the empty string itself
        assertFalse(matchingOptions.contains(""));
        // At least the registered long option "NR1W*T" should be matched by the empty prefix
        assertFalse(matchingOptions.isEmpty());
    }
}
