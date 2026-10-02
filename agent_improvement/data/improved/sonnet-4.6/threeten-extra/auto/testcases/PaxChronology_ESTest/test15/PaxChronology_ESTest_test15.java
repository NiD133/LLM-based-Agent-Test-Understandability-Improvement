package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalField;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test15 extends PaxChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_resolveDate_withEmptyFieldMap_returnsNull() throws Throwable {
        // An empty field map provides no date components, so resolveDate cannot construct
        // a PaxDate and should return null regardless of resolver style.
        HashMap<TemporalField, Long> emptyFields = new HashMap<TemporalField, Long>();
        PaxDate result = PaxChronology.INSTANCE.resolveDate(emptyFields, ResolverStyle.STRICT);
        assertNull(result);
    }
}
