package org.claudemirovsky.undetektedchromedriver

import java.io.File
import java.io.IOException
import java.time.Duration
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions

class UndetektedChromeDriverService: ChromeDriverService {

    constructor(
        executable: File,
        port: Int,
        timeout: Duration,
        args: List<String>,
        environment: Map<String, String>
    ) : super(
            executable,
            port,
            timeout,
            args,
            environment
        )

    constructor(
        executable: File,
        port: Int,
        args: List<String>,
        environment: Map<String, String>
    ) : super(
            executable,
            port,
            DEFAULT_TIMEOUT,
            args,
            environment
        )

    class Builder: ChromeDriverService.Builder() {
        override protected fun findDefaultExecutable(): File {
            val original = super.findDefaultExecutable()
            val patched = Patcher.patch(original)
            return patched
        }

        override protected fun createDriverService(
            exe: File,
            port: Int,
            timeout: Duration,
            args: List<String>,
            environment: Map<String, String>
        ): UndetektedChromeDriverService {
            return try {
                UndetektedChromeDriverService(
                    exe, 
                    port, 
                    timeout, 
                    args, 
                    environment
                )
            } catch (e: IOException) {
                throw WebDriverException(e)
            }
        }
    }

    companion object {
        fun createDefaultService() = Builder().build()

        fun createServiceWithConfig(options: ChromeOptions) = Builder()
            .withLogLevel(options.logLevel)
            .build()
    }
}


