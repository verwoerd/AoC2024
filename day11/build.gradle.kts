plugins {
  id("aoc.problem")
}
project.application.mainClass.set("MainKt")

tasks {
  test {
    maxHeapSize = "24g"
  }
}
