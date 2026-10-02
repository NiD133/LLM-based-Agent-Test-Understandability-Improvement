package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test08 extends PosixParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        PosixParser parser = new PosixParser();
        Options options = new Options();
        Option zOption = new Option("Z", "Z");
        Options optionsWithZ = options.addOption(zOption);

        String[] arguments = new String[7];
        arguments[5] = "-ZA";

        String[] flattenedArguments = parser.flatten(optionsWithZ, arguments, true);

        assertEquals(4, flattenedArguments.length);
    }
}
