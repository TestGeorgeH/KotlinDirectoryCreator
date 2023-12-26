package internship_2

import java.nio.file.*

open class FSEntry(var name: String) { 
    open fun getPaths(): Set<String> {
        return emptySet<String>()
    }

}

class FSFile(var content: String? = null, name: String): FSEntry(name) {
 
    override fun getPaths(): Set<String> {
        return setOfNotNull(name)
    }

}


class FSFolder(var content: Set<FSEntry> = emptySet<FSEntry>(), name: String): FSEntry(name) {

    override fun getPaths(): Set<String> {
        var paths = emptySet<String>()
        for (entry in content) {
            val local_paths = entry.getPaths()
            for (lp in local_paths) {
                paths += name + "/" + lp
            }
        }

        paths += name + "/"

        return paths
    }
}

class FSCreator {
    fun create(entryToCreate: FSEntry, destination: String) {
        val paths = entryToCreate.getPaths()
        var dest = destination
        if (!destination.endsWith("/")) {
            dest += "/"
        }

        for (p in paths) {
            val abs_path = dest + p
            try {
                if (abs_path.endsWith("/")) {
                    println(Files.createDirectories(Paths.get(abs_path)))
                } else {
                    val dir = abs_path.slice(IntRange(0, abs_path.lastIndexOf("/")))
                    println(Files.createDirectories(Paths.get(dir))) // Create the necessary dirs first
                    println(Files.createFile(Paths.get(abs_path)))
                }
            } catch (e: FileAlreadyExistsException) {
                println("File already exists, " + e)
            }
        }
    }
}

fun main() {

    println("This is a directory/files creating library.")
    println("You may modify this main function to try it out.")
    println("See the AppTest file or README for usage examples.")


}
