package ca.glong.komodo

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.KotlinMultiplatformAndroidComponentsExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.kotlin.multiplatform.library")
                apply("org.jetbrains.kotlin.plugin.serialization")

//                apply("dev.mokkery")
            }



            extensions.configure<KotlinMultiplatformAndroidComponentsExtension> {
                finalizeDsl {
                    it.minSdk =  libs.findVersion("android-minSdk").get().requiredVersion.toInt()
                    it.compileSdk = libs.findVersion("android-compileSdk").get().requiredVersion.toInt()

                    val moduleName = path.split(":").drop(2).joinToString(".")
                    it.namespace = if(moduleName.isNotEmpty()) "ca.glong.komodo.$moduleName" else "ca.glong.komodo.app"

                }

            }

            extensions.configure<KotlinMultiplatformExtension> {
                applyDefaultHierarchyTemplate()

                iosArm64()
                iosSimulatorArm64()

                sourceSets.apply {
                    commonMain.dependencies {
                        implLib("kotlinx-serialization-json")
                    }
                    commonTest.dependencies {
                        bundle("common-test")
                    }
                }
//                (this as ExtensionAware).extensions.configure<CocoapodsExtension>(::configureKotlinCocoapods)
            }
        }
    }
}
//internal fun Project.configureKotlinCocoapods(
//    extension: CocoapodsExtension
//) = extension.apply {
//    val moduleName = this@configureKotlinCocoapods.path.split(":").drop(1).joinToString("-")
//    summary = "Some description for the Shared Module"
//    homepage = "Link to the Shared Module homepage"
//    version = "1.0" //your cocoapods version
//    ios.deploymentTarget = "14.1" //your iOS deployment target
//    name = moduleName
//    framework {
//        isStatic = true //static or dynamic according to your project
//        baseName = moduleName
//    }
//}

internal fun Project.configureKotlinAndroid(
    extension: LibraryExtension
) = extension.apply {

    //get module name from module path
    val moduleName = path.split(":").drop(2).joinToString(".")
    namespace = if(moduleName.isNotEmpty()) "ca.glong.komodo.$moduleName" else "ca.glong.komodo.app"

    compileSdk = libs.findVersion("android-compileSdk").get().requiredVersion.toInt()
    defaultConfig {
        minSdk = libs.findVersion("android-minSdk").get().requiredVersion.toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
}