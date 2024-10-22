// JSON BASE A MOSTRAR EN FORMULARIO
var baseJSON = {
    "precio": 0.0,
    "unidades": 1,
    "modelo": "XX-000",
    "marca": "NA",
    "detalles": "NA",
    "imagen": "img/default.png"
};

function init() {
    var JsonString = JSON.stringify(baseJSON, null, 2);
    $("#description").val(JsonString);
    

    // SE LISTAN TODOS LOS PRODUCTOS
    listarProductos();
}

// Modifica el archivo listarProductos para que los nombres de los productos sean clicables
function listarProductos() {
    $.ajax({
        url: './backend/product-list.php',
        type: 'GET',
        contentType: 'application/x-www-form-urlencoded',
        success: function (response) {
            let productos = JSON.parse(response);

            if (Object.keys(productos).length > 0) {
                let template = '';

                productos.forEach(producto => {
                    let descripcion = `
                        <li>precio: ${producto.precio}</li>
                        <li>unidades: ${producto.unidades}</li>
                        <li>modelo: ${producto.modelo}</li>
                        <li>marca: ${producto.marca}</li>
                        <li>detalles: ${producto.detalles}</li>
                    `;

                    template += `
                        <tr productId="${producto.id}">
                            <td><a href="#" class="product-name" data-product='${JSON.stringify(producto)}'>${producto.nombre}</a></td>
                            <td><ul>${descripcion}</ul></td>
                            <td>
                                <button class="product-delete btn btn-danger" onclick="eliminarProducto(event)">
                                    Eliminar
                                </button>
                            </td>
                        </tr>
                    `;
                });
                $("#products").html(template);

                // Agrega evento de clic para los nombres de productos
                $(".product-name").click(function (event) {
                    event.preventDefault();
                    let producto = $(this).data('product');
                    console.log(producto.id);

                    //Mio 
                    baseJSON.id = producto.id; // Suponiendo que `producto` es el objeto correcto

                    $('#name').val(producto.nombre);
                    $('#productoId').val(productoId);

                    // Llenar el JSON base con los datos del producto clickeado
                    baseJSON.precio = producto.precio;
                    baseJSON.unidades = producto.unidades;
                    baseJSON.modelo = producto.modelo;
                    baseJSON.marca = producto.marca;
                    baseJSON.detalles = producto.detalles;
                    baseJSON.imagen = producto.imagen || 'img/default.png'; // Asegúrate de tener una imagen por defecto

                    // Actualiza el campo de descripción en el formulario
                    var JsonString = JSON.stringify(baseJSON, null, 2);
                    $("#description").val(JsonString);
                });
            }
        }
    });
}


// FUNCIÓN CALLBACK DE BOTÓN "Buscar"
$(document).ready(function () {
    $('#search').on('input', function () {
        let search = $(this).val();

        if (search.length > 0) {
            $.ajax({
                url: './backend/product-search.php',
                type: 'GET',
                data: { search: search },
                contentType: 'application/x-www-form-urlencoded',
                success: function (response) {
                    let productos = JSON.parse(response);
                    let template = '';

                    if (Object.keys(productos).length > 0) {
                        productos.forEach(producto => {
                            let descripcion = `
                                <li>precio: ${producto.precio}</li>
                                <li>unidades: ${producto.unidades}</li>
                                <li>modelo: ${producto.modelo}</li>
                                <li>marca: ${producto.marca}</li>
                                <li>detalles: ${producto.detalles}</li>
                            `;

                            template += `
                                <tr productId="${producto.id}">
                                    <td>${producto.id}</td>
                                    <td>${producto.nombre}</td>
                                    <td><ul>${descripcion}</ul></td>
                                    <td>
                                        <button class="product-delete btn btn-danger" onclick="eliminarProducto(event)">
                                            Eliminar
                                        </button>
                                    </td>
                                </tr>
                            `;
                        });
                        $("#products").html(template);
                    } else {
                        $("#products").html('<tr><td colspan="4">No se encontraron productos.</td></tr>');
                    }
                }
            });
        } else {
            listarProductos(); // Vuelve a listar todos los productos si no hay búsqueda
        }
    });
});

// FUNCIÓN CALLBACK DE BOTÓN "Agregar Producto"
function agregarProducto(e) {
    e.preventDefault();

    // Obtener el nombre del producto
    var nombre = $('#name').val();

    // Verificar que se haya ingresado un nombre
    if (!nombre) {
        alert("Por favor, ingrese un nombre para el producto.");
        return;
    }

    // Obtener los valores del campo de descripción
    var productoJsonString = $('#description').val();
    var finalJSON = JSON.parse(productoJsonString);
    finalJSON['nombre'] = nombre; // Asignar el nombre ingresado al JSON

    console.log(finalJSON['nombre'])

    // Determinar si se está modificando un producto existente o creando uno nuevo
    var url = baseJSON.id ? './backend/product-edit.php' : './backend/product-add.php'; // Cambia la URL según sea necesario
    console.log(url);

    var method = baseJSON.id ? 'PUT' : 'POST'; // Usa PUT para actualizar y POST para crear

    // Enviar datos al servidor
    $.ajax({
        url: url,
        type: method,
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(finalJSON, null, 2), // Convertir el JSON a string
        success: function (response) {
            let respuesta = JSON.parse(response);

            if (respuesta.status === 'success') {
                // Alerta de éxito
                alert(baseJSON.id ? "Se ha modificado el producto." : "Se ha agregado el producto.");

                // Restablecer el campo de descripción a los valores por defecto
                $("#description").val(JSON.stringify(baseJSON, null, 2));
                $('#name').val(''); // También puedes limpiar el campo del nombre si lo deseas
            } else {
                // Alerta de error
                alert("Este nombre ya existe.");
            }

            let template_bar = `
                <li style="list-style: none;">status: ${respuesta.status}</li>
                <li style="list-style: none;">message: ${respuesta.message}</li>
            `;

            $("#product-result").addClass("card my-4 d-block");
            $("#container").html(template_bar);
            listarProductos(); // Actualizar la lista de productos
        },
        error: function (xhr, status, error) {
            // Manejo de errores de la petición
            alert("Error en la petición: " + error);
        }
    });
}

// FUNCIÓN CALLBACK DE BOTÓN "Eliminar"
function eliminarProducto(event) {
    if (confirm("De verdad deseas eliminar el Producto")) {
        var id = $(event.target).closest("tr").attr("productId");

        $.ajax({
            url: './backend/product-delete.php',
            type: 'GET',
            data: { id: id },
            contentType: 'application/x-www-form-urlencoded',
            success: function (response) {
                let respuesta = JSON.parse(response);
                alert("Eliminacion Correcta");
                let template_bar = `
                    <li style="list-style: none;">status: ${respuesta.status}</li>
                    <li style="list-style: none;">message: ${respuesta.message}</li>
                `;

                $("#product-result").addClass("card my-4 d-block");
                $("#container").html(template_bar);
                listarProductos();
            }
        });
    }
}