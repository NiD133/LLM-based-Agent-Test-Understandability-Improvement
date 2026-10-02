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

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        StringReader csvInput = new StringReader("NsO[}lL&3m");
        CSVFormat csvFormat = CSVFormat.INFORMIX_UNLOAD_CSV;
        CSVParser parser = CSVParser.parse((Reader) csvInput, csvFormat);

        String[] recordValues = new String[2];
        CSVRecord record = new CSVRecord(parser, recordValues, "\u0085", 0L, 0L, 0L);

        int recordSize = record.size();

        assertEquals(2, recordSize);
    }
}
