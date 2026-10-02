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
public class OptionFormatter_ESTest_test10 extends OptionFormatter_ESTest_scaffolding {

    private static final String SINGLE_SPACE_ARGUMENT_NAME = " ";
    private static final String DEFAULT_FORMATTED_SPACE_ARGUMENT_NAME = "< >";

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        OptionFormatter.Builder optionFormatterBuilder = OptionFormatter.builder();

        String formattedArgumentName = optionFormatterBuilder.toArgName(SINGLE_SPACE_ARGUMENT_NAME);

        assertEquals(DEFAULT_FORMATTED_SPACE_ARGUMENT_NAME, formattedArgumentName);
    }
}
