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

    /**
     * Parsing an argument array whose only non-null entry is a registered
     * long-named option ("-bdKQ") should succeed and yield a CommandLine.
     */
    @Test(timeout = 4000)
    public void parseRegisteredOptionReturnsCommandLine() throws Throwable {
        // Build an argument array padded with nulls (the parser skips nulls),
        // placing the option token "-bdKQ" at index 2.
        String[] arguments = new String[13];
        arguments[2] = "-bdKQ";

        // Register an option named "bdKQ" that takes no argument.
        Options options = new Options();
        Option bdKQOption = new Option("bdKQ", false, "bdKQ");
        options.addOption(bdKQOption);

        PosixParser parser = new PosixParser();
        CommandLine commandLine = parser.parse(options, arguments);

        assertNotNull(commandLine);
    }
}
