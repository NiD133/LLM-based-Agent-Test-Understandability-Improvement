package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test04 extends OptionFormatter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Arrange: an option whose short and long option names are both absent.
        Option optionWithNoNames = new Option((String) null, (String) null);
        OptionFormatter formatter = OptionFormatter.from(optionWithNoNames);

        // Act: format the combined short/long option display text.
        String formattedOptionNames = formatter.getBothOpt();

        // Assert: with neither option name present, nothing is displayed.
        assertEquals("", formattedOptionNames);
    }
}
