package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test03 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void getOptionCount_returnsZero_whenOptionCharIsNotPresentInCommandLine() throws Throwable {
        // Build a CommandLine that contains one option identified by long name "Options" only (no short opt)
        Option optionWithLongNameOnly = new Option((String) null, "Options");
        CommandLine commandLine = CommandLine.builder()
                .addOption(optionWithLongNameOnly)
                .get();

        // 'h' was never added, so querying its count must return 0
        int countOfH = commandLine.getOptionCount('h');

        assertEquals(0, countOfH);
    }
}
