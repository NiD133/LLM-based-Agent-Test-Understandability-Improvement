package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertNull;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalField;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test15 extends PaxChronology_ESTest_scaffolding {

    /**
     * Resolving an empty set of field values yields no date, so resolveDate
     * returns null regardless of the resolver style.
     */
    @Test(timeout = 4000)
    public void resolveDateWithNoFieldsReturnsNull() throws Throwable {
        Map<TemporalField, Long> noFieldValues = new HashMap<TemporalField, Long>();

        PaxDate resolvedDate = PaxChronology.INSTANCE.resolveDate(noFieldValues, ResolverStyle.STRICT);

        assertNull(resolvedDate);
    }
}
