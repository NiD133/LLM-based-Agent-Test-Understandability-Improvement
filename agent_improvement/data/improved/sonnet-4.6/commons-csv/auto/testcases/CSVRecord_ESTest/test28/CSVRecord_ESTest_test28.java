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
public class CSVRecord_ESTest_test28 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test28() throws Throwable {
        StringReader csvInput = new StringReader("NsO[}lL&3m");
        CSVFormat format = CSVFormat.INFORMIX_UNLOAD_CSV;
        CSVParser parser = CSVParser.parse((Reader) csvInput, format);

        String[] values = new String[2];
        String recordComment = "org.apache.commons.io.serialization.ObjectStreamClassPredicate";
        long recordNumber = 698L;
        long characterPosition = 698L;
        long bytePosition = 0L;

        CSVRecord record = new CSVRecord(parser, values, recordComment, recordNumber, characterPosition, bytePosition);

        long actualCharacterPosition = record.getCharacterPosition();

        assertEquals(0L, record.getBytePosition());
        assertEquals(2, record.size());
        assertEquals(698L, record.getRecordNumber());
        assertEquals(698L, actualCharacterPosition);
    }
}
