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
public class OptionFormatter_ESTest_test15 extends OptionFormatter_ESTest_scaffolding {

    /**
     * Verifies that {@link OptionFormatter.Builder#setOptSeparator(String)} supports
     * method chaining by returning the same builder instance.
     */
    @Test(timeout = 4000)
    public void test15_setOptSeparator_returnsBuilderForMethodChaining() throws Throwable {
        OptionFormatter.Builder builder = OptionFormatter.builder();

        // setOptSeparator with an empty string should return the same builder (fluent API)
        OptionFormatter.Builder builderAfterSet = builder.setOptSeparator("");

        assertSame(builderAfterSet, builder);
    }
}
