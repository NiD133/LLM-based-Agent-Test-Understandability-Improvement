package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test45 extends CommandLine_ESTest_scaffolding {

    /**
     * A CommandLine built with no options should still return a non-null
     * (empty) iterator over its Option members.
     */
    @Test(timeout = 4000)
    public void iteratorOfEmptyCommandLineIsNotNull() throws Throwable {
        CommandLine emptyCommandLine = CommandLine.builder().get();

        Iterator<Option> optionsIterator = emptyCommandLine.iterator();

        assertNotNull(optionsIterator);
    }
}
