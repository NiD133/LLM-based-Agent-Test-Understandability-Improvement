package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test07 extends Options_ESTest_scaffolding {

    /**
     * getMatchingOptions returns an empty list when the queried prefix does not
     * match the long name of any registered option.
     */
    @Test(timeout = 4000)
    public void getMatchingOptionsReturnsEmptyListWhenNoLongNameMatches() throws Throwable {
        Options options = new Options();
        options.addRequiredOption("j", "j", false, "j");

        List<String> matches = options.getMatchingOptions(" ] [ long ");

        assertTrue("No registered long option matches the query", matches.isEmpty());
    }
}
