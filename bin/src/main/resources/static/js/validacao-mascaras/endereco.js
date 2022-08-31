//Consultar CEP via API

const cep = document.querySelector("#cep");

const mostraDados = function (result){
    if (result.logradouro !== "" && result.bairro !== "" 
    && result.localidade !== ""  && result.uf !== "") {
        result.pais = "Brasil";
    }
    for(const campo in result){
        if(document.querySelector("#" +campo)){
            document.querySelector("#" +campo).value = result[campo];
        }
     }
 }

cep.addEventListener("blur", (e) =>{
    let search = cep.value.replace(/\.|-/g,"");

    const options = {
        method: 'GET',
        mode: 'cors',
        cache: 'default'
    };

    // retorna uma promisse
    fetch(`https://viacep.com.br/ws/${search}/json/`, options)
    .then(response =>{ response.json()
        //chamar função ao retornar o json
        .then(dado => mostraDados(dado))
    })
    //se der erro
    .catch(e => 
        alert('CEP não encontrado, por favor digite seus dados.'));
});