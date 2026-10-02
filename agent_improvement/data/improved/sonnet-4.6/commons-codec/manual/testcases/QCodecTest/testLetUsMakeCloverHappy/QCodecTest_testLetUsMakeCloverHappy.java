package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QCodecTest_testLetUsMakeCloverHappy {

    @Test
    void testLetUsMakeCloverHappy() throws Exception {
        final QCodec qcodec = new QCodec();

        // Verify that encodeBlanks can be toggled on and off
        qcodec.setEncodeBlanks(true);
        assertTrue(qcodec.isEncodeBlanks(), "encodeBlanks should be true after setting it to true");

        qcodec.setEncodeBlanks(false);
        assertFalse(qcodec.isEncodeBlanks(), "encodeBlanks should be false after setting it to false");
    }
}
