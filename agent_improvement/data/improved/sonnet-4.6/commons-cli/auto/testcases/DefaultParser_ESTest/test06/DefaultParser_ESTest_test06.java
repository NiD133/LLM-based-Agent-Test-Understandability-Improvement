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
public class DefaultParser_ESTest_test06 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing a null argument array against an empty Options set
     * with stopAtNonOption=true succeeds and returns a valid CommandLine instance.
     */
    @Test(timeout = 4000)
    public void test_parseNullArguments_withStopAtNonOption_returnsNonNullCommandLine() throws Throwable {
        Options emptyOptions = new Options();
        DefaultParser parser = new DefaultParser();

        CommandLine result = parser.parse(emptyOptions, (String[]) null, true);

        assertNotNull("Parsing null arguments with stopAtNonOption=true should return a valid CommandLine", result);
    }
}
