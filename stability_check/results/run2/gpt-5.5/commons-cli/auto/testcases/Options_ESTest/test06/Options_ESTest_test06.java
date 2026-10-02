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

    private static final String SHORT_OPTION = "j";
    private static final String LONG_OPTION = "N\u007fR1W*T";
    private static final String DESCRIPTION = "j";
    private static final String EMPTY_PREFIX = "";

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Options options = new Options();

        options.addRequiredOption(SHORT_OPTION, LONG_OPTION, false, DESCRIPTION);
        List<String> matchingLongOptions = options.getMatchingOptions(EMPTY_PREFIX);

        assertFalse(matchingLongOptions.contains(EMPTY_PREFIX));
        assertFalse(matchingLongOptions.isEmpty());
    }
}
