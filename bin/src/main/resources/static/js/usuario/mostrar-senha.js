var $olho = $('#olho');
var $senha = $('#senha');

$olho
    .on('mousedown mouseup', function() {
      var inputType = $senha.attr('type') === 'password' ? 'text' : 'password';
      $senha.attr('type', inputType);
    })
    .on('mouseout', function() {
      $senha.attr('type', 'password');
    });