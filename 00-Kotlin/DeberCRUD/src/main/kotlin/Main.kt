import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val autorService = AutorService()
    val libroService = LibroService()
    val autoresFolder = "autores"
    val librosFolder = "libros"

    // Crear las carpetas si no existen
    File(autoresFolder).mkdirs()
    File(librosFolder).mkdirs()

    // Cargar datos desde archivos al iniciar el programa
    autorService.loadAutoresFromFile(autoresFolder)
    libroService.loadLibrosFromFile(librosFolder)

    val scanner = Scanner(System.`in`)

    var exitProgram = false

    while (!exitProgram) {
        println("--- Menu principal ---")
        println("1. Gestión de autores")
        println("2. Gestión de libros")
        println("3. Salir")
        print("Seleccione una opción: ")
        when (scanner.nextInt()) {
            1 -> {
                var exitAutoresMenu = false
                while (!exitAutoresMenu) {
                    println("\n-- Menú Autores --")
                    println("1. Crear autor")
                    println("2. Ver autores")
                    println("3. Editar autor")
                    println("4. Eliminar autor")
                    println("5. Salir")
                    print("Seleccione una opción: ")
                    when (scanner.nextInt()) {
                        1 -> {
                            println("\n-- Crear Autor --")
                            val autor = inputAutor(scanner)
                            autorService.createAutor(autor)
                            println("Autor creado exitosamente.")
                        }
                        2 -> {
                            println("\n-- Ver Autores --")
                            autorService.readAllAutores().forEach { println(it) }
                        }
                        3 -> {
                            println("\n-- Editar Autor --")
                            println("Ingrese el ID del autor a editar: ")
                            val id = scanner.nextInt()
                            val autor = autorService.readAutorById(id)
                            if (autor != null) {
                                val updatedAutor = inputAutor(scanner)
                                updatedAutor.id = id
                                autorService.updateAutor(updatedAutor)
                                println("Autor actualizado exitosamente.")
                            } else {
                                println("No se encontró un autor con ese ID.")
                            }
                        }
                        4 -> {
                            println("\n-- Eliminar Autor --")
                            println("Ingrese el ID del autor a eliminar: ")
                            val id = scanner.nextInt()
                            val autor = autorService.readAutorById(id)
                            if (autor != null) {
                                autorService.deleteAutor(autor)
                                println("Autor eliminado exitosamente.")
                            } else {
                                println("No se encontró un autor con ese ID.")
                            }
                        }
                        5 -> exitAutoresMenu = true
                        else -> println("Opción no válida.")
                    }
                }
            }
            2 -> {
                var exitLibrosMenu = false
                while (!exitLibrosMenu) {
                    println("\n-- Menú Libros --")
                    println("1. Crear libro")
                    println("2. Ver libros")
                    println("3. Editar libro")
                    println("4. Eliminar libro")
                    println("5. Salir")
                    print("Seleccione una opción: ")
                    when (scanner.nextInt()) {
                        1 -> {
                            println("\n-- Crear Libro --")
                            val libro = inputLibro(scanner)
                            libroService.createLibro(libro)
                            println("Libro creado exitosamente.")
                        }
                        2 -> {
                            println("\n-- Ver Libros --")
                            libroService.readAllLibros().forEach { println(it) }
                        }
                        3 -> {
                            println("\n-- Editar Libro --")
                            println("Ingrese el ID del autor al que pertenece el libro: ")
                            val autorId = scanner.nextInt()
                            println("Ingrese el ISBN del libro: ")
                            val isbn = scanner.next()
                            val libro = libroService.readLibroById(autorId, isbn)
                            if (libro != null) {
                                val updatedLibro = inputLibro(scanner)
                                updatedLibro.autorId = autorId
                                updatedLibro.isbn = isbn
                                libroService.updateLibro(updatedLibro)
                                println("Libro actualizado exitosamente.")
                            } else {
                                println("No se encontró un libro con esos datos.")
                            }
                        }
                        4 -> {
                            println("\n-- Eliminar Libro --")
                            println("Ingrese el ID del autor al que pertenece el libro: ")
                            val autorId = scanner.nextInt()
                            println("Ingrese el ISBN del libro: ")
                            val isbn = scanner.next()
                            val libro = libroService.readLibroById(autorId, isbn)
                            if (libro != null) {
                                libroService.deleteLibro(libro)
                                println("Libro eliminado exitosamente.")
                            } else {
                                println("No se encontró un libro con esos datos.")
                            }
                        }
                        5 -> exitLibrosMenu = true
                        else -> println("Opción no válida.")
                    }
                }
            }
            3 -> {
                // Guardar datos en archivos al salir del programa
                autorService.saveAutoresToFile(autoresFolder)
                libroService.saveLibrosToFile(librosFolder)
                exitProgram = true
                println("Saliendo del programa...")
            }
            else -> println("Opción no válida.")
        }
    }

    scanner.close()
}

fun inputAutor(scanner: Scanner): Autor {
    println("Creación de nuevo autor:")
    print("ID del autor (automático): ")
    val id = scanner.nextInt() // No es necesario, pero se solicita para mantener el formato
    print("Nombre del autor: ")
    val nombre = scanner.next()
    print("Fecha de nacimiento (YYYY-MM-DD): ")
    val fechaNacimiento = scanner.next()
    print("Nacionalidad: ")
    val nacionalidad = scanner.next()
    print("Número de libros publicados: ")
    val numLibrosPublicados = scanner.nextInt()
    print("¿Ganó premio literario? (true/false): ")
    val premioLiterario = scanner.nextBoolean()
    return Autor(id, nombre, fechaNacimiento, nacionalidad, numLibrosPublicados, premioLiterario)
}

fun inputLibro(scanner: Scanner): Libro {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    print("ID del autor al que pertenece el libro: ")
    val autorId = scanner.nextInt()
    print("Título del libro: ")
    val titulo = scanner.next()
    print("ISBN del libro: ")
    val isbn = scanner.next()

    var fechaPublicacion: Date? = null
    var fechaValida = false
    while (!fechaValida) {
        print("Fecha de publicación (formato YYYY-MM-DD): ")
        val fechaPublicacionStr = scanner.next()

        try {
            fechaPublicacion = dateFormat.parse(fechaPublicacionStr)
            fechaValida = true
        } catch (e: Exception) {
            println("Error al analizar la fecha. Asegúrate de seguir el formato YYYY-MM-DD.")
        }
    }

    print("Precio del libro: ")
    val precio = scanner.nextDouble()

    return Libro(autorId, titulo, isbn, fechaPublicacion!!, precio)
}
