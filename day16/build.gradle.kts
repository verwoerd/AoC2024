plugins {
  id("aoc.problem")
}
project.application.mainClass.set("MainKt")

tasks.test {
  maxHeapSize = "32g"
}
tasks {
  run.configure {
    maxHeapSize = "32g"
  }
}
