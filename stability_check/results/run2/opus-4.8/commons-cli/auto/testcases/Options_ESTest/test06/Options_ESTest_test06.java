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
     * An empty search string is a prefix of every registered long option, so
     * {@link Options#getMatchingOptions(String)} should return all long options
     * (here the single option "NR1W*T") rather than an empty list, and the
     * result should never contain the empty string itself.
     */
    @Test(timeout = 4000)
    public void getMatchingOptionsWithEmptyPrefixReturnsAllLongOptions() throws Throwable {
        Options options = new Options();
        options.addRequiredOption("j", "NR1W*T", false, "j");

        List<String> matchingOptions = options.getMatchingOptions("");

        assertFalse("empty prefix should match the long option, not the empty string",
                matchingOptions.contains(""));
        assertFalse("every long option matches an empty prefix, so the result is non-empty",
                matchingOptions.isEmpty());
    }
}
