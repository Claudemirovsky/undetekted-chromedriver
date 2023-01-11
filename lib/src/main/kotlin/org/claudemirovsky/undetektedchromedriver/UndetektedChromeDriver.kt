package org.claudemirovsky.undetektedchromedriver

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions

class UndetektedChromeDriver: ChromeDriver {

    constructor(): this(
        UndetektedChromeDriverService.createDefaultService(), 
        UndetektedChromeOptions()
    )
    constructor(service: ChromeDriverService): this(service, ChromeOptions())
    constructor(options: ChromeOptions): this(
        UndetektedChromeDriverService.createServiceWithConfig(options as UndetektedChromeOptions),
        options
    )
    constructor(service: ChromeDriverService, options: ChromeOptions) : super(
        service as UndetektedChromeDriverService,
        options as UndetektedChromeOptions
    )

    private val evasionScripts by lazy {
        arrayOf(
            "/undetekted-chromedriver-evasions/chrome.global.js",
            "/undetekted-chromedriver-evasions/emulate.touch.js",
            "/undetekted-chromedriver-evasions/navigator.cdc.js",
            "/undetekted-chromedriver-evasions/navigator.permissions.js",
            "/undetekted-chromedriver-evasions/navigator.webdriver.js",
        ).map { javaClass.getResource(it)!!.readText() }
    }

    private fun applyEvasions() {
        val userAgent = executeScript("return navigator.userAgent") as String
        executeCdpCommand(
            "Network.setUserAgentOverride", 
            mapOf("userAgent" to userAgent.replace("Headless", ""))
        )
        
        evasionScripts.forEach { script ->
            executeCdpCommand(
                "Page.addScriptToEvaluateOnNewDocument",
                mapOf("source" to script)
            )
        }
    }

    override fun get(url: String) {
        applyEvasions()
        super.get(url)
    }

}
