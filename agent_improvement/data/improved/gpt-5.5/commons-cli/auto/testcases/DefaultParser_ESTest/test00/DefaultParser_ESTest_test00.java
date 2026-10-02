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

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        DefaultParser parser = new DefaultParser();
        Options options = new Options();
        OptionGroup requiredGroup = new OptionGroup();
        Option shortOption = new Option("s", "s");

        requiredGroup.setRequired(true);
        OptionGroup groupWithShortOption = requiredGroup.addOption(shortOption);
        Options optionsWithRequiredGroup = options.addOptionGroup(groupWithShortOption);

        String[] arguments = new String[4];
        arguments[0] = "-s";
        Properties defaultProperties = new Properties();

        parser.parse(optionsWithRequiredGroup, arguments, defaultProperties, true);

        assertTrue(groupWithShortOption.isSelected());
        assertTrue(requiredGroup.isSelected());
    }
}
