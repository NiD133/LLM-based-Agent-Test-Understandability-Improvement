package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test10 extends Options_ESTest_scaffolding {

    /**
     * addOptions copies the options from the source into the target and returns
     * the target instance ("this"), not the source that was passed in.
     */
    @Test(timeout = 4000)
    public void addOptionsReturnsTargetNotSource() throws Throwable {
        Options target = new Options();

        Options source = new Options();
        source.addOption("v", " ]", false, "MBMwU(V1:l*[\"cE");

        Options result = target.addOptions(source);

        assertNotSame(source, result);
    }
}
