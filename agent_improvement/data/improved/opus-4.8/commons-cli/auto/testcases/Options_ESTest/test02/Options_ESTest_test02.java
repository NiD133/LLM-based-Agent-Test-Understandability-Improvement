package org.apache.commons.cli;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test02 extends Options_ESTest_scaffolding {

    /**
     * Verifies that an option registered with a long name can be looked up by that
     * long name via {@link Options#hasOption(String)}.
     */
    @Test(timeout = 4000)
    public void hasOptionFindsOptionByItsLongName() throws Throwable {
        final String shortName = "v";
        final String longName = " ]";

        final Options options = new Options();
        options.addOption(shortName, longName, false, "MBMwU(V1:l*[\"cE");

        final boolean foundByLongName = options.hasOption(longName);

        assertTrue("hasOption should locate the option by its long name", foundByLongName);
    }
}
