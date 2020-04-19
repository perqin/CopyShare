import jetbrains.buildServer.configs.kotlin.v2018_2.RelativeId
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2018_2.project
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v2018_2.version

version = "2019.2"

project {
    buildType {
        id("build")
        name = "Build"
        vcs {
            root(RelativeId("git"))
        }
        triggers {
            trigger(VcsTrigger().apply {
                branchFilter = ""
            })
        }
        steps {
            gradle {
                useGradleWrapper = true
                gradleWrapperPath = "."
                tasks = ":app:clean"
            }
            gradle {
                useGradleWrapper = true
                gradleWrapperPath = "."
                tasks = ":app:assemble"
            }
            script {
                scriptContent = arrayOf(
                        "APK_FILENAME=\$(cat ./app/build/outputs/apk/debug/output.json | jq -r '.elements[0].outputFile')",
                        "coscmd -c %coscmdConfigPath% upload app/build/outputs/apk/debug/\$APK_FILENAME copy-share/%build.number%/\$APK_FILENAME"
                ).joinToString("\n") { it }
            }
            script {
                scriptContent = arrayOf(
                        "APK_FILENAME=\$(cat ./app/build/outputs/apk/release/output.json | jq -r '.elements[0].outputFile')",
                        "coscmd -c %coscmdConfigPath% upload app/build/outputs/apk/release/\$APK_FILENAME copy-share/%build.number%/\$APK_FILENAME"
                ).joinToString("\n") { it }
            }
        }
    }
}
