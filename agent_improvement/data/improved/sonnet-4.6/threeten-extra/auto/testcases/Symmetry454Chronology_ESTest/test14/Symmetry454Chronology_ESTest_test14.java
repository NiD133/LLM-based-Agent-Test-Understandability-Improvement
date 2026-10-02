package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test14 extends Symmetry454Chronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void eraOf_withOutOfRangeValue_throwsDateTimeException() throws Throwable {
        try {
            Symmetry454Chronology.INSTANCE.eraOf(107016);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.chrono.IsoEra", e);
        }
    }
}
