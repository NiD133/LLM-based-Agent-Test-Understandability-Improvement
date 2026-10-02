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
public class Options_ESTest_test10 extends Options_ESTest_scaffolding {

    /**
     * Verifies that addOptions() returns the receiver (this), not the argument.
     * After merging sourceOptions into baseOptions, the returned instance must
     * be the same object as baseOptions, i.e. distinct from sourceOptions.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        Options baseOptions = new Options();

        Options sourceOptions = new Options();
        sourceOptions.addOption("v", " ]", false, "MBMwU(V1:l*[\"cE");

        Options returnedOptions = baseOptions.addOptions(sourceOptions);

        assertNotSame(
            "addOptions() should return the receiver (baseOptions), not the argument (sourceOptions)",
            sourceOptions, returnedOptions
        );
    }
}
