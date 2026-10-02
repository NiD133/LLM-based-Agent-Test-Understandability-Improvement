package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.Reader;
import java.io.StringReader;
import java.util.Locale;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test04 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_isSet_returnsFalse_whenIndexExceedsArraySize() throws Throwable {
        // Arrange: create a record with 2 values, record number 1105, positions both 0
        String[] twoValueArray = new String[2];
        CSVRecord record = new CSVRecord((CSVParser) null, twoValueArray, "", 1105L, 0L, 0L);

        // Act: check whether an out-of-bounds index (3174) is considered set
        boolean indexOutOfBoundsIsSet = record.isSet(3174);

        // Assert: the record metadata is as constructed, and the out-of-bounds index is not set
        assertEquals(1105L, record.getRecordNumber());
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(0L, record.getBytePosition());
        assertEquals(2, record.size());
        assertFalse(indexOutOfBoundsIsSet);
    }
}
