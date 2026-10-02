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
public class CSVRecord_ESTest_test15 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that looking up a column by an empty string name throws
     * IllegalArgumentException when the header only contains "*;Ax}g<".
     */
    @Test(timeout = 4000)
    public void test_getByName_emptyName_throwsIllegalArgumentException() throws Throwable {
        String headerName = "*;Ax}g<";
        String[] headerNames = new String[] { headerName, headerName };

        CSVFormat format = CSVFormat.Builder.create()
                .setHeader(headerNames)
                .get();

        CSVParser parser = CSVParser.parse(headerName, format);

        long recordNumber = -1013L;
        long characterPosition = -1013L;
        long bytePosition = -1013L;
        String comment = "*;Ax}g<";
        CSVRecord record = new CSVRecord(parser, headerNames, comment, recordNumber, characterPosition, bytePosition);

        // Attempting to get a value by an empty name ("") should fail because
        // no header with that name exists — only "*;Ax}g<" is mapped.
        try {
            record.get("");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Mapping for  not found, expected one of [*;Ax}g<]
            //
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
