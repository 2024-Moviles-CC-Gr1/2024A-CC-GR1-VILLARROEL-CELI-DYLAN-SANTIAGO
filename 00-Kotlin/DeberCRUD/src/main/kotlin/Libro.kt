import java.io.File
import java.text.SimpleDateFormat
import java.util.*

data class Libro(
    var autorId: Int,
    val titulo: String,
    var isbn: String,
    val fechaPublicacion: Date,
    val precio: Double
)

class LibroService {
    private val libros = mutableListOf<Libro>()

    fun createLibro(libro: Libro) {
        libros.add(libro)
    }

    fun readLibrosByAutorId(autorId: Int): List<Libro> {
        return libros.filter { it.autorId == autorId }
    }

    fun readAllLibros(): List<Libro> {
        return libros
    }

    fun readLibroById(autorId: Int, isbn: String): Libro? {
        return libros.find { it.autorId == autorId && it.isbn == isbn }
    }

    fun updateLibro(libro: Libro) {
        val index = libros.indexOfFirst { it.autorId == libro.autorId && it.isbn == libro.isbn }
        if (index != -1) {
            libros[index] = libro
        }
    }

    fun deleteLibro(libro: Libro) {
        libros.remove(libro)
    }

    fun saveLibrosToFile(librosFolder: String) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val librosFile = File(librosFolder, "libros.txt")
        librosFile.bufferedWriter().use { out ->
            libros.forEach { libro ->
                val fechaPublicacionString = dateFormat.format(libro.fechaPublicacion)
                out.write("${libro.autorId},${libro.titulo},${libro.isbn},$fechaPublicacionString,${libro.precio}\n")
            }
        }
    }

    fun loadLibrosFromFile(librosFolder: String): List<Libro> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val librosFile = File(librosFolder, "libros.txt")
        val newLibros = mutableListOf<Libro>()

        if (!librosFile.exists()) {
            println("El archivo 'libros.txt' no existe en el directorio '$librosFolder'.")
            return newLibros
        }

        librosFile.bufferedReader().use { reader ->
            reader.readLines().forEach { line ->
                val parts = line.split(",")
                if (parts.size == 5) {
                    val autorId = parts[0].toInt()
                    val titulo = parts[1]
                    val isbn = parts[2]
                    val fechaPublicacionString = parts[3]
                    val fechaPublicacion = dateFormat.parse(fechaPublicacionString)
                    val precio = parts[4].toDouble()
                    newLibros.add(Libro(autorId, titulo, isbn, fechaPublicacion, precio))
                } else {
                    println("Error al leer la línea: '$line'. Formato incorrecto.")
                }
            }
        }

        libros.addAll(newLibros) // Agregar los libros cargados al final de la lista existente
        return newLibros
    }
}
