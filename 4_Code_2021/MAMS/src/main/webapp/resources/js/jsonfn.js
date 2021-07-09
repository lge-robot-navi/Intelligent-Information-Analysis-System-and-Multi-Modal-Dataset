var JSONfn;
if (!JSONfn) {
	JSONfn = {};
}

(function() {	

	JSONfn.stringify = function(obj) {
		return JSON.stringify(obj, function(key, value) {
			if (typeof value == 'function') {
				return value();
			}
			return value;
		});
	};

}());