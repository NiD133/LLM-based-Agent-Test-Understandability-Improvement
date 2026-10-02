package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.Era;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test21 extends PaxChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_dateWithNullEra_throwsClassCastException() throws Throwable {
        PaxChronology chronology = PaxChronology.INSTANCE;
        // null is not a PaxEra instance, so prolepticYear() throws ClassCastException rather than NullPointerException
        try {
            chronology.INSTANCE.date((Era) null, (-70), (-70), (-70));
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            verifyException("org.threeten.extra.chrono.PaxChronology", e);
        }
    }
}
