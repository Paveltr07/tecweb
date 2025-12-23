import SwiftUI

struct ResultadoView: View {
    let preguntas: [Pregunta]
    let respuestas: [UUID: String]
    let nombre: String

    var aciertos: Int {
        preguntas.filter { respuestas[$0.id] == $0.correcta }.count
    }

    var puntos: Int {
        aciertos * 2
    }

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 20) {
                Text("Resultado")
                    .font(.largeTitle)
                    .bold()
                    .frame(maxWidth: .infinity, alignment: .center)

                Text("Alumno: \(nombre)")
                    .font(.title2)

                Text("Puntaje final: \(puntos) puntos")
                    .font(.title3)
                    .bold()
                    .padding(.bottom, 20)

                // Imagen condicional
                if puntos >= 6 {
                    Image("fcc")
                        .resizable()
                        .scaledToFit()
                        .frame(height: 300)
                        .frame(maxWidth: .infinity)
                } else {
                    Image("tiste")
                        .resizable()
                        .scaledToFit()
                        .frame(height: 300)
                        .frame(maxWidth: .infinity)
                }

                Divider()

                ForEach(preguntas) { pregunta in
                    let esCorrecta = respuestas[pregunta.id] == pregunta.correcta
                    let colorFondo = esCorrecta ? Color.green.opacity(0.2) : Color.red.opacity(0.2)

                    VStack(alignment: .leading, spacing: 8) {
                        Text("Pregunta: \(pregunta.texto)")
                            .font(.headline)

                        Text("Tu respuesta: \(respuestas[pregunta.id] ?? "No respondida")")
                            .foregroundColor(esCorrecta ? .green : .red)

                        Text("Respuesta correcta: \(pregunta.correcta)")
                            .foregroundColor(.blue)
                    }
                    .padding()
                    .background(colorFondo)
                    .cornerRadius(10)
                    .shadow(radius: 2)
                }

                Spacer()
            }
            .padding()
        }
        .background(Color(.systemGray6))
        .navigationBarBackButtonHidden(true)
        .onAppear {
            enviarResultados()
        }
    }

    func enviarResultados() {
        let respuestasCodificables = respuestas.reduce(into: [String: String]()) { (dict, pair) in
            dict[pair.key.uuidString] = pair.value
        }

        let datos: [String: Any] = [
            "nombre": nombre,
            "respuestas": respuestasCodificables,
            "puntaje": puntos
        ]

        guard let url = URL(string: "http://192.168.1.69:5050/guardar"),
              let jsonData = try? JSONSerialization.data(withJSONObject: datos) else {
            print("Error preparando JSON")
            return
        }

        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpBody = jsonData

        URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                print("Error al enviar: \(error)")
                return
            }
            if let httpResponse = response as? HTTPURLResponse {
                print("Código de respuesta: \(httpResponse.statusCode)")
            }
            if let data = data, let respuesta = try? JSONSerialization.jsonObject(with: data) {
                print("Respuesta del servidor: \(respuesta)")
            }
        }.resume()
    }
}
