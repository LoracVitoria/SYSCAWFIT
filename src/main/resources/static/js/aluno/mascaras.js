
// Máscara par CPF
$("#cpf").keypress(function() {
    $(this).mask('000.000.000-00');
});

// Máscara para telefone
$("#telefone").keypress(function() {
    $(this).mask('(00) 00000-0000')
});



 