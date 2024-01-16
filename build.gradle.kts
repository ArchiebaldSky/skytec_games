import groovy.xml.dom.DOMCategory.attributes

plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testImplementation("com.h2database:h2:2.1.212")
    implementation("org.slf4j:slf4j-api:2.0.11")
    implementation("org.apache.maven.plugins:maven-jar-plugin:3.3.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}