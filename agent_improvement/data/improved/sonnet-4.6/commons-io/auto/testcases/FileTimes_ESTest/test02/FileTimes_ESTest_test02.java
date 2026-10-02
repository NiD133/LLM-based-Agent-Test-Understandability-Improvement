package org.apache.commons.io.file.attribute;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Date;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileTimes_ESTest_test02 extends FileTimes_ESTest_scaffolding {

    // Millisecond timestamp so far before the NTFS epoch that NTFS conversion underflows to Long.MIN_VALUE
    private static final long EXTREME_PAST_MILLIS = -116444736000000000L;

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        MockDate extremePastDate = new MockDate(EXTREME_PAST_MILLIS);

        long ntfsTime = FileTimes.toNtfsTime((Date) extremePastDate);

        assertEquals(Long.MIN_VALUE, ntfsTime);
    }
}
