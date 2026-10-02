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
public class OptionFormatter_ESTest_test05 extends OptionFormatter_ESTest_scaffolding {

    private static final String NO_SHORT_OPTION = null;
    private static final String NO_DESCRIPTION = null;
    private static final String REQUIRED_ARGUMENT_SYNTAX = " <arg>";

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Option optionRequiringUnnamedArgument = new Option(NO_SHORT_OPTION, true, NO_DESCRIPTION);
        OptionFormatter formatter = OptionFormatter.from(optionRequiringUnnamedArgument);

        String syntaxOption = formatter.toSyntaxOption(true);

        assertEquals(REQUIRED_ARGUMENT_SYNTAX, syntaxOption);
    }
}
