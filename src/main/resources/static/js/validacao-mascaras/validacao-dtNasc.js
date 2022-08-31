
function validarIdade(data){
  let dataAtual = new Date();
  let dia = dataAtual.getDate();
  let mes = dataAtual.getMonth();
  let ano = dataAtual.getFullYear();

  const field = document.getElementById('dtNascimento');
  let dataNasc = field.value.split("/");

  let dtNascimento = new Date(`${dataNasc[2]}/${dataNasc[1] -1}/${dataNasc[0]}`);
  let dtNascimentoDia = dtNascimento.getDate();
  let dtNascimentoMes = dtNascimento.getMonth();
  let dtNascimentoAno = dtNascimento.getFullYear();
  
  // Comparar o ano de nascimento
  if(ano - dtNascimentoAno > 18){
    valid(field);

  } else if(ano - dtNascimentoAno == 18){
    // Comparar o mês de nascimento
    if(dtNascimentoMes > mes){
      valid(field);

    } else if(dtNascimentoMes  == mes){
      //Comparar o dia de nascimento
      if(dtNascimentoDia >= dia){
        valid(field);
      } else 
        invalid(field);

    } else 
      invalid(field);

  } else 
      invalid(field);
}  

function valid(field) {
  field.classList.remove("is-invalid");
  field.classList.add("is-valid");
}

function invalid(field) {
  field.classList.remove("is-valid");
  field.classList.add("is-invalid");
}






