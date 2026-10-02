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
public class Options_ESTest_test07 extends Options_ESTest_scaffolding {

    // " ] [ long " is a substring of Options.toString() output, not a valid option prefix
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Options options = new Options();
        Options optionsWithJ = options.addRequiredOption("j", "j", false, "j");

        List<String> matchingOptions = optionsWithJ.getMatchingOptions(" ] [ long ");

        assertTrue(matchingOptions.isEmpty());
    }
}
