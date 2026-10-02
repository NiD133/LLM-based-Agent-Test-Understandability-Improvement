package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test01 extends DefaultParser_ESTest_scaffolding {

    // Verifies that parsing succeeds when a recognized short option ("-s") is the first argument
    // in a partially-filled array (remaining slots are null) and stopAtNonOption is true.
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        DefaultParser parser = new DefaultParser();

        // Build an Options set containing a single option group with one short option "s"
        Option shortOptionS = new Option("s", "s");
        OptionGroup group = new OptionGroup();
        OptionGroup groupWithS = group.addOption(shortOptionS);
        Options options = new Options();
        options.addOptionGroup(groupWithS);

        // Provide an argument array where only the first slot is set to the recognised flag "-s"
        String[] args = new String[3];
        args[0] = "-s";

        // Parse with stopAtNonOption=true; the recognised option should be accepted without error
        CommandLine commandLine = parser.parse(options, args, true);
        assertNotNull(commandLine);
    }
}
