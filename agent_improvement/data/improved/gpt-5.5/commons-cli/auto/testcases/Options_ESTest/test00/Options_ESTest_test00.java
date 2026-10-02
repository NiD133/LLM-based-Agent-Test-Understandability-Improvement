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
public class Options_ESTest_test00 extends Options_ESTest_scaffolding {

    private static final String NULL_SHORT_OPTION = null;
    private static final String NULL_LONG_OPTION = null;
    private static final boolean OPTION_REQUIRES_ARGUMENT = true;
    private static final String OPTION_DESCRIPTION = "Y6.E))P%{qV#g";

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Options options = new Options();

        options.addRequiredOption(
                NULL_SHORT_OPTION,
                NULL_LONG_OPTION,
                OPTION_REQUIRES_ARGUMENT,
                OPTION_DESCRIPTION);

        boolean hasNullShortOption = options.hasShortOption(NULL_SHORT_OPTION);

        assertTrue(hasNullShortOption);
    }
}
