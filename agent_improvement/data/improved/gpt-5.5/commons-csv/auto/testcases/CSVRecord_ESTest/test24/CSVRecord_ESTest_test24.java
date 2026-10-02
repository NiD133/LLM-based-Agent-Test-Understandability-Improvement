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
public class CSVRecord_ESTest_test24 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        String[] recordValues = new String[2];
        CSVFormat format = formatBuilder.get();
        CSVParser parser = CSVParser.parse("*;Ax}g<", format);
        CSVRecord record = new CSVRecord(parser, recordValues, "*;Ax}g<", (-1013L), (-1013L), (-1013L));

        record.toMap();

        assertEquals((-1013L), record.getRecordNumber());
        assertEquals((-1013L), record.getBytePosition());
        assertEquals((-1013L), record.getCharacterPosition());
        assertEquals(2, record.size());
    }
}
