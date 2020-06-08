/*
 * Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import org.openjdk.Cint;
import org.openjdk.CScope;
import static jdk.incubator.foreign.MemoryAddress.NULL;
import static org.openjdk.Cstring.toCString;
import static org.openjdk.Cstring.toJavaStringRestricted;
import static org.openjdk.jimage_h.*;

public class JImageFile {
    public static void main(String[] args) {
        String javaHome = System.getProperty("java.home");
        try (var scope = new CScope()) {
            var jintResPtr = Cint.allocate(0, scope);
            var moduleFilePath = toCString(javaHome + "/lib/modules", scope);
            var jimageFile = JIMAGE_Open(moduleFilePath, jintResPtr);

            var mod = toJavaStringRestricted(JIMAGE_PackageToModule(jimageFile,
                toCString("java/util", scope)));
            System.out.println(mod);

            // const char* module_name, const char* version, const char* package,
            // const char* name, const char* extension, void* arg

            var visitor = JIMAGE_ResourceIterator$visitor.allocate(
                (jimage, module_name, version, package_name, name, extension, arg) -> {
                   System.out.println("module " + toJavaStringRestricted(module_name));
                   System.out.println("package " + toJavaStringRestricted(package_name));
                   System.out.println("name " + toJavaStringRestricted(name));
                   return 1;
                }, scope);

            JIMAGE_ResourceIterator(jimageFile, visitor, NULL);

            JIMAGE_Close(jimageFile);
        }
    }
}
