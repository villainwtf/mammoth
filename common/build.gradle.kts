dependencies {
    api(libs.adventure.api)
}

tasks.withType<Javadoc> {
    exclude("wtf/villain/mammoth/**/impl")
}