package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@Timeout(3)
public class Md5CryptTest_testMd5CryptNullData {

    @Test
    void testMd5CryptRejectsNullData() {
        assertThrows(NullPointerException.class, () -> Md5Crypt.md5Crypt((byte[]) null));
    }
}
