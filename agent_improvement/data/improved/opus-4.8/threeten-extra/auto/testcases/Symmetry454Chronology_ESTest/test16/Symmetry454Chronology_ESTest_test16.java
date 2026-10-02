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
public class Symmetry454Chronology_ESTest_test16 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Symmetry454Chronology only recognises {@link java.time.chrono.IsoEra} values.
     * Passing a null era to {@code date(Era, int, int, int)} causes the chronology to
     * reject it with a ClassCastException ("Invalid era: null") while resolving the
     * proleptic year, before the year/month/day arguments are ever validated.
     */
    @Test(timeout = 4000)
    public void dateWithNullEraThrowsClassCastException() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();
        Era nullEra = null;
        int yearOfEra = -2134851390;
        int month = -2134851390;
        int dayOfMonth = -2134851390;

        try {
            chronology.date(nullEra, yearOfEra, month, dayOfMonth);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            // Invalid era: null
            verifyException("org.threeten.extra.chrono.Symmetry454Chronology", e);
        }
    }
}
