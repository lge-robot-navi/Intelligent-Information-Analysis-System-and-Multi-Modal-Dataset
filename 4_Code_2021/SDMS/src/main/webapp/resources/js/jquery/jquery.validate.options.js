/* for datepicker */
$.validator.defaults.errorPlacement = function(error, element) {
	var trigger = element.next('.ui-datepicker-trigger, .textareaSize');
	error.insertAfter(trigger.length > 0 ? trigger : element);
};

/* UTF-8 MAXLENGTH CHECK */
$.validator.addMethod('utf8-maxlength', function(value, element, param) {
	return this.optional(element) || ( getUtf8ByteSize( value ) <= param );
}, "{0} Bytes 까지만 입력가능합니다.");