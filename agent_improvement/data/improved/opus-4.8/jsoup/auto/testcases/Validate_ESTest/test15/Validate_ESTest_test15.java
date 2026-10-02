package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test15 extends Validate_ESTest_scaffolding {

    /**
     * When the validated value is true, {@link Validate#isTrue(boolean, String)}
     * accepts it and returns normally without throwing a ValidationException.
     * The supplied message is only used if validation fails, so it has no effect here.
     */
    @Test(timeout = 4000)
    public void isTrueWithTrueValuePasses() throws Throwable {
        Validate.isTrue(true, "W\"N+(;C");
    }
}
