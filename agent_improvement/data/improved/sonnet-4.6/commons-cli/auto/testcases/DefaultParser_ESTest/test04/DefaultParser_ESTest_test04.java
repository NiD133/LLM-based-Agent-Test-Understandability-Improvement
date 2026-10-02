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
public class DefaultParser_ESTest_test04 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Register option "d" (short and long) that requires an argument
        Options options = new Options();
        options.addOption("d", "d", true, "-=R};SP'-");

        // Build a parser with quote-stripping enabled
        DefaultParser parser = DefaultParser.builder()
            .setStripLeadingAndTrailingQuotes(Boolean.TRUE)
            .get();

        // First token is an unrecognized option-like string; remaining slots are null
        String[] args = new String[6];
        args[0] = "-=R};SP'-";

        // With stopAtNonOption=true, the unrecognized first token stops parsing
        CommandLine commandLine = parser.parse(options, args, true);
        assertNotNull(commandLine);
    }
}
