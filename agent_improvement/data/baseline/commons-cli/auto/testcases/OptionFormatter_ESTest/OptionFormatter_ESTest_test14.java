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
public class OptionFormatter_ESTest_test14 extends OptionFormatter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        Option option0 = new Option("arKg", "arKg");
        OptionFormatter optionFormatter0 = OptionFormatter.from(option0);
        OptionFormatter.Builder optionFormatter_Builder0 = new OptionFormatter.Builder(optionFormatter0);
        OptionFormatter optionFormatter1 = optionFormatter_Builder0.get();
        assertNull(optionFormatter1);
    }
}
