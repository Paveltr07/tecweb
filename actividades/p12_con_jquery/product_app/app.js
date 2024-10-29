// JSON BASE A MOSTRAR EN FORMULARIO
var baseJSON = {
    "precio": 0.0,
    "unidades": 1,
    "modelo": "XX-000",
    "marca": "NA",
    "detalles": "NA",
    "imagen": "img/default.png"
};

$(document).ready(function () {
    let edit = false;

    // Asignar valores iniciales a los campos del formulario
    $('#price').val(baseJSON.precio);
    $('#units').val(baseJSON.unidades);
    $('#model').val(baseJSON.modelo);
    $('#brand').val(baseJSON.marca);
    $('#details').val(baseJSON.detalles);
    $('#image').val(baseJSON.imagen);

    $('#product-result').hide();
    listarProductos();

    function listarProductos() {
        $.ajax({
            url: './backend/product-list.php',
            type: 'GET',
            success: function (response) {
                const productos = JSON.parse(response);
                
                if (Object.keys(productos).length > 0) {
                    let template = '';

                    productos.forEach(producto => {
                        let descripcion = '';
                        descripcion += '<li>precio: ' + producto.precio + '</li>';
                        descripcion += '<li>unidades: ' + producto.unidades + '</li>';
                        descripcion += '<li>modelo: ' + producto.modelo + '</li>';
                        descripcion += '<li>marca: ' + producto.marca + '</li>';
                        descripcion += '<li>detalles: ' + producto.detalles + '</li>';

                        template += `
                            <tr productId="${producto.id}">
                                <td>${producto.id}</td>
                                <td><a href="#" class="product-item">${producto.nombre}</a></td>
                                <td><ul>${descripcion}</ul></td>
                                <td>
                                    <button class="product-delete btn btn-danger" onclick="eliminarProducto()">
                                        Eliminar
                                    </button>
                                </td>
                            </tr>
                        `;
                    });
                    $('#products').html(template);
                }
            }
        });
    }

    $('#search').keyup(function () {
        if ($('#search').val()) {
            let search = $('#search').val();
            $.ajax({
                url: './backend/product-search.php?search=' + search,
                type: 'GET',
                success: function (response) {
                    if (!response.error) {
                        const productos = JSON.parse(response);
                        
                        if (Object.keys(productos).length > 0) {
                            let template = '';
                            let template_bar = '';

                            productos.forEach(producto => {
                                let descripcion = '';
                                descripcion += '<li>precio: ' + producto.precio + '</li>';
                                descripcion += '<li>unidades: ' + producto.unidades + '</li>';
                                descripcion += '<li>modelo: ' + producto.modelo + '</li>';
                                descripcion += '<li>marca: ' + producto.marca + '</li>';
                                descripcion += '<li>detalles: ' + producto.detalles + '</li>';

                                template += `
                                    <tr productId="${producto.id}">
                                        <td>${producto.id}</td>
                                        <td><a href="#" class="product-item">${producto.nombre}</a></td>
                                        <td><ul>${descripcion}</ul></td>
                                        <td>
                                            <button class="product-delete btn btn-danger">
                                                Eliminar
                                            </button>
                                        </td>
                                    </tr>
                                `;

                                template_bar += `
                                    <li>${producto.nombre}</li>
                                `;
                            });
                            $('#product-result').show();
                            $('#container').html(template_bar);
                            $('#products').html(template);
                        }
                    }
                }
            });
        } else {
            $('#product-result').hide();
        }
    });

    // Función para mostrar estado de validación
    function mostrarEstado(elemento, mensaje, estado) {
        let estadoBarra = $(elemento).next('.status-bar');
        if (!estadoBarra.length) {
            $(elemento).after(`<div class="status-bar"></div>`);
            estadoBarra = $(elemento).next('.status-bar');
        }
        estadoBarra.text(mensaje);
        estadoBarra.css('color', estado ? 'green' : 'red');
    }

    // Validar campos cuando cambie el foco (blur)
    $('#name, #price, #units, #model, #brand, #details').on('blur', function () {
        if ($(this).val() === '') {
            mostrarEstado(this, 'Este campo es obligatorio', false);
        } else {
            mostrarEstado(this, 'Campo válido', true);
        }
    });

        // Validación asíncrona del nombre del producto
    $('#name').on('input', function () {
        let nombreProducto = $(this).val();
        if (nombreProducto !== '') {
            $.ajax({
                url: './backend/product-list.php', // Usamos el mismo archivo
                type: 'POST',
                data: { nombre: nombreProducto },
                success: function (response) {
                    let resultado = JSON.parse(response);
                    if (resultado.exists) {
                        mostrarEstado('#name', 'El nombre del producto ya existe', false);
                    } else {
                        mostrarEstado('#name', 'Nombre disponible', true);
                    }
                },
                error: function () {
                    mostrarEstado('#name', 'Error al validar el nombre', false);
                }
            });
        } else {
            mostrarEstado('#name', '', true); // Restablece el estado si está vacío
        }
    });

    // Validar antes de enviar el formulario
    $('#product-form').submit(e => {
        e.preventDefault();

        // Verificar si hay campos vacíos
        let camposVacios = false;
        $('#name, #price, #units, #model, #brand, #details').each(function () {
            if ($(this).val() === '') {
                mostrarEstado(this, 'Este campo es obligatorio', false);
                camposVacios = true;
            }
        });

        if (camposVacios) {
            alert('Por favor, completa todos los campos obligatorios.');
            return;
        }

        // Recoger los datos del formulario
        let postData = {
            nombre: $('#name').val(),
            precio: $('#price').val(),
            unidades: $('#units').val(),
            modelo: $('#model').val(),
            marca: $('#brand').val(),
            detalles: $('#details').val(),
            imagen: $('#image').val(),
            id: $('#productId').val()
        };

        const url = edit === false ? './backend/product-add.php' : './backend/product-edit.php';

        $.post(url, postData, (response) => {
            let respuesta = JSON.parse(response);
            let template_bar = '';
            template_bar += `
                        <li style="list-style: none;">status: ${respuesta.status}</li>
                        <li style="list-style: none;">message: ${respuesta.message}</li>
                    `;
            $('#name').val('');
            $('#price').val(baseJSON.precio);
            $('#units').val(baseJSON.unidades);
            $('#model').val(baseJSON.modelo);
            $('#brand').val(baseJSON.marca);
            $('#details').val(baseJSON.detalles);
            $('#image').val(baseJSON.imagen);
            $('#product-result').show();
            $('#container').html(template_bar);
            listarProductos();
            edit = false;
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud:", textStatus, errorThrown);
        });
    });

    $(document).on('click', '.product-delete', (e) => {
        if (confirm('¿Realmente deseas eliminar el producto?')) {
            const element = $(this)[0].activeElement.parentElement.parentElement;
            const id = $(element).attr('productId');
            $.post('./backend/product-delete.php', { id }, (response) => {
                $('#product-result').hide();
                listarProductos();
            });
        }
    });

    $(document).on('click', '.product-item', (e) => {
        const element = $(this)[0].activeElement.parentElement.parentElement;
        const id = $(element).attr('productId');
        $.post('./backend/product-single.php', { id }, (response) => {
            let product = JSON.parse(response);
            $('#name').val(product.nombre);
            $('#productId').val(product.id);
            $('#price').val(product.precio);
            $('#units').val(product.unidades);
            $('#model').val(product.modelo);
            $('#brand').val(product.marca);
            $('#details').val(product.detalles);
            $('#image').val(product.imagen);
            edit = true;
        });
        e.preventDefault();
    });
});