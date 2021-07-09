// jQuery validator for bootstrap
jQuery.validator.setDefaults({
	errorPlacement : function(error, element) {
		error.insertAfter(element.parent());
	},
	invalidHandler: function(form, validator) {
		var errors = validator.numberOfInvalids();
		if (errors) {
			validator.errorList[0].element.focus();
		}
	}
});

// add method
jQuery.validator.addMethod("regex", function(value, element, regexp) {
	var re = new RegExp(regexp);
	return this.optional(element) || re.test(value);
}, "Please check your input.");

// ajax setup
/*jQuery.ajaxError(
	function(e, x, settings, exception) {
		var message;
		var statusErrorMap = {
			'400' : "Server understood the request but request content was invalid.",
            '401' : "Unauthorised access.",
            '403' : "Forbidden resouce can't be accessed",
            '500' : "Internal Server Error.",
            '503' : "Service Unavailable"
		};

		if (x.status) {
            message = statusErrorMap[x.status];
            if (!message) {
            	message = "Unknow Error \n.";
            }
        } else if (exception == 'parsererror'){
            message = "Error.\nParsing JSON Request failed.";
        } else if (exception == 'timeout'){
            message = "Request Time out.";
        } else if (exception == 'abort'){
            message = "Request was aborted by the server";
        } else {
            message = "Unknow Error \n.";
        }

		alert(message);
	}
);
*/