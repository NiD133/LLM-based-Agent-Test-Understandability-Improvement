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
public class OptionFormatter_ESTest_test18 extends OptionFormatter_ESTest_scaffolding {

    private static final String NO_SHORT_OPTION = null;
    private static final String NO_LONG_OPTION = null;
    private static final String EMPTY_SYNTAX = "";

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        Option optionWithoutNames = new Option(NO_SHORT_OPTION, NO_LONG_OPTION);
        OptionFormatter formatter = OptionFormatter.from(optionWithoutNames);

        String syntax = formatter.toSyntaxOption();

        assertEquals(EMPTY_SYNTAX, syntax);
    }
}
