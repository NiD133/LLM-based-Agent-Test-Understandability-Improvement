public class name.

I found the writer only exposes `getEncoding()` after it has closed and detected the charset, so I’m keeping the close-before-assert pattern intact. Now I’m creating only the improved test file in the working directory.

The improved test is in place. Per the required skill, I’m running only `bash compile.sh` from the working directory now and will repair only if javac reports an error.

Implemented the refactored `XmlStreamWriterTest_testUTF8Encoding.java` in the working directory and verified it with `bash compile.sh`.

`COMPILE_OK`