package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test01 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parses a command line that supplies the short option "-s", which belongs to
     * an option group registered with the {@link Options}. The remaining (null)
     * arguments are tolerated because stopAtNonOption is set to true. Parsing should
     * succeed and return a non-null {@link CommandLine}.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        DefaultParser parser = new DefaultParser();

        // Define a single short option "-s" and register it via an option group.
        Option shortOption = new Option("s", "s");
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(shortOption);

        Options options = new Options();
        options.addOptionGroup(optionGroup);

        // The first argument selects "-s"; the remaining nulls are non-options.
        String[] arguments = new String[3];
        arguments[0] = "-s";

        boolean stopAtNonOption = true;
        CommandLine commandLine = parser.parse(options, arguments, stopAtNonOption);

        assertNotNull(commandLine);
    }
}
