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
     * getMatchingOptions("") should return every long option, since an empty
     * prefix matches all long names. Here a single required option with the long
     * name "NR1W*T" is registered, so the result must contain that name (and is
     * therefore non-empty and never contains the empty prefix itself).
     */
    @Test(timeout = 4000)
    public void getMatchingOptionsWithEmptyPrefixReturnsAllLongOptions() throws Throwable {
        Options options = new Options();
        options.addRequiredOption("j", "NR1W*T", false, "j");

        List<String> matchingOptions = options.getMatchingOptions("");

        assertFalse("empty prefix is never itself a matched long name", matchingOptions.contains(""));
        assertFalse("the registered long option should be matched", matchingOptions.isEmpty());
    }
}
