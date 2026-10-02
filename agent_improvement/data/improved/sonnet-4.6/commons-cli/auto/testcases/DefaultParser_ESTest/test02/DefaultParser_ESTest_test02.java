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
public class DefaultParser_ESTest_test02 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_parseShortOptionWithArgument_usingParserBuiltWithQuoteStrippingDisabled() throws Throwable {
        // Set up options: "-s" accepts an argument
        Options options = new Options();
        Options optionsWithS = options.addOption("s", true, "s");

        // Prepare command-line args: "-s" followed by its value "s"
        // Array size is 9 but only the first two slots are populated
        String[] args = new String[9];
        args[0] = "-s";
        args[1] = "s";

        // Boolean.valueOf("s") evaluates to false (only "true" returns true)
        // so stripLeadingAndTrailingQuotes is set to false
        Boolean stripQuotes = Boolean.valueOf("s");
        DefaultParser parser = DefaultParser.builder()
                .setStripLeadingAndTrailingQuotes(stripQuotes)
                .get();

        CommandLine commandLine = parser.parse(optionsWithS, args, false);
        assertNotNull(commandLine);
    }
}
