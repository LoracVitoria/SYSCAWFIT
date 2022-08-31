
function getCPF(){
    // obter cpf
    let cpfStr = document.getElementById('cpf').value;
    // remover caracteres e manter só numeros
    cpfStr = cpfStr.replace(/\.|-/g,"");

    let cpf = [];

    for(let i=0; i < cpfStr.length; i++){
        cpf.push(Number(cpfStr[i]));
    }
    return cpf;
}

function validaCPF(){

    const field = document.getElementById('cpf' );
    const cpf = getCPF();

    // Verificar qtd.mínima e máxima de digitos
    if(cpf.length != 11){
        invalid(field);
        return;
    }

    // Verificar se são repetidos (inválido)
    if (verificaRepetidos(cpf)) {
        invalid(field);
        return;
    }

    // Verificar primeiro digito verificador
    if (!verificaPrimeiroDigito(cpf)) {
        invalid(field);
        return;
    }

    // Verificar segundo digito verificador
    if (!verificaSegundoDigito(cpf)) {
        invalid(field);
        return;
    }

    valid(field);
}


function verificaRepetidos(cpf){

    let primeiro = cpf[0];

    for (let i = 1; i < cpf.length; i++){
        if (cpf[i] != primeiro) {
            return false;
        }
    }
    return true;
}


function verificaPrimeiroDigito(cpf){
    let mult = 0;
    let primeiroDigito;

    for(let i=0,j=10; i < 9 && j >= 2; i++, j--){
        mult += cpf[i] * j;
    }

    let resto = mult % 11;

    if(resto == 0 || resto == 1){
        primeiroDigito = 0;
    } else {
        primeiroDigito = 11 - resto;
    }

    if(primeiroDigito == cpf[9]){
        return true;
    } else {
        return false;
    }
}

function verificaSegundoDigito(cpf){
    let mult = 0;
    let segundoDigito;

    for(let i=0,j=11; i < 10 && j >= 2; i++, j--){
        mult += cpf[i] * j;
    }

    let resto = mult % 11;

    if(resto == 0 || resto == 1){
        segundoDigito = 0;
    } else {
        segundoDigito = 11 - resto;
    }

    if(segundoDigito == cpf[10]){
        return true;

    } else {
        return false;
    }
}

function valid(field) {
    field.classList.remove("is-invalid");
    field.classList.add("is-valid");
}

function invalid(field) {
    field.classList.remove("is-valid");
    field.classList.add("is-invalid");
}
 


    






