package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test05 extends DefaultParser_ESTest_scaffolding {

    // Arbitrary string that is not "true", so Boolean.valueOf produces false
    private static final String NON_TRUE_STRING = "-=4{};J}P'";

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Define an option "IGNORE" (short and long) that accepts an argument
        Options options = new Options();
        options.addOption("IGNORE", "IGNORE", true, NON_TRUE_STRING);

        // Empty properties — no default values to inject during parsing
        Properties properties = new Properties();

        // Boolean.valueOf of a non-"true" string evaluates to false, so quote-stripping is disabled
        Boolean stripQuotes = Boolean.valueOf(NON_TRUE_STRING);
        DefaultParser parser = DefaultParser.builder()
                .setStripLeadingAndTrailingQuotes(stripQuotes)
                .get();

        // Parse an argument string that starts with "-=" (not a recognized option)
        String[] args = new String[] { NON_TRUE_STRING };
        CommandLine commandLine = parser.parse(options, args, properties);
        assertNotNull(commandLine);
    }
}
