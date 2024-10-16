// JSON BASE A MOSTRAR EN FORMULARIO
var baseJSON = {
    "precio": 0.0,
    "unidades": 1,
    "modelo": "XX-000",
    "marca": "NA",
    "detalles": "NA",
    "imagen": "img/default.png"
};

// FUNCIÓN CALLBACK DE BOTÓN "Buscar"
function buscarID(e) {
    /**
     * Revisar la siguiente información para entender porqué usar event.preventDefault();
     * http://qbit.com.mx/blog/2013/01/07/la-diferencia-entre-return-false-preventdefault-y-stoppropagation-en-jquery/#:~:text=PreventDefault()%20se%20utiliza%20para,escuche%20a%20trav%C3%A9s%20del%20DOM
     * https://www.geeksforgeeks.org/when-to-use-preventdefault-vs-return-false-in-javascript/
     */
    e.preventDefault();

    // SE OBTIENE EL ID A BUSCAR
    var id = document.getElementById('profe').value;

    // SE CREA EL OBJETO DE CONEXIÓN ASÍNCRONA AL SERVIDOR
    var client = getXMLHttpRequest();
    client.open('POST', './backend/read.php', true);
    client.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    client.onreadystatechange = function () {
        // SE VERIFICA SI LA RESPUESTA ESTÁ LISTA Y FUE SATISFACTORIA
        if (client.readyState == 4 && client.status == 200) {
            console.log('[CLIENTE]\n'+client.responseText);
            
            // SE OBTIENE EL OBJETO DE DATOS A PARTIR DE UN STRING JSON
            let productos = JSON.parse(client.responseText);    // similar a eval('('+client.responseText+')');
            
            // SE VERIFICA SI EL OBJETO JSON TIENE DATOS
            if(Object.keys(productos).length > 0) {
                // SE CREA UNA LISTA HTML CON LA DESCRIPCIÓN DEL PRODUCTO
                let descripcion = '';
                    descripcion += '<li>precio: '+productos.precio+'</li>';
                    descripcion += '<li>unidades: '+productos.unidades+'</li>';
                    descripcion += '<li>modelo: '+productos.modelo+'</li>';
                    descripcion += '<li>marca: '+productos.marca+'</li>';
                    descripcion += '<li>detalles: '+productos.detalles+'</li>';
                    descripcion += '<li>Imagen: '+productos.imagen+'</li>';
                    
                // SE CREA UNA PLANTILLA PARA CREAR LA(S) FILA(S) A INSERTAR EN EL DOCUMENTO HTML
                let template = '';
                    template += `
                        <tr>
                            <td>${productos.id}</td>
                            <td>${productos.nombre}</td>
                            <td><ul>${descripcion}</ul></td>
                        </tr>
                    `;

                // SE INSERTA LA PLANTILLA EN EL ELEMENTO CON ID "productos"
                document.getElementById("productos").innerHTML = template;
            }
        }
    };
    client.send("id="+id);
}

function buscarProducto(e) {
    e.preventDefault();

    // SE OBTIENE EL TÉRMINO DE BÚSQUEDA
    var searchTerm = document.getElementById('search').value;

    // SE CREA EL OBJETO DE CONEXIÓN ASÍNCRONA AL SERVIDOR
    var client = getXMLHttpRequest();
    client.open('POST', './backend/read.php', true);
    client.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    client.onreadystatechange = function () {
        // SE VERIFICA SI LA RESPUESTA ESTÁ LISTA Y FUE SATISFACTORIA
        if (client.readyState == 4 && client.status == 200) {
            console.log('[CLIENTE]\n' + client.responseText);
            
            // SE OBTIENE EL OBJETO DE DATOS A PARTIR DE UN STRING JSON
            let productos = JSON.parse(client.responseText);
            
            // SE VERIFICA SI EL OBJETO JSON TIENE DATOS
            if (Array.isArray(productos) && productos.length > 0) {
                // SE CREA UNA LISTA HTML CON LA DESCRIPCIÓN DE CADA PRODUCTO
                let template = '';

                productos.forEach(producto => {
                    let descripcion = '';
                    descripcion += '<li>Precio: ' + producto.precio + '</li>';
                    descripcion += '<li>Unidades: ' + producto.unidades + '</li>';
                    descripcion += '<li>Modelo: ' + producto.modelo + '</li>';
                    descripcion += '<li>Marca: ' + producto.marca + '</li>';
                    descripcion += '<li>Detalles: ' + producto.detalles + '</li>';
                    descripcion += '<li>Imagen: <img src="' + producto.imagen + '" alt= ' + producto.imagen + '" /></li>';

                    // SE CREA UNA PLANTILLA PARA CADA PRODUCTO
                    template += `
                        <tr>
                            <td>${producto.id}</td>
                            <td>${producto.nombre}</td>
                            <td><ul>${descripcion}</ul></td>
                        </tr>
                    `;
                });

                // SE INSERTA LA PLANTILLA EN EL ELEMENTO CON ID "productos"
                document.getElementById("productos").innerHTML = template;
            } else {
                // SE MANEJA EL CASO EN QUE NO SE ENCUENTRAN PRODUCTOS
                document.getElementById("productos").innerHTML = '<tr><td colspan="3">No se encontraron productos.</td></tr>';
            }
        }
    };
    client.send("searchTerm=" + encodeURIComponent(searchTerm)); // Se envía el término de búsqueda
}

