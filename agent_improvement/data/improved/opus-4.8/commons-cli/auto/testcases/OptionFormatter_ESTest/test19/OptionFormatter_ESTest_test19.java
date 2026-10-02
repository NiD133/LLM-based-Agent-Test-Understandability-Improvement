package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test19 extends OptionFormatter_ESTest_scaffolding {

    /**
     * An OptionFormatter built from a null Option holds no Option to delegate to.
     * Calling getSince() then dereferences that null Option (via option.getSince()),
     * so the call must throw a NullPointerException originating from OptionFormatter.
     */
    @Test(timeout = 4000)
    public void getSinceThrowsNullPointerExceptionWhenOptionIsNull() throws Throwable {
        OptionFormatter formatterWithNullOption = OptionFormatter.from((Option) null);

        try {
            formatterWithNullOption.getSince();
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The NPE has no message and is raised inside OptionFormatter itself.
            verifyException("org.apache.commons.cli.help.OptionFormatter", e);
        }
    }
}
