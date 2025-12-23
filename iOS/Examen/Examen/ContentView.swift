import SwiftUI

struct Pregunta: Identifiable {
    let id = UUID()
    let texto: String
    let opciones: [String]
    let correcta: String
}

let baseDePreguntas: [Pregunta] = [
    Pregunta(texto: "¿Qué lenguaje se usa principalmente en iOS?", opciones: ["Swift", "Kotlin", "Java"], correcta: "Swift"),
    Pregunta(texto: "¿Qué significa 'HTML'?", opciones: ["Hyper Trainer Markup Language", "Hyper Text Markup Language", "Home Tool Markup Language"], correcta: "Hyper Text Markup Language"),
    Pregunta(texto: "¿Qué es una variable?", opciones: ["Un número fijo", "Una función", "Un espacio para almacenar datos"], correcta: "Un espacio para almacenar datos"),
    Pregunta(texto: "¿Qué lenguaje usa el navegador para interactuar con el usuario?", opciones: ["PHP", "JavaScript", "Python"], correcta: "JavaScript"),
    Pregunta(texto: "¿Qué significa 'CPU'?", opciones: ["Central Process Unit", "Central Processing Unit", "Computer Personal Unit"], correcta: "Central Processing Unit"),
    Pregunta(texto: "¿Cuál es el sistema operativo de una Mac?", opciones: ["Windows", "Linux", "macOS"], correcta: "macOS"),
    Pregunta(texto: "¿Qué símbolo se usa para comentarios en Swift?", opciones: ["//", "##", "--"], correcta: "//"),
    Pregunta(texto: "¿Qué tipo de dato representa texto?", opciones: ["String", "Int", "Bool"], correcta: "String"),
    Pregunta(texto: "¿Qué es un bucle?", opciones: ["Una función matemática", "Una estructura para repetir código", "Un tipo de variable"], correcta: "Una estructura para repetir código"),
    Pregunta(texto: "¿Qué hace un compilador?", opciones: ["Diseña la interfaz", "Traduce el código a lenguaje máquina", "Almacena datos"], correcta: "Traduce el código a lenguaje máquina"),
    Pregunta(texto: "¿Qué extensión tienen los archivos de Swift?", opciones: [".java", ".swift", ".py"], correcta: ".swift"),
    Pregunta(texto: "¿Qué es un 'array'?", opciones: ["Una imagen", "Una función", "Una lista de elementos"], correcta: "Una lista de elementos"),
    Pregunta(texto: "¿Cuál es el operador de asignación?", opciones: ["==", "=", "!="], correcta: "="),
    Pregunta(texto: "¿Qué es una función?", opciones: ["Un número", "Una operación reutilizable", "Una constante"], correcta: "Una operación reutilizable"),
    Pregunta(texto: "¿Qué representa el valor booleano 'true'?", opciones: ["0", "Falso", "Verdadero"], correcta: "Verdadero"),
    Pregunta(texto: "¿Qué componente guarda datos de forma permanente?", opciones: ["RAM", "Disco Duro", "CPU"], correcta: "Disco Duro"),
    Pregunta(texto: "¿Qué lenguaje se utiliza en bases de datos?", opciones: ["CSS", "SQL", "JSON"], correcta: "SQL"),
    Pregunta(texto: "¿Qué hace el comando 'print'?", opciones: ["Crea una variable", "Muestra un mensaje en pantalla", "Elimina datos"], correcta: "Muestra un mensaje en pantalla"),
    Pregunta(texto: "¿Cuál es un sistema de control de versiones?", opciones: ["Git", "Bit", "Fit"], correcta: "Git"),
    Pregunta(texto: "¿Qué significa IDE?", opciones: ["Integrated Development Environment", "Internet Data Editor", "Input Device Extension"], correcta: "Integrated Development Environment"),
    Pregunta(texto: "¿Qué es un bit?", opciones: ["Unidad de temperatura", "Unidad mínima de información", "Un tipo de procesador"], correcta: "Unidad mínima de información"),
    Pregunta(texto: "¿Qué representa '0' en lógica booleana?", opciones: ["Verdadero", "Falso", "Nulo"], correcta: "Falso"),
    Pregunta(texto: "¿Qué significa 'RAM'?", opciones: ["Read All Memory", "Random Access Memory", "Run After Memory"], correcta: "Random Access Memory"),
    Pregunta(texto: "¿Qué es un sistema operativo?", opciones: ["Una app", "Una base de datos", "Software que gestiona el hardware"], correcta: "Software que gestiona el hardware"),
    Pregunta(texto: "¿Qué lenguaje se usa para estilo en páginas web?", opciones: ["HTML", "CSS", "SQL"], correcta: "CSS"),
    Pregunta(texto: "¿Qué significa 'URL'?", opciones: ["Universal Resource Locator", "Uniform Resource Locator", "Unified Request Language"], correcta: "Uniform Resource Locator"),
    Pregunta(texto: "¿Cuál es el valor de 2 elevado a la 3?", opciones: ["6", "8", "9"], correcta: "8"),
    Pregunta(texto: "¿Qué es un IDE?", opciones: ["Editor de imágenes", "Entorno de desarrollo", "Consola de comandos"], correcta: "Entorno de desarrollo"),
    Pregunta(texto: "¿Qué es una constante?", opciones: ["Una variable que no cambia", "Un bucle", "Una clase"], correcta: "Una variable que no cambia"),
    Pregunta(texto: "¿Qué extensión tienen los archivos de Python?", opciones: [".py", ".pt", ".pyt"], correcta: ".py"),
    Pregunta(texto: "¿Qué lenguaje es mejor para inteligencia artificial?", opciones: ["Python", "HTML", "CSS"], correcta: "Python"),
    Pregunta(texto: "¿Qué tipo de software es Word?", opciones: ["Sistema operativo", "Aplicación", "Driver"], correcta: "Aplicación"),
    Pregunta(texto: "¿Qué es un driver?", opciones: ["Controlador de hardware", "Juego", "Idioma de programación"], correcta: "Controlador de hardware"),
    Pregunta(texto: "¿Qué es una clase en programación?", opciones: ["Una estructura de control", "Una plantilla para objetos", "Un tipo de bucle"], correcta: "Una plantilla para objetos"),
    Pregunta(texto: "¿Qué palabra se usa para declarar funciones en Swift?", opciones: ["function", "func", "def"], correcta: "func"),
    Pregunta(texto: "¿Qué operador lógico representa 'y'?", opciones: ["||", "&&", "!="], correcta: "&&"),
    Pregunta(texto: "¿Qué es una IP?", opciones: ["Un sistema operativo", "Una dirección de red", "Un protocolo de archivos"], correcta: "Una dirección de red"),
    Pregunta(texto: "¿Qué lenguaje se usa con frameworks como React?", opciones: ["Java", "JavaScript", "PHP"], correcta: "JavaScript"),
    Pregunta(texto: "¿Qué es el 'frontend'?", opciones: ["La base de datos", "La parte visible de una app", "La lógica del servidor"], correcta: "La parte visible de una app"),
    Pregunta(texto: "¿Qué es el 'backend'?", opciones: ["Diseño gráfico", "Lógica del servidor", "Maquetado HTML"], correcta: "Lógica del servidor"),
    Pregunta(texto: "¿Qué tipo de dato puede ser verdadero o falso?", opciones: ["String", "Int", "Bool"], correcta: "Bool"),
    Pregunta(texto: "¿Para qué sirve el lenguaje SQL?", opciones: ["Diseñar páginas web", "Controlar estilos", "Manipular bases de datos"], correcta: "Manipular bases de datos"),
    Pregunta(texto: "¿Qué estructura se usa para condiciones?", opciones: ["loop", "if", "class"], correcta: "if"),
    Pregunta(texto: "¿Cuál es la salida de 5 % 2?", opciones: ["2", "1", "0"], correcta: "1"),
    Pregunta(texto: "¿Qué significa 'debuggear'?", opciones: ["Optimizar código", "Buscar errores", "Escribir documentación"], correcta: "Buscar errores"),
    Pregunta(texto: "¿Qué es un framework?", opciones: ["Una imagen", "Un conjunto de herramientas", "Una app"], correcta: "Un conjunto de herramientas"),
    Pregunta(texto: "¿Qué tipo de archivo suele contener código JavaScript?", opciones: [".js", ".css", ".json"], correcta: ".js"),
    Pregunta(texto: "¿Qué es un bucle 'for'?", opciones: ["Un condicional", "Un ciclo controlado", "Una función"], correcta: "Un ciclo controlado"),
    Pregunta(texto: "¿Qué hace un servidor?", opciones: ["Procesa peticiones", "Edita imágenes", "Conecta a Bluetooth"], correcta: "Procesa peticiones"),
    Pregunta(texto: "¿Qué herramienta permite subir código a la nube?", opciones: ["Photoshop", "GitHub", "Excel"], correcta: "GitHub"),
    Pregunta(texto: "¿Qué es un string?", opciones: ["Un número", "Una función", "Una cadena de texto"], correcta: "Una cadena de texto"),
    Pregunta(texto: "¿Qué operador se usa para comparar igualdad en Swift?", opciones: ["=", "==", "==="], correcta: "=="),
    Pregunta(texto: "¿Qué es JSON?", opciones: ["Un lenguaje", "Un formato de datos", "Un sistema operativo"], correcta: "Un formato de datos"),
    Pregunta(texto: "¿Qué significa 'null'?", opciones: ["Cadena vacía", "Sin valor", "Cero"], correcta: "Sin valor"),
    Pregunta(texto: "¿Qué es una API?", opciones: ["Base de datos", "Interfaz para programar aplicaciones", "Estilo de CSS"], correcta: "Interfaz para programar aplicaciones"),
    Pregunta(texto: "¿Qué empresa desarrolla Swift?", opciones: ["Microsoft", "Apple", "Google"], correcta: "Apple"),
    Pregunta(texto: "¿Qué hace un 'return'?", opciones: ["Declara una variable", "Finaliza una función y devuelve un valor", "Imprime en consola"], correcta: "Finaliza una función y devuelve un valor"),
    Pregunta(texto: "¿Qué palabra se usa para constantes en Swift?", opciones: ["let", "var", "const"], correcta: "let"),
    Pregunta(texto: "¿Cuál es la base del sistema binario?", opciones: ["10", "2", "16"], correcta: "2"),
    Pregunta(texto: "¿Qué herramienta se usa para diseñar interfaces en iOS?", opciones: ["Storyboard", "Xcode", "Terminal"], correcta: "Storyboard"),
    Pregunta(texto: "¿Qué lenguaje se usa con Xcode para iOS?", opciones: ["Swift", "Java", "Kotlin"], correcta: "Swift"),
    Pregunta(texto: "¿Qué significa 'open source'?", opciones: ["Código cerrado", "Código gratuito", "Código accesible y modificable"], correcta: "Código accesible y modificable"),
    Pregunta(texto: "¿Qué es un repositorio?", opciones: ["Un backup de apps", "Un contenedor de código", "Un plugin"], correcta: "Un contenedor de código"),
    Pregunta(texto: "¿Qué tipo de dato representa 3.14?", opciones: ["Int", "Float", "Bool"], correcta: "Float"),
    Pregunta(texto: "¿Qué es un commit en Git?", opciones: ["Una copia de seguridad", "Una versión guardada", "Un error"], correcta: "Una versión guardada")
]


