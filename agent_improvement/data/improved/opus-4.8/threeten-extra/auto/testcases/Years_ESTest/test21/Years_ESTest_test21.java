package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test21 extends Years_ESTest_scaffolding {

    /**
     * Years.get(TemporalUnit) only supports the YEARS unit. Passing a null unit
     * is unsupported and must raise an UnsupportedTemporalTypeException.
     */
    @Test(timeout = 4000)
    public void get_withNullUnit_throwsUnsupportedTemporalType() throws Throwable {
        Years oneYear = Years.ONE;

        try {
            oneYear.get((TemporalUnit) null);
            fail("Expected UnsupportedTemporalTypeException for a null unit");
        } catch (UnsupportedTemporalTypeException expected) {
            // Message produced by Years.get: "Unsupported unit: null"
            verifyException("org.threeten.extra.Years", expected);
        }
    }
}
