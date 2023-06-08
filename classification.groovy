def addClassification(module, propertyFile) {
    if (fileExists(propertyFile)) {
        def props = readProperties interpolate: true, file: propertyFile
        cucumber fileIncludePattern: "${module}/target/cucumber-reports/*.json",
                sortingMethod: 'ALPHABETICAL',
                classifications: [
                        ['key'  : 'Device',
                         'value': props.deviceName
                        ],
                        ['key'  : 'Platform',
                         'value': props.platformName
                        ],
                        ['key'  : 'Server',
                         'value': props.server
                        ],
                        ['key'  : 'App',
                         'value': props.app
                        ]
                ]
    }
}

return this
