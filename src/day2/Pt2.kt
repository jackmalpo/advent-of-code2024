package day2

import java.io.File
import kotlin.math.abs

/**
 * --- Part Two ---
 * The engineers are surprised by the low number of safe reports until they realize they forgot to tell you about the Problem Dampener.
 *
 * The Problem Dampener is a reactor-mounted module that lets the reactor safety systems tolerate a single bad level in what would otherwise be a safe report. It's like the bad level never happened!
 *
 * Now, the same rules apply as before, except if removing a single level from an unsafe report would make it safe, the report instead counts as safe.
 *
 * More of the above example's reports are now safe:
 *
 * 7 6 4 2 1: Safe without removing any level.
 * 1 2 7 8 9: Unsafe regardless of which level is removed.
 * 9 7 6 2 1: Unsafe regardless of which level is removed.
 * 1 3 2 4 5: Safe by removing the second level, 3.
 * 8 6 4 4 1: Safe by removing the third level, 4.
 * 1 3 6 7 9: Safe without removing any level.
 * Thanks to the Problem Dampener, 4 reports are actually safe!
 *
 * Update your analysis by handling situations where the Problem Dampener can remove a single level from unsafe reports. How many reports are now safe?
 *
 * Your puzzle answer was 343.
 */
fun main() {
    val reports = mutableListOf<List<Int>>()

    File("src/day2/input.txt").forEachLine { line ->
        reports.add(line.trim().split("\\s+".toRegex()).map { it.toInt() })
    }

    val safeReports = findSafeReportsAllowSkip(reports)
    println(safeReports)
}

private fun findSafeReportsAllowSkip(reports: List<List<Int>>): Int {
    var safeReports = 0
    lineLoop@ for (line in reports) {

        dropLoop@ for (i in 0..line.lastIndex) {
            // for each line in the report, as long as we fail, try dropping the value at the next index and retry
            // probably a more efficient way to do this 😅
            val droppedLine = line.toMutableList()
            droppedLine.removeAt(i)

            var lastLevel: Int? = null
            var lineDirection: Int? = null
            levelLoop@ for (level in droppedLine) {
                if (lastLevel == null) {
                    // can't do anything with first value
                    lastLevel = level
                    continue@levelLoop
                }

                // whether we're moving up or down
                val direction: Int = when {
                    lastLevel > level -> -1
                    lastLevel < level -> 1
                    else -> 0
                }

                when {
                    lineDirection == null -> {
                        // set initial direction
                        lineDirection = direction
                    }

                    direction != lineDirection -> {
                        // not safe because changing from + to -
                        continue@dropLoop
                    }
                }


                val difference = lastLevel - level
                if (difference == 0) {
                    // not safe because diff == 0
                    continue@dropLoop
                }
                if (abs(difference) < 1) {
                    // not safe because diff must be at least 1
                    continue@dropLoop
                }
                if (abs(difference) > 3) {
                    // not safe because diff can't be more than 3
                    continue@dropLoop
                }
                lastLevel = level
            }

            // if we get here we're safe
            safeReports += 1

            // quit the drop loops and go to the next line
            continue@lineLoop
        }
    }

    return safeReports
}



