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
public class CSVRecord_ESTest_test01 extends CSVRecord_ESTest_scaffolding {

    private static final String DUPLICATE_HEADER_AND_VALUE = "*;Ax}g<";
    private static final long SENTINEL_POSITION = -1013L;

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        String[] duplicateHeaders = new String[2];
        duplicateHeaders[0] = DUPLICATE_HEADER_AND_VALUE;
        duplicateHeaders[1] = DUPLICATE_HEADER_AND_VALUE;

        CSVFormat.Builder builderWithHeaders = formatBuilder.setHeader(duplicateHeaders);
        CSVFormat format = builderWithHeaders.get();
        CSVParser parser = CSVParser.parse(DUPLICATE_HEADER_AND_VALUE, format);
        CSVRecord record = new CSVRecord(
                parser,
                duplicateHeaders,
                DUPLICATE_HEADER_AND_VALUE,
                SENTINEL_POSITION,
                SENTINEL_POSITION,
                SENTINEL_POSITION);

        Map<String, String> valuesByHeader = record.toMap();

        assertEquals(SENTINEL_POSITION, record.getCharacterPosition());
        assertEquals(1, valuesByHeader.size());
        assertEquals(SENTINEL_POSITION, record.getRecordNumber());
        assertEquals(SENTINEL_POSITION, record.getBytePosition());
    }
}
