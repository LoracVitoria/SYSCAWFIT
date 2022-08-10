
function validaCampoValor(){

    let field= document.getElementById("valor");
    
    if(field.value == "" || field.value <= 0){
        invalid(field);
    } else {
        valid(field);
    }
}

function validaCampoPlano(){

    let field = document.getElementById("plano");

    if(field.value == ""){
        invalid(field);
    } else {
        valid(field);
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