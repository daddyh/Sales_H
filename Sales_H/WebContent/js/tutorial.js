$(function() {
  $('.error').hide();
  $('input.text-input').css({backgroundColor:"#FFFFFF"});
  $('input.text-input').focus(function(){
    $(this).css({backgroundColor:"#FFDDAA"});
  });
  $('input.text-input').blur(function(){
    $(this).css({backgroundColor:"#FFFFFF"});
  });

  $(".button").click(function() {
		// validate and process form
		// first hide any error messages
  //  $('.error').hide();
		
	  var MSG = $("input#name").val();
		if (MSG == "") {
      $("label#name_error").show();
      $("input#name").focus();
      return false;
    }
		var dataString = 'message='+ MSG;
		//alert (dataString);return false;
		
		$.ajax({
      type: "POST",
      url: "InsertMessage",
      cache: false,
      data: dataString,
      success: function() {
        $('#contact_form').html("<div id='message'></div>");
        $('#message').html("<h2>Terminado!</h2>")
        .append("<p>Mensaje Enviado! :D</p>")
        .hide()
        .fadeIn(5000, function() {
          $('#message').append("<img id='checkmark' src='images/check.png' />");
        });
      }
     });
		
    return false;
	});
});
runOnLoad(function(){
  $("input#name").select().focus();
});
