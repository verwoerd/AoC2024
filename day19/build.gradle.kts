plugins {
  id("aoc.problem")
}
project.application.mainClass.set("MainKt")

tasks.run.configure {
  maxHeapSize = "32G"
}
