package org.apache.commons.cli;

import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test16 extends Options_ESTest_scaffolding {

    /**
     * A freshly constructed Options has no required options, so
     * getRequiredOptions() should return an empty list.
     */
    @Test(timeout = 4000)
    public void getRequiredOptionsOnNewOptionsReturnsEmptyList() throws Throwable {
        Options options = new Options();

        List<?> requiredOptions = options.getRequiredOptions();

        assertTrue("A new Options instance should have no required options",
                requiredOptions.isEmpty());
    }
}
