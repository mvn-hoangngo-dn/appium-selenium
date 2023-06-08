def sendMessage(module, proFile) {
    def properties = readProperties interpolate: true, file: proFile
    def report = readJSON file: "${module}/target/GitHubReport.json"
    def buildStatus = ''
    def colorCode = ''
    def duration = ''
    def step = ''
    def scenario = ''
    def feature = ''
    def configDescription = ''
    def buildUrl = ''
    def jobName = "- Job Name: ${env.JOB_NAME}\n"
    if (report.features.failedFeature == 0) {
        buildStatus = "success"
        colorCode = '#00FF00'
        duration = "- Duration: ${report.durations.totalDuration}, Features: ${report.features.totalFeatures}, Scenarios: ${report.scenarios.totalScenarios}, Steps: ${report.scenarios.totalScenarios}, Device: ${properties.deviceName}, Platform: ${properties.platformName}, ProjectName: Knavi\n"
    } else {
        buildStatus = "failed"
        colorCode = '#FF0000'
        duration = "- Total duration: ${report.durations.totalDuration}\n"
        step = "- Step: Total: ${report.steps.totalSteps}, Passed: ${report.steps.passedStep}, Failed: ${report.steps.failedStep}, Skipped: ${report.steps.skippedStep}, Pending: ${report.steps.pendingStep}, Undefined: ${report.steps.undefinedStep}\n"
        scenario = "- Scenario: Total: ${report.scenarios.totalScenarios}, Passed: ${report.scenarios.passedScenario}, Failed: ${report.scenarios.failedScenario}\n"
        feature = "- Feature: Total: ${report.features.totalFeatures}, Passed: ${report.features.passedFeature}, Failed: ${report.features.failedFeature}\n"
        configDescription = "- Device: ${properties.deviceName}, Platform: ${properties.platformName.capitalize()}\n- App: ${properties.app}\n- Server: ${properties.server}\n"
    }
    buildUrl = "- Detail: ${env.BUILD_URL}\n"
    slackSend(color: colorCode, message: "*Build ${buildStatus}.*\n" + jobName + duration + feature + scenario + step + configDescription + buildUrl)
}

def cucumberFailed(proFile) {
    def properties = readProperties interpolate: true, file: proFile
    def configDescription = "Device: ${properties.deviceName}, Platform: ${properties.platformName}"
    slackSend(color: "#FF0000", message: "*Build failed.*\n- ${configDescription}\n- Detail: ${env.BUILD_URL}")
}

return this
