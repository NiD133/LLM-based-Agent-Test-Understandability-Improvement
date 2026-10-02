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
public class Options_ESTest_test02 extends Options_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        final Options options = new Options();
        final String shortOptionName = "v";
        final String longOptionName = " ]";
        final boolean hasArgument = false;
        final String description = "MBMwU(V1:l*[\"cE";

        options.addOption(shortOptionName, longOptionName, hasArgument, description);

        final boolean containsLongOption = options.hasOption(longOptionName);
        assertTrue(containsLongOption);
    }
}
