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
public class OptionFormatter_ESTest_test13 extends OptionFormatter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        OptionFormatter.Builder optionFormatter_Builder0 = OptionFormatter.builder();
        BiFunction<OptionFormatter, Boolean, String> biFunction0 = (BiFunction<OptionFormatter, Boolean, String>) mock(BiFunction.class, new ViolatedAssumptionAnswer());
        OptionFormatter.Builder optionFormatter_Builder1 = optionFormatter_Builder0.setSyntaxFormatFunction(biFunction0);
        assertSame(optionFormatter_Builder0, optionFormatter_Builder1);
    }
}
