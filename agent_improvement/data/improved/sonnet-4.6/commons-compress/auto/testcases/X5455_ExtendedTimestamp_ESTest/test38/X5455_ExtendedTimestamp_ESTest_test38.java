package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test38 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    // 0x5455 in decimal — the "UT" extra field tag defined by Info-ZIP
    private static final int UT_EXTRA_FIELD_TAG = 21589;

    @Test(timeout = 4000)
    public void test38() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();
        ZipShort headerId = extendedTimestamp.getHeaderId();
        assertEquals("Header ID must be the 'UT' extra field tag (0x5455)", UT_EXTRA_FIELD_TAG, headerId.getValue());
    }
}
