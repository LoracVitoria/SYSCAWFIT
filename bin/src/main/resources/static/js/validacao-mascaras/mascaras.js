
// Máscara para CPF

$( "#cpf").blur(function() {
    $(this).mask('000.000.000-00');
});

// Máscara para telefone
$("#telefone").keypress(function() {
    $(this).mask('(00) 00000-0000')
});

// Máscara para CPF
$( "#cpfBusca").blur(function() {
    $(this).mask('000.000.000-00');
});

$("#txtUsername").keypress(function() {
    $(this).mask('000.000.000-00');
});



 