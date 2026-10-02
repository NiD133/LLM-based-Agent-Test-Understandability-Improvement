package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test00 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parsing a command line that supplies the short option of a required option
     * group should mark that group as selected.
     */
    @Test(timeout = 4000)
    public void parsingShortOptionMarksItsRequiredGroupSelected() throws Throwable {
        DefaultParser parser = new DefaultParser();

        // Build a required option group containing the single option "-s".
        Option shortOption = new Option("s", "s");
        OptionGroup requiredGroup = new OptionGroup();
        requiredGroup.setRequired(true);
        // addOption returns the same group instance for fluent chaining.
        requiredGroup.addOption(shortOption);

        Options options = new Options();
        options.addOptionGroup(requiredGroup);

        // Command line that activates the "-s" option (trailing nulls are ignored).
        String[] arguments = new String[4];
        arguments[0] = "-s";

        parser.parse(options, arguments, new Properties(), true);

        assertTrue("The required group should be selected after parsing -s",
                requiredGroup.isSelected());
    }
}
