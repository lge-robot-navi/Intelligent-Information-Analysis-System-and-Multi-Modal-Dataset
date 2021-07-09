// check textarea size
jQuery.fn.textareaSize = function() {
	return this.each(function() {
		var $elem = $(this);

		// if Textarea
		if ($elem[0].tagName.toUpperCase() != 'TEXTAREA') {
			return;
		}

		// Keyup
		$elem.keyup(function(e) {
			var $textarea = $(this);
			var txt = getUtf8ByteSize($textarea.val());
			var maxSize = $textarea.attr('utf8-maxlength');
			if (maxSize) {
				txt += " / " + maxSize;
			}
			txt += " Bytes";

			var $logElem = $('<div>', {
				'class': 'textareaSize',
				'text': txt
			});

			// remove old object, and append new object
			$elem.nextAll('.textareaSize').remove()
				.end().after($logElem);
		}).keyup();
	});
};