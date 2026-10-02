package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QCodecTest_testNullInput {

    @Test
    @DisplayName("doDecoding and doEncoding return null when given null input")
    void testNullInput() throws Exception {
        final QCodec qcodec = new QCodec();
        assertNull(qcodec.doDecoding(null), "doDecoding(null) should return null");
        assertNull(qcodec.doEncoding(null), "doEncoding(null) should return null");
    }
}
