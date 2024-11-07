function ejemplo3(){
    var nombre2 = prompt("Ingresa tu nombre: ", "");
    var edad2 = prompt("Ingresa tu edad: ", 0);

    var div1 = document.getElementById('nombre2');
    div1.innerHTML = '<h3> Hola ' + nombre2 + ' asi que tienes '  + edad2 +' años</h3>';
}

function ejemplo1(){
    const resultado = "Hola Mundo"; 
    var div1 = document.getElementById('resultadoHolaMundo');
    div1.innerHTML = '<h3> Resultado : '+resultado+'</h3>';
}

function ejemplo2(){
    var nombre = 'Juan';
    var edad = 10;
    var altura = 1.92
    var casado = false;

    var div1 = document.getElementById('nombre');
    div1.innerHTML = '<h3> Nombre: '+nombre+'</h3>';

    var div2 = document.getElementById('edad');
    div2.innerHTML = '<h3> Edad: '+edad+'</h3>';
    
    var div3 = document.getElementById('altura');
    div3.innerHTML = '<h3> Altura: '+altura+'</h3>';

    var div4 = document.getElementById('casado');
    div4.innerHTML = '<h3> Casado: '+casado+'</h3>';
}

function ejemplo4(){
    var valor1 = window.prompt("Introducir primer numero ; ");
    var valor2 = window.prompt("Introducir segundo numero : ");

    var suma = parseFloat(valor1)+parseFloat(valor2);
    var producto = parseFloat(valor1)*parseFloat(valor2);
    
    var div1 = document.getElementById('suma');
    div1.innerHTML = '<h3> La suma es : '+suma+'</h3>';

    var div2 = document.getElementById('producto');
    div2.innerHTML = '<h3> El producto es : '+producto+'</h3>';
}

function ejemplo5(){
    var nombre = prompt("Ingresa tu nombre  : ");
    var nota = prompt("Ingresa tu nota : ");

    var div1 = document.getElementById('nota');

    if(nota>=4){
        div1.innerHTML = '<h3> '+nombre+' esta aprobado con una nota '+nota+'</h3>';
    }

    else{
        div1.innerHTML = '<h3> '+nombre+ ' esta REPROBADO con un '+nota+' Pongase a estudiar mi joven</h3>';
    }
}

function ejemplo6(){
    var n1 = prompt("Ingresa el primer numero : ");
    var n2 = prompt("Ingresa un segundo numero : ");
    var div1 = document.getElementById('mayor');

    n1 = parseFloat(n1);
    n2 = parseFloat(n2);

    if(n1 > n2){
        div1.innerHTML = '<h3>El mayor es : '+n1+'</h3>';
    }

    else{
        div1.innerHTML = '<h3>El mayor es : '+n2+'</h3>';
    }
}

function ejemplo7(){
    var nota1 = prompt("Ingresa 1ra nota : ");
    var nota2 = prompt("Ingresa 2da nota : ");
    var nota3 = prompt("Ingresa 3ra nota : ");
    var pro;

    nota1 = parseFloat(nota1);
    nota2 = parseFloat(nota2);
    nota3 = parseFloat(nota3);
    pro = (nota1+nota2+nota3)/3;

    var div1 = document.getElementById('notas');
    div1.innerHTML = '<h3>La nota promedio es  '+pro+'</h3>';

    if(pro>=7){
        div1.innerHTML = '<h3>El promedio es '+pro+' estas APROBADO</h3>';
    }

    else{
        if(pro>=4){
            div1.innerHTML = '<h3>El promedio es '+pro+' estas regular</h3>';
        }

        else{
            div1.innerHTML = '<h3>El promedio es '+pro+' estas REPROBADO ESTUDIA MAS PLOX</h3>';
        }
    }
}

function ejemplo8(){
    var valor = prompt("Ingresar un valor comprendido entre 1 y 5 : ");
    valor = parseInt(valor);
    var div1 = document.getElementById('rango');

    switch(valor){
        case 1:
            div1.innerHTML = '<h3>UNO</h3>';
            break;
        case 2:
            div1.innerHTML = '<h3>DOS</h3>';
            break;
        case 3:
            div1.innerHTML = '<h3>TRES</h3>';
            break;
        case 4:
            div1.innerHTML = '<h3>CUATRO</h3>';
            break;
        case 5:
            div1.innerHTML = '<h3>CINCO</h3>';
            break;
        default:
            div1.innerHTML = '<h3>Debe ingresar un dato entre 1 - 5</h3>';
    }
}

function ejemplo9(){
    var col = prompt("Ingresa el color con que quierar pintar el fondo de la ventana (rojo, verde, azul)");
    var div1 = document.getElementById('color');

    switch(col){
        case 'rojo':
            div1.innerHTML = '<h3>Color : #ff0000</h3>';
            document.body.style.backgroundColor = '#ff0000';  // Cambia el fondo a rojo
            break;
        case 'verde':
            div1.innerHTML = '<h3>Color : #00ff00</h3>';
            document.body.style.backgroundColor = '#00ff00';  // Cambia el fondo a rojo
            break;
        case 'azul':
            div1.innerHTML = '<h3>Color : #0000ff</h3>';
            document.body.style.backgroundColor = '#0000ff';  // Cambia el fondo a rojo
            break;
    }    
}