// FUNCIÓN CALLBACK DE BOTÓN "Agregar Producto"
function agregarProducto(e) {
    e.preventDefault();

    // OBTENEMOS EL NOMBRE DEL PRODUCTO DESDE EL CAMPO DE BÚSQUEDA
    var nombreProducto = document.getElementById('search').value.trim();
    
    // VALIDACIÓN A: El nombre debe ser requerido y tener 100 caracteres o menos.
    if (!nombreProducto || nombreProducto.length > 100) {
        alert('El nombre es requerido y debe tener 100 caracteres o menos.');
        return;
    }

    // OBTENEMOS DESDE EL FORMULARIO EL JSON A ENVIAR (contenido del textarea)
    var productoJsonString = document.getElementById('description').value;

    // CONVERTIMOS EL JSON STRING A OBJETO
    var finalJSON = JSON.parse(productoJsonString);

    // SE AGREGA AL JSON EL NOMBRE DEL PRODUCTO
    finalJSON['nombre'] = nombreProducto;

    // VALIDACIÓN B: La marca debe ser requerida y seleccionarse de una lista de opciones.
    var marca = finalJSON.marca;

    // VALIDACIÓN C: El modelo debe ser requerido, texto alfanumérico y tener 25 caracteres o menos.
    var modelo = finalJSON.modelo;
    var regexAlfanumerico = /^[a-zA-Z0-9-]+$/;
    if (!modelo || !regexAlfanumerico.test(modelo) || modelo.length > 25) {
        alert('El modelo es requerido, debe ser alfanumérico y tener 25 caracteres o menos.');
        return;
    }

    // VALIDACIÓN D: El precio debe ser requerido y mayor a 99.99.
    var precio = parseFloat(finalJSON.precio);
    if (isNaN(precio) || precio <= 99.99) {
        alert('El precio es requerido y debe ser mayor a 99.99.');
        return;
    }

    // VALIDACIÓN E: Los detalles son opcionales, pero deben tener 250 caracteres o menos si se proporcionan.
    var detalles = finalJSON.detalles || "";
    if (detalles.length > 250) {
        alert('Los detalles deben tener 250 caracteres o menos.');
        return;
    }

    // VALIDACIÓN F: Las unidades deben ser requeridas y mayores o iguales a 0.
    var unidades = parseInt(finalJSON.unidades);
    if (isNaN(unidades) || unidades < 0) {
        alert('Las unidades son requeridas y deben ser mayores o iguales a 0.');
        return;
    }

    // VALIDACIÓN G: La imagen es opcional, pero si no se registra, se debe usar la imagen por defecto.
    var imagen = finalJSON.imagen || "img/default.png";
    finalJSON.imagen = imagen;

    // OBTENEMOS EL STRING DEL JSON FINAL PARA ENVIAR
    productoJsonString = JSON.stringify(finalJSON, null, 2);

    // CREACIÓN DEL OBJETO DE CONEXIÓN ASÍNCRONA (AJAX)
    var client = getXMLHttpRequest();
    client.open('POST', './backend/create.php', true);
    client.setRequestHeader('Content-Type', "application/json;charset=UTF-8");

    client.onreadystatechange = function () {
        // VERIFICACIÓN SI LA RESPUESTA ESTÁ LISTA Y FUE SATISFACTORIA
        if (client.readyState == 4) {
            if (client.status == 200) {
                // PARSEAR LA RESPUESTA DEL SERVIDOR
                var respuesta = JSON.parse(client.responseText);

                // MOSTRAR MENSAJE DEPENDIENDO DEL STATUS RECIBIDO
                if (respuesta.status === "success") {
                    alert(respuesta.message);
                } else if (respuesta.status === "error") {
                    alert(respuesta.message);
                }
            } else {
                alert('Error al intentar agregar el producto.');
            }
        }
    };
    // ENVÍO DEL JSON AL SERVIDOR
    client.send(productoJsonString);
}

// SE CREA EL OBJETO DE CONEXIÓN COMPATIBLE CON EL NAVEGADOR
function getXMLHttpRequest() {
    var objetoAjax;

    try{
        objetoAjax = new XMLHttpRequest();
    }catch(err1){
        /**
         * NOTA: Las siguientes formas de crear el objeto ya son obsoletas
         *       pero se comparten por motivos historico-académicos.
         */
        try{
            // IE7 y IE8
            objetoAjax = new ActiveXObject("Msxml2.XMLHTTP");
        }catch(err2){
            try{
                // IE5 y IE6
                objetoAjax = new ActiveXObject("Microsoft.XMLHTTP");
            }catch(err3){
                objetoAjax = false;
            }
        }
    }
    return objetoAjax;
}

function init() {
    /**
     * Convierte el JSON a string para poder mostrarlo
     * ver: https://developer.mozilla.org/es/docs/Web/JavaScript/Reference/Global_Objects/JSON
     */
    var JsonString = JSON.stringify(baseJSON,null,2);
    document.getElementById("description").value = JsonString;
}