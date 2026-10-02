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
public class OptionFormatter_ESTest_test19 extends OptionFormatter_ESTest_scaffolding {

    /**
     * Verifies that calling getSince() on an OptionFormatter created with a null Option
     * throws a NullPointerException, because getSince() delegates to the underlying
     * Option which has not been initialized.
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        OptionFormatter formatter = OptionFormatter.from((Option) null);
        try {
            formatter.getSince();
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.cli.help.OptionFormatter", e);
        }
    }
}
