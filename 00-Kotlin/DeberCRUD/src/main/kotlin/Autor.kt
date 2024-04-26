import java.io.File

data class Autor(
    var id: Int,
    val nombre: String,
    val fechaNacimiento: String,
    val nacionalidad: String,
    val numLibrosPublicados: Int,
    val premioLiterario: Boolean
)

class AutorService {
    private val autores = mutableListOf<Autor>()

    fun createAutor(autor: Autor) {
        autores.add(autor)
    }

    fun readAllAutores(): List<Autor> {
        return autores
    }

    fun readAutorById(id: Int): Autor? {
        return autores.find { it.id == id }
    }

    fun updateAutor(autor: Autor) {
        val index = autores.indexOfFirst { it.id == autor.id }
        if (index != -1) {
            autores[index] = autor
        }
    }

    fun deleteAutor(autor: Autor) {
        autores.remove(autor)
    }

    // Guardar los datos en archivos de texto (.txt) y leerlos posteriormente
    fun saveAutoresToFile(autoresFolder: String) {
        val autoresFile = File(autoresFolder, "autores.txt")
        autoresFile.bufferedWriter().use { out ->
            autores.forEach { autor ->
                out.write("${autor.id},${autor.nombre},${autor.fechaNacimiento},${autor.nacionalidad},${autor.numLibrosPublicados},${autor.premioLiterario}\n")
            }
        }
    }

    fun loadAutoresFromFile(autoresFolder: String): List<Autor> {
        val autoresFile = File(autoresFolder, "autores.txt")
        val newAutores = mutableListOf<Autor>()

        if (!autoresFile.exists()) {
            println("El archivo 'autores.txt' no existe en el directorio '$autoresFolder'.")
            return newAutores
        }

        autoresFile.bufferedReader().use { reader ->
            reader.forEachLine { line ->
                val parts = line.split(",")
                if (parts.size == 6) {
                    val id = parts[0].toInt()
                    val nombre = parts[1]
                    val fechaNacimiento = parts[2]
                    val nacionalidad = parts[3]
                    val numLibrosPublicados = parts[4].toInt()
                    val premioLiterario = parts[5].toBoolean()
                    newAutores.add(Autor(id, nombre, fechaNacimiento, nacionalidad, numLibrosPublicados, premioLiterario))
                } else {
                    println("Error al leer la línea: '$line'. Formato incorrecto.")
                }
            }
        }

        autores.addAll(newAutores)
        return newAutores
    }
}
