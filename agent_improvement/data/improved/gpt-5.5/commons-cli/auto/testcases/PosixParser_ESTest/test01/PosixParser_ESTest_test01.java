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
public class PosixParser_ESTest_test01 extends PosixParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        String[] argumentsWithSparseOption = new String[13];
        argumentsWithSparseOption[2] = "-bdKQ";

        Options configuredOptions = new Options();
        Option bdKqOption = new Option("bdKQ", false, "bdKQ");
        configuredOptions.addOption(bdKqOption);

        PosixParser parser = new PosixParser();
        CommandLine parsedCommandLine = parser.parse(configuredOptions, argumentsWithSparseOption);

        assertNotNull(parsedCommandLine);
    }
}
