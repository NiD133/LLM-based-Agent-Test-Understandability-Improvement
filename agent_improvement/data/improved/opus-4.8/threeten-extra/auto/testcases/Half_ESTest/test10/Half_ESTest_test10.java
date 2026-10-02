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
public class Half_ESTest_test10 extends Half_ESTest_scaffolding {

    /**
     * Verifies that {@link Half#getLong(TemporalField)} throws a
     * {@code NullPointerException} when the requested field is {@code null}.
     * <p>
     * A null field is neither {@code HALF_OF_YEAR} nor a {@code ChronoField},
     * so the implementation falls through to {@code field.getFrom(this)},
     * dereferencing the null field and triggering the exception inside Half.
     */
    @Test(timeout = 4000)
    public void getLongWithNullFieldThrowsNullPointerException() throws Throwable {
        Half half = Half.H2;

        try {
            half.getLong((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The exception originates from within the Half class and carries no message.
            verifyException("org.threeten.extra.Half", e);
        }
    }
}
