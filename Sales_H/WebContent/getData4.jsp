	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
	<head>		
		<title>Desplegamiento de Records </title>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<meta name="description" content="Flexible Slide-to-top Accordion" />
		<meta name="keywords" content="accordion, jquery, flexible, responsive, slide to top, tabs, UI, web design" />
		<meta name="author" content="Codrops" />
		<link rel="shortcut icon" href="../favicon.ico"> 
		<link rel="stylesheet" type="text/css" href="css/style1.css" />
		<link rel="stylesheet" type="text/css" href="css/prettyPhoto.css" />
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
		<script src="js/modernizr.custom.63321.js"></script>
		<script charset="utf-8" type="text/javascript" src="js/jquery.prettyPhoto.js"></script>
		<script type="text/javascript" src="js/jquery.dropdownH.js"></script>

	</head>

	<body>

		<div class="container">
			<div class="wrapper" id="juntar">

			</div>
	
		</div>

<div>
		
		

		<script type='text/javascript'>

		BringRenglon2 = function() {
			

			var dataString = $(".pp_details .pp_description .pictureId").html();

			dataString = 'message_id2='+ dataString;
       		//alert(dataString);

			//get the <a> data and then serialize that
			//dataString2 = $(dataString).serialize();

			$.ajax({
				type: "POST",
				url: "GetRenglon",
				data: dataString,
				dataType: "json",
				success: function (data) { 

				var data2 = data.Renglon;
					//if (data2.Renglon) {
					//	$.each(data2.Renglon, function (i, data2) {

							var msg_data2 = 
							'<div id="'+data2.msg_id+'">'+
							'<span class="renglon">'+data2.msg_id+'</span>'+
							'</div>';
							$(msg_data2).appendTo(".pp_fade");
							
						//});
						
					//} else {
					//	$("#content").html("No Results");
					//}

				}
			});

		}
			




			$(function () {

				$.ajax({
				type: "POST",
				url: "GetMessages",
				dataType: "json",
				success: function (data) {
					if (data.Messages.length) {
						$.each(data.Messages, function (i, data) {

							var temp = null;	
							var temp = "#cd-dropdown";
							var temp = temp+data.msg_id;
							var msg_data = 
							'<div class="cd-dropdown" id="cd-dropdown'+data.msg_id+'">'+
							'<span>Orden: '+data.msg_id+'</span>'+
							'<input type="hidden" value="-1" name="cd-dropdown">'+
							'<ul style="height: auto;">'+
							'<li>'+
							//'<a href="#?custom=true" rel="prettyPhoto" title="<div class=''pictureId'' style=''display:none''>'+data.message+'</div>picture description">'+data.message+'</a>'+
							//'<a href="#?custom=true" rel="prettyPhoto"><span class="renglon_orden">'+data.message+'</span></a>'+
							'<a href="#?custom=true" rel="prettyPhoto" title="<div class=pictureId style=display:none>'+data.msg_id+'</div>"><span class="renglon_orden">'+data.message+'</span></a>'+
							'</li>'+
							'<li>'+
							'<span class="renglon_orden">'+data.fecha+'</span>'+
							'</li>'+
							'<li>'+
							'<span class="renglon_orden">'+data.fecha+'</span>'+
							'</li>'+
							'<li>'+
							'<span class="renglon_orden">'+data.fecha+'</span>'+
							'</li>'+
							'<li>'+
							'<span class="renglon_orden">'+data.fecha+'</span>'+
							'</li>'+																						
							'</ul>'+
							'</div>';
							$(msg_data).appendTo("#juntar");
							
							$(temp).dropdown( {
								gutter : 6,

							});

						});

					} else {
						$("#content").html("No Results");
					}

					$("a[rel^='prettyPhoto']").prettyPhoto({
					custom_markup: '<div id="ventana_servir" style="width:600 height:300"></div>',
					theme: 'light_square',
					social_tools: false,
					default_width: 600,
					default_height: 200,
					modal: true,
					changepicturecallback: BringRenglon2

					//function () {
       				//var id = $(".pp_details .pp_description .pictureId").html();
       				//alert(id);

       				// }


					//changepicturecallback: function(){ initialize() }
					//changepicturecallback: function(){ $('.pp_content').css("height", "200");}
				});


				}
			});

	

		});


	</script>

		<script type='text/javascript'>
			


		</script>
</div>




	</body>


	</html>