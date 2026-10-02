package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test02 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that {@link CommandLine#getOptionObject(String)} returns {@code null}
     * when queried with an empty option name that matches none of the added options.
     */
    @Test(timeout = 4000)
    public void getOptionObjectWithUnknownEmptyNameReturnsNull() throws Throwable {
        // Build a command line containing a single option named "p1".
        CommandLine commandLine = new CommandLine();
        Option p1Option = new Option("p1", "", true, "p1");
        commandLine.addOption(p1Option);

        // The empty name "" does not match the "p1" option, so no value is resolved.
        Object resolvedValue = commandLine.getOptionObject("");

        assertNull(resolvedValue);
    }
}
