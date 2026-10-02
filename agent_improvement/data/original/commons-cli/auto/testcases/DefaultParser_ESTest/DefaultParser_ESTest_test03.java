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

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Options options0 = new Options();
        Options options1 = options0.addOption("s", true, "-o=9RUmk9vb/'-u");
        String[] stringArray0 = new String[9];
        stringArray0[0] = "-s";
        stringArray0[1] = "-o=9RUmk9vb/'-u";
        DefaultParser.Builder defaultParser_Builder0 = DefaultParser.builder();
        Boolean boolean0 = Boolean.valueOf(true);
        DefaultParser.Builder defaultParser_Builder1 = defaultParser_Builder0.setStripLeadingAndTrailingQuotes(boolean0);
        DefaultParser defaultParser0 = defaultParser_Builder1.get();
        CommandLine commandLine0 = defaultParser0.parse(options1, stringArray0, true);
        assertNotNull(commandLine0);
    }
}
