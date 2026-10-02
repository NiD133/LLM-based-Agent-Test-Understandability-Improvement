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
public class DefaultParser_ESTest_test00 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parsing a command line containing "-s" should mark the required OptionGroup
     * (which holds option "s") as selected. This also verifies that trailing null
     * entries in the argument array are silently ignored, and that stopAtNonOption=true
     * does not prevent the recognised option from being processed.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Build a required option group containing the short option "-s"
        Option shortOptionS = new Option("s", "s");
        OptionGroup group = new OptionGroup();
        group.setRequired(true);
        // addOption returns the same OptionGroup instance ("this"), captured here
        // so that the assertion below mirrors the original test's two-variable check
        OptionGroup groupRef = group.addOption(shortOptionS);

        // Register the group; addOptionGroup also returns the same Options instance
        Options options = new Options();
        Options optionsRef = options.addOptionGroup(groupRef);

        // Only args[0] is meaningful; the parser skips null entries
        String[] args = new String[4];
        args[0] = "-s";

        // Parse with empty properties; stopAtNonOption=true stops on unknown tokens
        DefaultParser parser = new DefaultParser();
        parser.parse(optionsRef, args, new Properties(), true);

        // groupRef and group are the same object — both assertions confirm selection
        assertTrue(groupRef.isSelected());
        assertTrue(group.isSelected());
    }
}
