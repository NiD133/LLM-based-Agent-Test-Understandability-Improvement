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
public class CSVRecord_ESTest_test19 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that CSVRecord.size() returns the number of values in the
     * backing array regardless of the CSV content or comment text.
     *
     * The record is constructed directly with a two-element values array and
     * record/position numbers all set to zero. size() must equal the array length.
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        Reader csvInput = new StringReader("NsO[}lL&3m");
        CSVFormat informixFormat = CSVFormat.INFORMIX_UNLOAD_CSV;
        CSVParser parser = CSVParser.parse(csvInput, informixFormat);

        String[] twoElementValues = new String[2];
        //  is the Unicode NEL (Next Line) character used as the comment text
        String nelComment = "\u0085"; // Unicode NEL (Next Line) character
        CSVRecord record = new CSVRecord(parser, twoElementValues, nelComment, 0L, 0L, 0L);

        int recordSize = record.size();

        assertEquals(2, recordSize);
    }
}