function ejemplo10(){
    var x;
    x = 1;
    var div1 = document.getElementById('repetir');

    while(x<=100){
        div1.innerHTML = '<h3>'+ x + '</h3>';
        x = x+1;
    }
}


function ejemplo11(){
    var x = 1;
    var suma = 0;
    var valor;
    var div1 = document.getElementById('sumas');

    while (x <= 5) {
        valor = prompt('Ingresar el valor: ', '');
        valor = parseInt(valor);

        if (!isNaN(valor)) {
            suma = suma + valor;
            x = x + 1;
        } else {
            alert('Por favor, ingresa un número válido');
        }
    }

    div1.innerHTML = '<h3>La suma de los valores ingresados es : ' + suma + '</h3>';
}

function ejemplo12() {
    var valor;
    var div1 = document.getElementById('variable');
    var ultimoValor; // Variable para almacenar el último valor ingresado

    do {
        valor = prompt("Ingrese un valor entre 0 y 999 (o 'exit' para salir): ");

        // Verificar si el usuario quiere salir o si canceló el prompt
        if (valor === null || valor.toLowerCase() === 'exit') {
            if (ultimoValor !== undefined) {
                div1.innerHTML = '<h3>Último valor ingresado: ' + ultimoValor + '</h3>';

                if (ultimoValor < 10) {
                    div1.innerHTML += '<h3>Tiene 1 dígito.</h3>';
                } 
                else if (ultimoValor < 100) {
                    div1.innerHTML += '<h3>Tiene 2 dígitos.</h3>';
                } 
                else {
                    div1.innerHTML += '<h3>Tiene 3 dígitos.</h3>';
                }
                
            }
            div1.innerHTML += '<h3>Salida del programa.</h3>';
            break; 
        }

        valor = parseInt(valor);

        if (isNaN(valor)) {
            div1.innerHTML = '<h3>Por favor, ingrese un número válido.</h3>';
            continue;
        }

        if (valor < 0 || valor > 999) {
            div1.innerHTML = '<h3>El valor debe estar entre 0 y 999.</h3>';
            continue;
        }

        // Almacenar el último valor ingresado
        ultimoValor = valor;

    } while (true); // El bucle seguirá hasta que se encuentre 'exit'
}

function ejemplo13(){
    var f;
    var div1 = document.getElementById('for');


    for(f=1; f<=10; f++){
        div1.innerHTML += '<h3>'+f+' </h3>';
    }
}

function ejemplo14(){
    var f;
    var div1 = document.getElementById('texto');

    for(f=1; f<=3; f++){
        div1.innerHTML += '<h3>‘Cuidado Ingresa tu documento correctamente’ </h3>';
    }
}

function ejemplo15(){
    var div1 = document.getElementById('profe');
    div1.innerHTML += '<h3>Cuidado</h3>';
    div1.innerHTML += '<h3>Ingresa tu documento correctamente </h3>';
}

function ejemplo16(){
    var valor1, valor2;
    valor1 = prompt("Ingresa el valor inferior : ");
    valor1 = parseInt(valor1);

    valor2 = prompt("Ingresa el valor superior : ");
    valor2 = parseInt(valor2);

    mostrarRango(valor1,valor2);
}

function mostrarRango(x1,x2){
    var div1 = document.getElementById('poderoso'); // Obtener el elemento div
    div1.innerHTML = ''; // Limpiar el contenido previo

    for (var inicio = x1; inicio <= x2; inicio++) {
        div1.innerHTML += '<h3>Inicio: ' + inicio + '</h3>'; 
    }
}

function ejemplo17(){
    var valor = prompt("Digite un numero del 1-5 : ");
    valor = parseInt(valor);
    var resultado = Castellano(valor); // Convertir el número a texto
    document.getElementById('conversion').innerHTML = '<h3>Resultado: ' + resultado + '</h3>'; // Mostrar el resultado
}

function Castellano(x){
    if(x==1)
        return "uno";
    else
        if(x==2)
            return "dos";
        else
            if(x==3)
                return "tres";
            else
                if(x==4)
                    return "cuatro";
                else
                    if(x==5)
                        return "cinco"
                    else
                        return "Valor incorrecto"
}

function ejemplo18() {
    var valor = prompt("Digite un numero del 1-5 : ");
    valor = parseInt(valor);
    var resultado = convertirCastellano(valor); // Convertir el número a texto
    document.getElementById('castellano').innerHTML = '<h3>Resultado: ' + resultado + '</h3>'; // Mostrar el resultado
}

function convertirCastellano(x) {
    switch (x) {
        case 1: return "uno";
        case 2: return "dos";
        case 3: return "tres";
        case 4: return "cuatro";
        case 5: return "cinco";
        default: return "valor incorrecto";
    }
}


