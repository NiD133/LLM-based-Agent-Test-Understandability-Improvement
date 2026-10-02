package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.BiFunction;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test13 extends OptionFormatter_ESTest_scaffolding {

    /**
     * Verifies that {@link OptionFormatter.Builder#setSyntaxFormatFunction} returns the same
     * builder instance, enabling fluent-style chaining.
     */
    @Test(timeout = 4000)
    public void test_setSyntaxFormatFunction_returnsSameBuilderInstance() throws Throwable {
        OptionFormatter.Builder builder = OptionFormatter.builder();

        BiFunction<OptionFormatter, Boolean, String> mockSyntaxFormatFunction =
                (BiFunction<OptionFormatter, Boolean, String>) mock(BiFunction.class, new ViolatedAssumptionAnswer());

        OptionFormatter.Builder returnedBuilder = builder.setSyntaxFormatFunction(mockSyntaxFormatFunction);

        assertSame(builder, returnedBuilder);
    }
}
