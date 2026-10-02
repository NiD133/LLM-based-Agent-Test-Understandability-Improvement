package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test04 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // The negative nano adjustment is normalized into the previous TAI second.
        TaiInstant normalizedInstant = TaiInstant.ofTaiSeconds((-3113L), (-1194L));
        TaiInstant zeroNanoInstant = normalizedInstant.withNano(0);

        boolean zeroNanoEqualsOriginal = zeroNanoInstant.equals(normalizedInstant);

        assertEquals((-3114L), zeroNanoInstant.getTaiSeconds());
        assertFalse(normalizedInstant.equals((Object) zeroNanoInstant));
        assertFalse(zeroNanoEqualsOriginal);
        assertEquals((-3114L), normalizedInstant.getTaiSeconds());
        assertEquals(0, zeroNanoInstant.getNano());
        assertEquals(999998806, normalizedInstant.getNano());
    }
}
