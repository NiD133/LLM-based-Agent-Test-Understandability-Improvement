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
public class X5455_ExtendedTimestamp_ESTest_test39 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Two extended-timestamp fields whose modify times differ should not be
     * considered equal. One field's modify time is set from a java.util.Date
     * (4 ms after the epoch), the other from a FileTime (2 days after the epoch).
     * Setting the modify time also turns on the "modify time present" flag bit.
     */
    @Test(timeout = 4000)
    public void modifyTimeSetterEnablesFlagAndFieldsWithDifferentTimesAreNotEqual() throws Throwable {
        X5455_ExtendedTimestamp fieldWithDateModifyTime = new X5455_ExtendedTimestamp();
        Date modifyDate = Date.from(MockInstant.ofEpochMilli(4L));
        fieldWithDateModifyTime.setModifyJavaTime(modifyDate);

        X5455_ExtendedTimestamp fieldWithFileTimeModifyTime = new X5455_ExtendedTimestamp();
        FileTime modifyFileTime = FileTime.from(2L, TimeUnit.DAYS);
        fieldWithFileTimeModifyTime.setModifyFileTime(modifyFileTime);

        boolean fieldsAreEqual = fieldWithDateModifyTime.equals(fieldWithFileTimeModifyTime);

        assertTrue(fieldWithDateModifyTime.isBit0_modifyTimePresent());
        assertFalse(fieldsAreEqual);
    }
}
