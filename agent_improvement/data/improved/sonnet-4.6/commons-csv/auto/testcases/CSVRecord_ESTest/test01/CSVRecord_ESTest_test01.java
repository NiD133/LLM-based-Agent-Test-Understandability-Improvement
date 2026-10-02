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

    // Arbitrary negative sentinel values used for record/position fields under test
    private static final long SENTINEL = -1013L;

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Build a CSVFormat with two duplicate header names; when mapped, only one entry survives
        String duplicateHeaderName = "*;Ax}g<";
        String[] headersWithDuplicate = new String[] { duplicateHeaderName, duplicateHeaderName };

        CSVFormat format = CSVFormat.Builder.create()
                .setHeader(headersWithDuplicate)
                .get();

        // Parse a CSV string whose content matches the (duplicate) header name
        CSVParser parser = CSVParser.parse(duplicateHeaderName, format);

        // Construct a CSVRecord manually with sentinel values for all numeric positions
        CSVRecord record = new CSVRecord(parser, headersWithDuplicate, duplicateHeaderName,
                SENTINEL /*recordNumber*/, SENTINEL /*characterPosition*/, SENTINEL /*bytePosition*/);

        // toMap() maps header names to values; duplicate headers collapse to a single entry
        Map<String, String> headerToValueMap = record.toMap();

        assertEquals(SENTINEL, record.getCharacterPosition());
        assertEquals(1, headerToValueMap.size());
        assertEquals(SENTINEL, record.getRecordNumber());
        assertEquals(SENTINEL, record.getBytePosition());
    }
}
