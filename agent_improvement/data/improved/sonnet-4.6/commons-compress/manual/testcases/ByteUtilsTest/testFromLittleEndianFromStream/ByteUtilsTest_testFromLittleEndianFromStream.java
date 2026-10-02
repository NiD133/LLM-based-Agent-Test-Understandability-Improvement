package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromStream {

    @Test
    void testFromLittleEndianFromStream() throws IOException {
        // Stream contains [0x02, 0x03, 0x04, 0x05]; only the first 3 bytes are consumed.
        // Little-endian decoding of [0x02, 0x03, 0x04]:
        //   byte[0] is least-significant:  0x02 * 256^0 =      2
        //   byte[1] is next:               0x03 * 256^1 =    768
        //   byte[2] is most-significant:   0x04 * 256^2 = 262144
        //   total                                        = 262914
        final long expectedValue = 2 + 3 * 256 + 4 * 256 * 256;

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[] { 2, 3, 4, 5 });
        assertEquals(expectedValue, fromLittleEndian(inputStream, 3));
    }
}
