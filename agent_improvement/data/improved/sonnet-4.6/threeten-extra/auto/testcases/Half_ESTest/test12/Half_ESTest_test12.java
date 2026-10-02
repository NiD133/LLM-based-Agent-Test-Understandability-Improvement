package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.TemporalField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test12 extends Half_ESTest_scaffolding {

    /**
     * Verifies that calling get() with a null TemporalField throws NullPointerException,
     * as null field arguments are validated via Objects.requireNonNull in the temporal API.
     */
    @Test(timeout = 4000)
    public void test_get_nullField_throwsNullPointerException() throws Throwable {
        Half half = Half.H2;
        try {
            half.get((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("java.util.Objects", e);
        }
    }
}
