package org.claudemirovsky.undetektedchromedriver

import org.openqa.selenium.chrome.ChromeOptions

class UndetektedChromeOptions: ChromeOptions() {
    init {
        addArguments(
            "--disable-blink-features=AutomationControlled",
            "disable-infobars"
        )
        setExperimentalOption("excludeSwitches", listOf("enable-automation"))
    }

    override fun addArguments(arguments: List<String>): ChromeOptions {
        if (arguments.any { "headless" in it }) {
            val newArgs = arguments.toMutableList().apply {
                add("--window-size=1920,1080")
                add("--start-maximized")
                add("--no-sandbox")
            }
            return super.addArguments(newArgs)
        }
        return super.addArguments(arguments)
    }
}