func obtenerPreguntasCiclicas(cantidad: Int) -> [Pregunta] {
    let total = baseDePreguntas.count
    let inicio = UserDefaults.standard.integer(forKey: "indicePreguntaActual")

    var preguntasSeleccionadas: [Pregunta] = []
    for i in 0..<cantidad {
        let index = (inicio + i) % total
        preguntasSeleccionadas.append(baseDePreguntas[index])
    }

    let nuevoInicio = (inicio + cantidad) % total
    UserDefaults.standard.set(nuevoInicio, forKey: "indicePreguntaActual")

    return preguntasSeleccionadas
}

struct ContentView: View {
    @State private var preguntas: [Pregunta] = obtenerPreguntasCiclicas(cantidad: 5)
    @State private var respuestas: [UUID: String] = [:]
    @State private var navegarResultado = false
    @State private var aciertos: Int = 0
    @State private var nombre: String = ""

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(alignment: .leading, spacing: 20) {
                    Text("Examen de Programación")
                        .font(.largeTitle)
                        .bold()
                        .foregroundColor(.black)
                        .frame(maxWidth: .infinity, alignment: .center)
                        .padding(.bottom, 10)

                    Image("logo")
                        .resizable()
                        .scaledToFit()
                        .frame(height: 200)
                        .frame(maxWidth: .infinity)

                    VStack(alignment: .leading, spacing: 5) {
                        Text("Nombre:")
                            .font(.headline)
                            .foregroundColor(.black)

                        TextField("Escribe tu nombre aquí", text: $nombre)
                            .padding()
                            .background(Color.white)
                            .foregroundColor(.black)
                            .cornerRadius(10)
                            .shadow(color: .gray.opacity(0.2), radius: 4, x: 0, y: 2)
                            .onChange(of: nombre) { newValue in
                                var limpio = newValue

                                // 1. Elimina emojis
                                limpio = limpio.filter { !$0.isEmoji }

                                // 2. Reemplaza múltiples espacios con uno solo
                                while limpio.contains("  ") {
                                    limpio = limpio.replacingOccurrences(of: "  ", with: " ")
                                }

                                // 3. Elimina espacios al inicio (pero deja espacios normales después)
                                if let first = limpio.first, first == " " {
                                    limpio = String(limpio.dropFirst())
                                }

                                // Actualiza si hubo cambios
                                if nombre != limpio {
                                    nombre = limpio
                                }
                            }
                    }
                    .padding(.bottom, 10)

                    ForEach(preguntas) { pregunta in
                        VStack(alignment: .leading, spacing: 10) {
                            Text(pregunta.texto)
                                .font(.headline)
                                .foregroundColor(.black)

                            ForEach(pregunta.opciones, id: \.self) { opcion in
                                Button(action: {
                                    respuestas[pregunta.id] = opcion
                                }) {
                                    HStack {
                                        Image(systemName: respuestas[pregunta.id] == opcion ? "largecircle.fill.circle" : "circle")
                                        Text(opcion)
                                    }
                                    .padding(10)
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                    .background(Color.blue.opacity(0.1))
                                    .cornerRadius(8)
                                    .foregroundColor(.black)
                                }
                            }
                        }
                        .padding()
                        .background(Color.white)
                        .cornerRadius(10)
                        .shadow(color: .gray.opacity(0.2), radius: 5, x: 0, y: 2)
                    }

                    NavigationLink(
                        destination: ResultadoView(
                            preguntas: preguntas,
                            respuestas: respuestas,
                            nombre: nombre
                        ),
                        isActive: $navegarResultado
                    ) {
                        EmptyView()
                    }

                    Button(action: {
                        aciertos = preguntas.filter { respuestas[$0.id] == $0.correcta }.count
                        navegarResultado = true
                    }) {
                        Text("Enviar Respuestas")
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background((respuestas.count == preguntas.count && !nombre.isEmpty) ? Color.red : Color.gray)
                            .foregroundColor(.white)
                            .cornerRadius(12)
                            .font(.headline)
                    }
                    .disabled(respuestas.count < preguntas.count || nombre.isEmpty)
                    .padding(.top, 20)
                }
                .padding()

                Text("Autor Pavel Tamanis Rodriguez\n202058576\nServicio Social FCC 2025")
                    .font(.footnote)
                    .foregroundColor(.black)
                    .opacity(0.3)
                    .multilineTextAlignment(.center)
                    .frame(maxWidth: .infinity)
                    .padding(.top, 40)
                    .padding(.bottom, 20)
            }
            .background(Color(.systemBlue))
            .padding(.bottom, 10)
        }
    }
}

extension Character {
    var isEmoji: Bool {
        unicodeScalars.contains { $0.properties.isEmojiPresentation }
    }
}
