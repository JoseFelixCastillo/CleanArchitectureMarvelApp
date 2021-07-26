
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.androidGradlePlugin)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.detektPlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    configurations.all {
        resolutionStrategy.force("org.objenesis:objenesis:2.6")
    }
}

tasks.register("clean").configure {
    delete("build")
}
