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

    /**
     * Parses arguments with a DefaultParser configured to strip balanced
     * leading/trailing quotes, and verifies that a CommandLine is produced.
     *
     * The single recognized argument ("-=R};SP'-") begins with the option
     * prefix but is not a known option; parsing stops at the non-option
     * (stopAtNonOption = true) instead of throwing, so a CommandLine is still
     * returned.
     */
    @Test(timeout = 4000)
    public void parseWithQuoteStrippingReturnsCommandLine() throws Throwable {
        // Define a single option "-d"/"--d" that takes an argument.
        Options options = new Options();
        options.addOption("d", "d", true, "-=R};SP'-");

        // Build a parser that strips balanced leading and trailing quotes.
        DefaultParser parser = DefaultParser.builder()
                .setStripLeadingAndTrailingQuotes(Boolean.TRUE)
                .get();

        // First argument looks like an option prefix but matches no option;
        // remaining slots are left null.
        String[] arguments = new String[6];
        arguments[0] = "-=R};SP'-";

        boolean stopAtNonOption = true;
        CommandLine commandLine = parser.parse(options, arguments, stopAtNonOption);

        assertNotNull(commandLine);
    }
}
