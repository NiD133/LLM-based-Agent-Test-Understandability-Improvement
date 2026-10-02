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
     * Parsing a null argument array (with stopAtNonOption enabled) against an
     * empty set of options should still yield a valid, non-null CommandLine.
     * The parser treats a null argument array as "no arguments to process".
     */
    @Test(timeout = 4000)
    public void parseWithNullArgumentsReturnsCommandLine() throws Throwable {
        Options noOptions = new Options();
        DefaultParser parser = new DefaultParser();
        String[] nullArguments = null;
        boolean stopAtNonOption = true;

        CommandLine commandLine = parser.parse(noOptions, nullArguments, stopAtNonOption);

        assertNotNull(commandLine);
    }
}
