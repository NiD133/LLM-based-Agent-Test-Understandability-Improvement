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
public class CSVRecord_ESTest_test28 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that the position and metadata values passed to the CSVRecord
     * constructor are returned unchanged by the corresponding getters.
     */
    @Test(timeout = 4000)
    public void getCharacterPosition_returnsValueGivenToConstructor() throws Throwable {
        final long recordNumber = 698L;
        final long characterPosition = 698L;
        final long bytePosition = 0L;
        final String comment = "org.apache.commons.io.serialization.ObjectStreamClassPredicate";

        Reader reader = new StringReader("NsO[}lL&3m");
        CSVParser parser = CSVParser.parse(reader, CSVFormat.INFORMIX_UNLOAD_CSV);

        // A record with two (null) values and explicit position metadata.
        String[] values = new String[2];
        CSVRecord record = new CSVRecord(parser, values, comment, recordNumber, characterPosition, bytePosition);

        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(bytePosition, record.getBytePosition());
        assertEquals(recordNumber, record.getRecordNumber());
        assertEquals(2, record.size());
    }
}
