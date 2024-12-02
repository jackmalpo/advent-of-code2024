package day1

import java.io.File

/**
 * --- Part Two ---
 * Your analysis only confirmed what everyone feared: the two lists of location IDs are indeed very different.
 *
 * Or are they?
 *
 * The Historians can't agree on which group made the mistakes or how to read most of the Chief's handwriting, but in the commotion you notice an interesting detail: a lot of location IDs appear in both lists! Maybe the other numbers aren't location IDs at all but rather misinterpreted handwriting.
 *
 * This time, you'll need to figure out exactly how often each number from the left list appears in the right list. Calculate a total similarity score by adding up each number in the left list after multiplying it by the number of times that number appears in the right list.
 *
 * Here are the same example lists again:
 *
 * 3   4
 * 4   3
 * 2   5
 * 1   3
 * 3   9
 * 3   3
 * For these example lists, here is the process of finding the similarity score:
 *
 * The first number in the left list is 3. It appears in the right list three times, so the similarity score increases by 3 * 3 = 9.
 * The second number in the left list is 4. It appears in the right list once, so the similarity score increases by 4 * 1 = 4.
 * The third number in the left list is 2. It does not appear in the right list, so the similarity score does not increase (2 * 0 = 0).
 * The fourth number, 1, also does not appear in the right list.
 * The fifth number, 3, appears in the right list three times; the similarity score increases by 9.
 * The last number, 3, appears in the right list three times; the similarity score again increases by 9.
 * So, for these example lists, the similarity score at the end of this process is 31 (9 + 4 + 0 + 0 + 9 + 9).
 *
 * Once again consider your left and right lists. What is their similarity score?
 *
 * Your puzzle answer was 23927637.
 */
fun main() {
    val column1 = mutableListOf<Int>()
    val column2 = mutableListOf<Int>()

    File("src/day1/input.txt").forEachLine { line ->
        val columns = line.trim().split("\\s+".toRegex())
        if (columns.size == 2) {
            column1.add(columns[0].toInt())
            column2.add(columns[1].toInt())
        }
    }

    val sum = findSimilarity(column1, column2)
    println("Similarity = $sum")
}

private fun findSimilarity(column1: List<Int>, column2: List<Int>): Int {
    var similarity = 0
    column1.forEach { value ->
        val count = column2.count { it == value }
        similarity += (value * count)
    }
    return similarity
}

