jQuery(function(){
	// Help Toggle
	$('.item>.i_help').click(function(){
		$(this).parent('.item').find('.i_dsc').toggleClass('hide');
	});
	// Input Clear
	var i_text = $('.item>.i_label').next('.i_text');
	$('.item>.i_label').css('position','absolute');
	i_text
	.focus(function(){
		$(this).prev('.i_label').css('visibility','hidden');
	})
	.blur(function(){
		if($(this).val() == ''){
			$(this).prev('.i_label').css('visibility','visible');
		} else {
			$(this).prev('.i_label').css('visibility','hidden');
		}
	})
	.change(function(){
		if($(this).val() == ''){
			$(this).prev('.i_label').css('visibility','visible');
		} else {
			$(this).prev('.i_label').css('visibility','hidden');
		}
	})
	.blur();
});
