 $(document).ready(function() {
	 function escapeHtml(unsafe) {
		    return unsafe
		         .replace(/&/g, "-amp-")
		         .replace(/</g, "-lt-")
		         .replace(/>/g, "-gt-")
		         .replace(/"/g, "-quot-")
		         .replace(/'/g, "-039-")
		         .replace(/\//g, "-slash-");
		 }
	 function restoreHtml(safe){
		    return safe
	         .replace(/-amp-/g, "&")
	         .replace(/-lt-/g, "<")
	         .replace(/-gt-/g, ">")
	         .replace(/-quot-/g, "\"")
	         .replace(/-039-/g, "'")
	         .replace(/-slash-/g, "/");
	 }
	 
	 $('.home').on('click', navigation);
	 $('.get').on('click', function(e){
		 navigation(e);
		 $('.item').html("");
		 $('.another').css('display','none');
		 $('.loader').fadeIn('slow');
		 getRandomItem();
	 });
	 $('.contact').on('click', navigation);
	 $('.add').on('click', navigation);
	 $('#started').on('click', function(e){
		    $('.item').html("");
			$('.another').css('display','none');
			$('ul').find('li.active').removeClass('active');
			$('.get').parent('li').addClass('active');
			fades("get");
			$('.loader').fadeIn('slow');
			getRandomItem();
	 });
	 $('.getItem').on('click', function(e){
		 e.preventDefault();
		$('.another').fadeOut('fast', function(){
			$('.item').fadeOut('slow', function() {
				$('.loader').fadeIn('slow');
				 getRandomItem();
			});
		});
	 });
	 $('.addnew').on('click', function(){
		 $(".thanks").fadeOut('fast',function(){
			 $(".opps").fadeOut('fast',function(){
				$('input[name="title"]').val("");
				$('input[name="url"]').val("");
				$('input[name="thumb"]').val("");
				$('textarea[name="desc"]').val(""); 
				$(".addsale").fadeIn();
			 }); 
		 });
	 });
	 
	 function navigation(e){
	        e.preventDefault();
	        var $thisClassName = $(e.target).attr('class');
		    var $thisLi = $(e.target).parent('li');
		    var $ul = $thisLi.parent('ul');
		    if (!$thisLi.hasClass('active'))
		        {
		            $ul.find('li.active').removeClass('active');
		                $thisLi.addClass('active');
		        }
		    fades($thisClassName);		 
	 }
	 
	 function fades(selectedNav){
		 var $currentPage = $('.page:visible').attr('id');
		 $('#' + $currentPage).fadeOut('fast', function(){ $('#' + selectedNav).fadeIn('slow');});
	 }

	 $(".additem").submit(function(e){
		e.preventDefault();

		var title = $('input[name="title"]').val();
		title = escapeHtml(title);
		var url = $('input[name="url"]').val();
		url = escapeHtml(url);
		var thumb = $('input[name="thumb"]').val();
		thumb = escapeHtml(thumb);
		var description = $('textarea[name="desc"]').val(); 
		description = escapeHtml(description);
		if(title == ""){
			$('.addtitle').tooltip('show');
		}else if(url == ""){
			//Could be using Regex to test or the jQuery plugin.
			$('.addurl').tooltip('show');
		}else if(thumb == ""){
			//Could be more indepth, could check for HEAD response with AJAX for example.
			$('.addthumb').tooltip('show');
		}else if(description == ""){
			$('.adddesc').tooltip('show');
		}else{
		addItem(title,url,thumb,description);
		}
 	 });
	 
	 function getRandomItem(){
			return	$.ajax({ url: '/Sale/rest/salesservice/randomitem',
				 dataType: "xml",
		         type: 'GET',
				 async: 'false',
		         success: parseItemXml
				});	
	}
	 
	 function addItem(title, url, thumb, description){
			return	$.ajax({ url: '/Sale/rest/salesservice/add/'+title+'&'+url+'&'+thumb+'&'+description,
				 dataType: "xml",
		         type: 'POST',
				 async: 'false',
		         success: function(){
		        	 $('.addsale').fadeOut('fast',function(){
				     		$('.thanks').fadeIn();
		        	 });
		         },
		         error: function(){
		        	 $('.addsale').fadeOut('fast',function(){
				     		$('.opps').fadeIn();
		        	 });
		         }
				});	
	}
	 
	function parseItemXml(xml){
			$(xml).find('item').each(function(){
				title = $(this).find('title').eq(0).text();
				url = $(this).find('url').eq(0).text();
				thumb = $(this).find('thumb').eq(0).text();
				description = $(this).find('description').eq(0).text();
				upvotes = $(this).find('upvotes').eq(0).text();
				downvotes = $(this).find('downvotes').eq(0).text();
				$('.item').html('<div class="item"><h1>'+restoreHtml(title)+'</h1><a href="'+restoreHtml(url)+'" target="_new"><div class="circle"><img src="'+restoreHtml(thumb)+'" alt="Product Image"/></div></a><p>'+restoreHtml(description)+'</p></div>');
				$('.loader').fadeOut('slow', function(){
					$('.item').fadeIn('slow');
					$('.another').fadeIn('slow');
				});
			});
		}

	
 });