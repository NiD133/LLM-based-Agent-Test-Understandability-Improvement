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
public class OptionFormatter_ESTest_test00 extends OptionFormatter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        OptionFormatter optionFormatter0 = OptionFormatter.from((Option) null);
        String string0 = optionFormatter0.toOptional("-");
        assertEquals("[-]", string0);
    }
}
