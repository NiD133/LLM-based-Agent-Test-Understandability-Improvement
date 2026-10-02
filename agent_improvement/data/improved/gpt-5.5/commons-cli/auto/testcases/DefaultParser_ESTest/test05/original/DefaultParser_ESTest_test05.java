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
public class DefaultParser_ESTest_test05 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Options options0 = new Options();
        options0.addOption("IGNORE", "IGNORE", true, "-=4{};J}P'");
        Properties properties0 = new Properties();
        DefaultParser.Builder defaultParser_Builder0 = DefaultParser.builder();
        Boolean boolean0 = Boolean.valueOf("-=4{};J}P'");
        DefaultParser.Builder defaultParser_Builder1 = defaultParser_Builder0.setStripLeadingAndTrailingQuotes(boolean0);
        DefaultParser defaultParser0 = defaultParser_Builder1.get();
        String[] stringArray0 = new String[1];
        stringArray0[0] = "-=4{};J}P'";
        CommandLine commandLine0 = defaultParser0.parse(options0, stringArray0, properties0);
        assertNotNull(commandLine0);
    }
}
