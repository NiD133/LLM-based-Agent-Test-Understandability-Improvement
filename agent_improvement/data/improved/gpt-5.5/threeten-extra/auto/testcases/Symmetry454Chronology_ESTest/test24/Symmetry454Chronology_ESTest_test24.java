package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test24 extends Symmetry454Chronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void dateNowAtMaximumZoneOffsetUsesCommonEra() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();
        ZoneOffset maximumZoneOffset = ZoneOffset.MAX;

        Symmetry454Date currentDate = chronology.INSTANCE.dateNow((ZoneId) maximumZoneOffset);

        assertEquals(IsoEra.CE, currentDate.getEra());
    }
}
