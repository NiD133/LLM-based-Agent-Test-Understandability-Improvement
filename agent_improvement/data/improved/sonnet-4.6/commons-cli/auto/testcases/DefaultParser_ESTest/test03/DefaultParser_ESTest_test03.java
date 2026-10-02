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
public class DefaultParser_ESTest_test03 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that a DefaultParser configured with quote-stripping enabled
     * can successfully parse command-line arguments containing special characters
     * (including embedded quotes) when stopAtNonOption is true.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Define option "-s" that accepts a value; its description contains special chars and quotes
        String optionDescription = "-o=9RUmk9vb/'-u";
        Options options = new Options();
        Options optionsWithS = options.addOption("s", true, optionDescription);

        // Build the argument list: "-s" followed by the special-character string as its value
        String[] args = new String[9];
        args[0] = "-s";
        args[1] = "-o=9RUmk9vb/'-u";

        // Build a parser with leading/trailing quote stripping explicitly enabled
        DefaultParser.Builder builder = DefaultParser.builder();
        Boolean stripQuotes = Boolean.valueOf(true);
        DefaultParser.Builder builderWithQuoteStripping = builder.setStripLeadingAndTrailingQuotes(stripQuotes);
        DefaultParser parser = builderWithQuoteStripping.get();

        // Parse with stopAtNonOption=true; result must be a valid CommandLine object
        CommandLine commandLine = parser.parse(optionsWithS, args, true);
        assertNotNull(commandLine);
    }
}
