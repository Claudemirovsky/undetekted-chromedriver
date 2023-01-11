package org.claudemirovsky.undetektedchromedriver

import java.io.File
import java.nio.file.Files

object Patcher {

    private fun getTempDriver(): File {
        return Files.createTempFile("undetekted-chromedriver-", ".exe").toFile().also {
            it.deleteOnExit()
            it.setExecutable(true, true)
        }
    }

    private fun randomCdc(): String {
        val randomStr = (1..26).map { ('a'..'z').random() }.joinToString("")
        return buildString {
            append(randomStr)
            set(2, get(0))
            set(3, '_')
            replace(20, 22, substring(20, 22).uppercase())
        }
    }

    fun patch(driver: File): File {
        val regex = Regex("cdc_.{22}")
        val token = randomCdc()
        val patchedFile = getTempDriver()
        Files.newOutputStream(patchedFile.toPath()).use { output ->
            Files.newInputStream(driver.toPath()).use { input ->
                val buffer = ByteArray(16 * 1024)
                var n: Int
                while (-1 != input.read(buffer).also { n = it }) {
                    val str = String(buffer, Charsets.UTF_8)
                    if ("cdc_" in str) {
                        val newStr = str.replace(regex, token)
                        output.write(newStr.toByteArray(), 0, n)
                    } else {
                        output.write(buffer, 0, n)
                    }
                }
            }
        }
        return patchedFile
    }
}
