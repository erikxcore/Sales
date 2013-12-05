 $(document).ready(function() {
	 function escapeHtml(unsafe){
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
	 
	 $('.adminHome').on('click', navigation);
	 $('.adminAllItems').on('click', function(e){
		 navigation(e);
		 if($("#adminAllItems .results").html()=="<p>The item has been approved.</p>" || $("#adminAllItems .results").html()=="<p>The item has been disapproved.</p>" ){
			 $("#adminAllItems .results").css('display','none');
		 }
		 $("#adminAllItems .searchAgain").css('display','none');
	     $('#adminAllItems .loader').stop().fadeIn('slow',function(){
				    searchItems("All");
	     });
	 });
	 $('.adminView').on('click', navigation);
	 $('.adminApprove').on('click', navigation);
	 $('.adminAdd').on('click', navigation);
	 
	 $('.adminApprovedItems').on('click', function(e){
		 navigation(e);
	     $('#adminApprovedItems .loader').stop().fadeIn('slow',function(){
					searchItems("Approved");
			});
	 });
	 $('.results').on('click', 'button.approvedItemApprove' ,function(){
		 changeStatus("approve",this.id);
		 if($('.page:visible').attr('id') == "adminAllItems" ){
			 $("#adminAllItems .results").stop().fadeOut('fast',function(){
				 $("#adminAllItems .results").html(""); 
			     $('#adminAllItems .loader').stop().fadeIn();
			 });
		 }else{
			 $('#adminApprove .searchAgain').stop().fadeOut('fast', function(){
			 $("#adminApprove .results").stop().fadeOut('fast',function(){
			 $("#adminApprove .results").html(""); 
			 $('input[name="approveItem"]').val("");
		     $('#adminApprove .loader').stop().fadeIn();
		 });
			 });
		 }
	 });
	 $('.results').on('click', 'button.approvedItemDisapprove' ,function(){
		 changeStatus("disapprove",this.id);
		 if($('.page:visible').attr('id') == "adminAllItems" ){
			 $("#adminAllItems .results").stop().fadeOut('fast',function(){
				 $("#adminAllItems .results").html(""); 
			     $('#adminAllItems .loader').stop().fadeIn();
			 });
		 }else{
			 $('#adminApprove .searchAgain').stop().fadeOut('fast', function(){
			 $("#adminApprove .results").stop().fadeOut('fast',function(){
			 $("#adminApprove .results").html(""); 
			 $('input[name="approveItem"]').val("");
		     $('#adminApprove .loader').stop().fadeIn();
		 });
			 });
		 }
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
		    clearData($thisClassName);
	 }
	 
	 function fades(selectedNav){
		 var $currentPage = $('.page:visible').attr('id');
		 $('#' + $currentPage).stop().fadeOut('fast', function(){ $('#' + selectedNav).stop().fadeIn('slow');});
	 }
	 
	 function clearData(selectedNav){
		 if(selectedNav == "adminApprove"){
			 $('#adminApprove .searchAgain').stop().fadeOut('fast', function(){
				 $("#adminApprove .results").stop().fadeOut('fast',function(){
				 $("#adminApprove .results").html(""); 
				 $('input[name="approveItem"]').val("");
			     $('#adminApprove #searchItem').stop().fadeIn();
				 });
			 }); 
		 }else if(selectedNav == "adminView"){
			 $('#adminView .searchAgain').stop().fadeOut('fast', function(){
				 $("#adminView .results").stop().fadeOut('fast',function(){
				 $("#adminView .results").html(""); 
				 $('input[name="viewItem"]').val("");
			     $('#adminView #searchItem').stop().fadeIn();
				 });
			 });
		 }
	 }
	 
	 
	 $("#adminApprove #searchItem").submit(function(e){
			e.preventDefault();
			var id = $('input[name="approveItem"]').val();
			var type ="Approve";
			searchItem(id,type);
       	 $('#adminApprove #searchItem').stop().fadeOut('fast',function(){
       		$('#adminApprove .loader').stop().fadeIn();
       	 });
	 });
       	 
	 $("#adminView #searchItem").submit(function(e){
			e.preventDefault();
			var id = $('input[name="viewItem"]').val();
			var type ="View";
			searchItem(id,type);
	       	$('#adminView #searchItem').stop().fadeOut('fast',function(){
	        		$('#adminView .loader').stop().fadeIn();
	       	});
	 	 });
	 $('#adminAllItems .searchAgain').on('click', function(e){
		 e.preventDefault();
			 $('#adminAllItems .searchAgain').stop().fadeOut('fast', function(){
				 $("#adminAllItems .results").html('');
				     $("#adminAllItems .results").stop().fadeOut('fast',function(){ 
				    	 $('#adminAllItems .loader').stop().fadeIn('slow',function(){
							    searchItems("All");
				     });
			 });
		 });
	 });
	 $('#adminApprove .searchAgain').on('click', function(e){
		 e.preventDefault();
			 $('#adminApprove .searchAgain').stop().fadeOut('fast', function(){
				 $("#adminApprove .results").stop().fadeOut('fast',function(){
				 $("#adminApprove .results").html(""); 
				 $('input[name="approveItem"]').val("");
			     $('#adminApprove #searchItem').stop().fadeIn();
			 });
		 });
	 });
	 $('#adminView .searchAgain').on('click', function(e){
		 e.preventDefault();
			 $('#adminView .searchAgain').stop().fadeOut('fast', function(){
				 $("#adminView .results").stop().fadeOut('fast',function(){
				 $("#adminView .results").html(""); 
				 $('input[name="viewItem"]').val("");
			     $('#adminView #searchItem').stop().fadeIn();
			 });
		 });
	 });
	 
	 function searchItem(id, type){
		 if(type == "View"){
				$.ajax({ url: '/Sale/rest/salesservice/item/'+id,
					 dataType: "xml",
			         type: 'get',
					 async: 'false',
			         success: function(xml){
		        		 $(xml).find('item').each(function(){
		     				title = $(this).find('title').eq(0).text();
		     				id = $(this).find('id').eq(0).text();
		     				url = $(this).find('url').eq(0).text();
		     				thumb = $(this).find('thumb').eq(0).text();
		     				description = $(this).find('description').eq(0).text();
		     				upvotes = $(this).find('upvotes').eq(0).text();
		     				downvotes = $(this).find('downvotes').eq(0).text();
		     				status = $(this).find('status').eq(0).text();
		     				if(title == "null"){
			        		    $('#adminView .results').html("<p>There was a problem searching for your item. Did you use a proper integer? If so, perhaps you entered an ID that is too high.</p>");
			     				$('#adminView .loader').stop().fadeOut('slow', function(){
			     				$('#adminView .results').stop().fadeIn('slow');
			     				$('#adminView .searchAgain').stop().fadeIn('slow');
			     				});
		     				}else{
		     					$('#adminView .results').html('<div class="well"><table class="table"><thead><tr><th>ID #</th><th>Title</th><th>URL</th><th>Thumbnail URL</th><th>Upvotes</th><th>Downvotes</th><th>Approval Status</th></tr><tbody><tr><td>'+id+'</td><td>'+title+'</td><td><a href="'+url+'" target="_new">URL</a></td><td><a href="'+thumb+'" target="_new">Thumbnail</a></td><td>'+upvotes+'</td><td>'+downvotes+'</td><td>'+status+'</td></tr><tr><td><strong>Description:</strong></td><td colspan="6">'+description+'</tr></tbody></table></div>');
		     					$('#adminView .loader').stop().fadeOut('slow', function(){
		     					$('#adminView .results').stop().fadeIn('slow');
		     					$('#adminView .searchAgain').stop().fadeIn('slow');
		     				});
		     				}
		     			});
		         },
		         error: function(){
		        		    $('#adminView .results').html("<p>There was a problem searching for your item. Did you use a proper integer? If so, perhaps you entered an ID that is too high.</p>");
		     				$('#adminView .loader').stop().fadeOut('slow', function(){
		     				$('#adminView .results').stop().fadeIn('slow');
		     				$('#adminView .searchAgain').stop().fadeIn('slow');
		        	 });
		         }	
					});	
		 }else if(type == "Approve"){
				$.ajax({ url: '/Sale/rest/salesservice/item/'+id,
					 dataType: "xml",
			         type: 'get',
					 async: 'false',
			         success: function(xml){
			        		 $(xml).find('item').each(function(){
			     				title = $(this).find('title').eq(0).text();
			     				id = $(this).find('id').eq(0).text();
			     				url = $(this).find('url').eq(0).text();
			     				thumb = $(this).find('thumb').eq(0).text();
			     				description = $(this).find('description').eq(0).text();
			     				upvotes = $(this).find('upvotes').eq(0).text();
			     				downvotes = $(this).find('downvotes').eq(0).text();
			     				status = $(this).find('status').eq(0).text();
			     				if(title == "null"){
				        		    $('#adminApprove .results').html("<p>There was a problem searching for your item. Did you use a proper integer? If so, perhaps you entered an ID that is too high.</p>");
				     				$('#adminApprove .loader').stop().fadeOut('slow', function(){
				     				$('#adminApprove .results').stop().fadeIn('slow');
				     				$('#adminApprove .searchAgain').stop().fadeIn('slow');
				     				});
			     				}else{
			     				$('#adminApprove .results').html('<div class="well"><table class="table"><thead><tr><th>ID #</th><th>Title</th><th>URL</th><th>Thumbnail URL</th><th>Upvotes</th><th>Downvotes</th><th>Approve Item?</th><th>Disapprove Item?</th></tr><tbody><tr><td>'+id+'</td><td>'+title+'</td><td><a href="'+url+'" target="_new">URL</a></td><td><a href="'+thumb+'" target="_new">Thumbnail</a></td><td>'+upvotes+'</td><td>'+downvotes+'</td><td><button type="button" class="btn btn-info btn-circle approvedItemApprove" id="'+id+'"><i class="icon-ok"></i></button><td><button type="button" class="btn btn-warning btn-circle approvedItemDisapprove" id="'+id+'"><i class="icon-remove"></i></button></td></tr><tr><td><strong>Description:</strong></td><td colspan="7">'+description+'</tr><tr><td><strong>Approved?: </strong></td><td colspan="6">'+status+'</td></tr></tbody></table></div>');
			     				$('#adminApprove .loader').stop().fadeOut('slow', function(){
			     				$('#adminApprove .results').stop().fadeIn('slow');
			     				$('#adminApprove .searchAgain').stop().fadeIn('slow');
			     				});
			     				}
			     			});
			         },
			         error: function(){
			        		    $('#adminApprove .results').html("<p>There was a problem searching for your item. Did you use a proper integer? If so, perhaps you entered an ID that is too high.</p>");
			     				$('#adminApprove .loader').stop().fadeOut('slow', function(){
			     				$('#adminApprove .results').stop().fadeIn('slow');
			     				$('#adminApprove .searchAgain').stop().fadeIn('slow');
			        	 });
			         }
					});	
		 }
	 }
	 
	 function changeStatus(type,id){
	 if(type == "approve"){
		 $.ajax({ url: '/Sale/rest/salesservice/changestatus/'+id+'&approve',
			 dataType: "xml",
	         type: 'post',
			 async: 'false',
	         success: function(xml){
	        	    if($('.page:visible').attr('id') == "adminAllItems" ){
	        	    	$('#adminAllItems .results').stop().fadeOut('fast',function(){
		        	    			$('#adminAllItems .loader').stop().fadeOut('slow', function(){
		        	    				$('#adminAllItems .results').html('<p>The item has been approved.</p>');
		        	    				$('#adminAllItems .results').stop().fadeIn('slow');
		        	    				$('#adminAllItems .searchAgain').stop().fadeIn('slow');
	        	    		});
	        	    	});
	        	    }else if($('.page:visible').attr('id') == "adminApprove" ){
			    		    $('#adminApprove .results').html("<p>The item has been approved.</p>");
			 			    $('#adminApprove .loader').stop().fadeOut('slow', function(){
			 				$('#adminApprove .results').stop().fadeIn('slow');
			 				$('#adminApprove .searchAgain').stop().fadeIn('slow');
			 			});
	        	    }
	         },
	         error: function(){
	        	    if($('.page:visible').attr('id') == "adminAllItems" ){
	        	    	$('#adminAllItems .loader').stop().fadeOut('slow', function(){
    	    				$('#adminAllItems .results').html('<p>The was a problem approving this item. Please try again later.</p>');
    	    				$('#adminAllItems .results').stop().fadeIn('slow');
    	    				$('#adminAllItems .searchAgain').stop().fadeIn('slow');
	        	    	});
	        	    }else if($('.page:visible').attr('id') == "adminApprove" ){
			    		    $('#adminApprove .results').html("<p>There was a problem approving this item. Please try again later.</p>");
			 			    $('#adminApprove .loader').stop().fadeOut('slow', function(){
			 				$('#adminApprove .results').stop().fadeIn('slow');
			 				$('#adminApprove .searchAgain').stop().fadeIn('slow');
			 			});
	        	    }
	         }
		 });

	 }else if(type == "disapprove"){
		 $.ajax({ url: '/Sale/rest/salesservice/changestatus/'+id+'&disapprove',
			 dataType: "xml",
	         type: 'post',
			 async: 'false',
			 success: function(xml){
	        	    if($('.page:visible').attr('id') == "adminAllItems" ){
	        	    	$('#adminAllItems .loader').stop().fadeOut('slow', function(){
    	    				$('#adminAllItems .results').html('<p>The item has been disapproved.</p>');
    	    				$('#adminAllItems .results').stop().fadeIn('slow');
    	    				$('#adminAllItems .searchAgain').stop().fadeIn('slow');
	        	    	});
	        	    }else if($('.page:visible').attr('id') == "adminApprove" ){
			    		    $('#adminApprove .results').html("<p>The item has been disapproved.</p>");
			 			    $('#adminApprove .loader').stop().fadeOut('slow', function(){
			 				$('#adminApprove .results').stop().fadeIn('slow');
			 				$('#adminApprove .searchAgain').stop().fadeIn('slow');
			 			});
	        	    }
	         },
	         error: function(){
	        	    if($('.page:visible').attr('id') == "adminAllItems" ){
	        	    	$('#adminAllItems .loader').stop().fadeOut('slow', function(){
    	    				$('#adminAllItems .results').html('<p>There was a problem disapproving this item. Please try again later.</p>');
    	    				$('#adminAllItems .results').stop().fadeIn('slow');
    	    				$('#adminAllItems .searchAgain').stop().fadeIn('slow');
	    		});
	        	    }else if($('.page:visible').attr('id') == "adminApprove" ){
			    		    $('#adminApprove .results').html("<p>There was a problem disapproving this item. Please try again later.</p>");
			 			    $('#adminApprove .loader').stop().fadeOut('slow', function(){
			 				$('#adminApprove .results').stop().fadeIn('slow');
			 				$('#adminApprove .searchAgain').stop().fadeIn('slow');
			 			});
	        	    }
	         }
		 });
	  }
	 }
	 
	function searchItems(type){
		if(type == "Approved"){
			$.ajax({ url: '/Sale/rest/salesservice/approveditems',
				 dataType: "xml",
		         type: 'get',
				 async: 'false',
		         success: function(xml){
		        	 $('#adminApprovedItems .results').html('<h1>Approved Items.</h1><div class="well"><table class="table"><thead><tr><th>ID #</th><th>Title</th><th>URL</th><th>Thumbnail URL</th><th>Upvotes</th><th>Downvotes</th><th>Approval Status</th></tr><tbody>');
		        	 $(xml).find('item').each(function(){
	     				title = $(this).find('title').eq(0).text();
	     				id = $(this).find('dbID').eq(0).text();
	     				url = $(this).find('url').eq(0).text();
	     				thumb = $(this).find('thumb').eq(0).text();
	     				description = $(this).find('description').eq(0).text();
	     				upvotes = $(this).find('upVote').eq(0).text();
	     				downvotes = $(this).find('downVote').eq(0).text();
	     				status = $(this).find('approved').eq(0).text();
	     				var table = $("#adminApprovedItems .results .well .table");
	     				$('<tr><td>'+id+'</td><td>'+title+'</td><td><a href="'+url+'" target="_new">URL</a></td><td><a href="'+thumb+'" target="_new">Thumbnail</a></td><td>'+upvotes+'</td><td>'+downvotes+'</td><td>'+status+'</td></tr><tr><td><strong>Description:</strong></td><td colspan="6">'+description+'</tr></table></div>').appendTo(table.find("tbody"));;
	     				$('#adminApprovedItems .loader').stop().fadeOut('slow', function(){
	     					$('#adminApprovedItems .results').stop().fadeIn('fast');
	     				});
	        		 });
	        		 
	         },
	         error: function(){
	        		    $('#adminApprovedItems .results').html("<p>There was a problem generating a list of all approved items. Please try again later.</p>");	
		 			    $('#adminApprovedItems .loader').stop().fadeOut('slow', function(){
			 			$('#adminApprovedItems .results').stop().fadeIn('slow');
			 			$('#adminApprovedItems .searchAgain').stop().fadeIn('slow');
			 			});
	         }	
	         });	
		}else if(type == "All"){
			$.ajax({ url: '/Sale/rest/salesservice/allitems',
				 dataType: "xml",
		         type: 'get',
				 async: 'false',
		         success: function(xml){
		        	 $('#adminAllItems .results').html('<h1>All Items.</h1><div class="well"><table class="table"><thead><tr><th>ID #</th><th>Title</th><th>URL</th><th>Thumbnail URL</th><th>Upvotes</th><th>Downvotes</th><th>Approve Item?</th><th>Disapprove Item?</th></tr><tbody>');
		        	 $(xml).find('item').each(function(){
	     				title = $(this).find('title').eq(0).text();
	     				id = $(this).find('dbID').eq(0).text();
	     				url = $(this).find('url').eq(0).text();
	     				thumb = $(this).find('thumb').eq(0).text();
	     				description = $(this).find('description').eq(0).text();
	     				upvotes = $(this).find('upVote').eq(0).text();
	     				downvotes = $(this).find('downVote').eq(0).text();
	     				status = $(this).find('approved').eq(0).text();
	     				var table = $("#adminAllItems .results .well .table");
	     					$('<tr><td>'+id+'</td><td>'+title+'</td><td><a href="'+url+'" target="_new">URL</a></td><td><a href="'+thumb+'" target="_new">Thumbnail</a></td><td>'+upvotes+'</td><td>'+downvotes+'</td><td><button type="button" class="btn btn-info btn-circle approvedItemApprove" id="'+id+'"><i class="icon-ok"></i></button><td><button type="button" class="btn btn-warning btn-circle approvedItemDisapprove" id="'+id+'"><i class="icon-remove"></i></button></td></tr><tr><td><strong>Description:</strong></td><td colspan="7">'+description+'</tr><tr><td><strong>Current Status:</strong></td><td colspan="6">'+status+'</td></tr></table></div>').appendTo(table.find("tbody"));;
	     					$('#adminAllItems .loader').stop().fadeOut('slow', function(){
	     						$('#adminAllItems .results').stop().fadeIn('fast');
	     					});
		        	 });
	        		 
	         },
	         error: function(){
	        		    $('#adminAllItems .results').html("<p>There was a problem generating a list of all approved items. Please try again later.</p>");	
		 			    $('#adminAllItems .loader').stop().fadeOut('slow', function(){
				 		$('#adminAllItems .results').stop().fadeIn('slow');
				 		$('#adminAllItems .searchAgain').stop().fadeIn('slow');
				 		});
	         }	
	         });	
		}
	}
 });