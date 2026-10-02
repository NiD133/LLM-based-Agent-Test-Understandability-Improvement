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
public class CSVRecord_ESTest_test00 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        final String repeatedHeaderAndValue = "*;Ax}g<";

        final CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        final String[] duplicateHeaders = new String[] {
                repeatedHeaderAndValue,
                repeatedHeaderAndValue,
                repeatedHeaderAndValue
        };
        formatBuilder.setHeader(duplicateHeaders);

        final CSVFormat format = formatBuilder.get();
        final CSVParser parser = CSVParser.parse(repeatedHeaderAndValue, format);
        final CSVRecord record = parser.nextRecord();

        record.toMap();

        assertEquals(1L, record.getRecordNumber());
        assertEquals(1, record.size());
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(0L, record.getBytePosition());
    }
}
