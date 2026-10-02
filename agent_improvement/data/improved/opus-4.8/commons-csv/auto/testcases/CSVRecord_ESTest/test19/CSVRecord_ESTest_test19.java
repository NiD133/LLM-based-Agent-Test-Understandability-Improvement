package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.Reader;
import java.io.StringReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test19 extends CSVRecord_ESTest_scaffolding {

    /**
     * size() should report the number of values held by the record,
     * which equals the length of the values array passed to the constructor.
     * The comment, record number and stream positions do not affect the size.
     */
    @Test(timeout = 4000)
    public void sizeReturnsNumberOfValues() throws Throwable {
        Reader input = new StringReader("NsO[}lL&3m");
        CSVParser parser = CSVParser.parse(input, CSVFormat.INFORMIX_UNLOAD_CSV);

        String[] values = new String[2];
        String comment = "";
        CSVRecord record = new CSVRecord(parser, values, comment, 0L, 0L, 0L);

        assertEquals(2, record.size());
    }
}
