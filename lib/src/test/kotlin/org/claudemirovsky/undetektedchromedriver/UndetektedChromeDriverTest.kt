/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package org.claudemirovsky.undetektedchromedriver

import kotlin.test.Test
import java.time.Duration
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

// https://github.com/ultrafunkamsterdam/undetected-chromedriver/blob/master/undetected_chromedriver/tests/quick_test_cf.cmd
class UndetektedChromeDriverTest {
    @Test fun passCloudflareIUAM() {
        val driver = UndetektedChromeDriver()
        driver.get("https://nowsecure.nl/#relax")
        WebDriverWait(driver, Duration.ofSeconds(15))
            .until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".hystericalbg")
                )
            )
        println("========== IT WORKS!!! ==========")
        driver.close()
    }
}
