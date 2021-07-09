function showLoading() {
	var bodyTop = document.body.scrollTop;
	var documentTop = document.documentElement.scrollTop;
	
	// create div element
	var overlayDIV = document.createElement('div');
	overlayDIV.setAttribute('id', 'overlay');
	overlayDIV.setAttribute('class', 'overlay');
	overlayDIV.setAttribute('style', 'margin-top: ' + ((bodyTop > documentTop ? bodyTop : documentTop)) + 'px;');
	overlayDIV.style.visibility = 'visible';

	var loadingDIV = document.createElement('div');
	loadingDIV.setAttribute('id', 'loading');
	loadingDIV.setAttribute('class', 'modalPopup');
	loadingDIV.setAttribute('style', 'top: ' + ((bodyTop > documentTop ? bodyTop : documentTop) + 250) + 'px;');
	loadingDIV.innerHTML = '<center><img src="' + getContextPath() + '/resources/images/loading.gif"><br>Loading...</center>';

	overlayDIV.appendChild(loadingDIV);
	var content = document.body;
	if (content) {
		content.appendChild(overlayDIV);
		var overlay = document.getElementById('overlay');
		if (overlay) {
			overlay.style.visibility = 'visible';
		}
	}

	return true;
};

function closeLoading() {
	var overlayDIV = document.getElementById('overlay');
	var loadingDIV = document.getElementById('loading');

	if (overlayDIV) {
		if (loadingDIV) {
			overlayDIV.removeChild(loadingDIV);
			document.body.removeChild(overlayDIV);
		}
	}

	return true;
};