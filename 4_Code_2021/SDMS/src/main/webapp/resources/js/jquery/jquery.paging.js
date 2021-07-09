/**************************************************************************************
 * jQuery Paging 0.1.7
 * by composite (ukjinplant@msn.com)
 * http://composite.tistory.com
 * This project licensed under a MIT License.
 **************************************************************************************/;

 /**
  * 페이징처리
  * 사용방법
  * ex)
  * <div id="paging" class="paging"></div>
  * 
  * $('#paging').paging({ totalRows: '77', current: 3, onclick: getPage });
  * 
  *  function getPage(item, page, opts) {
  *  	console.debug(item);
  *  	console.debug(page);
  *  	console.debug(opts);
  *  }
  */
(function($) {
	//default properties.
	var a = /a/i,
	defs = {
			contextPath: '/',
			item : 'li',
			//format : '[{0}]',
			//format : '{0}',
			format : '<a href="#">{0}</a>',
			itemClass : 'paging-item',
			sideClass : 'paging-side',
			itemCurrent : 'active',
			totalRows : null,
			//pageSize : 15,
			pageSize : 10,
			interval : 10,
			max : 1,
			current : 1,
			append : false,
			href : '#{0}',
			event : true,
			//next : '[&gt;{5}]',
			//prev : '[{4}&lt;]',
			//first : '[1&lt;&lt;]',
			//last : '[&gt;&gt;{6}]'
			next: '<a href="#">&gt;</a>',
			prev : '<a href="#">&lt;</a>',
			first : '<a href="#">&laquo;</a>',
			last : '<a href="#">&raquo;</a>'
	},
	format = function(str) {
		var arg = arguments;
		return str.replace(/\{(\d+)\}/g, function(m, d) {
			if (+d<0) return m;
			else return arg[+d+1]||"";
		});
	},
	item,
	make = function(opts, page, cls, str) {
		item = document.createElement(opts.item);
		item.className = cls;
		item.innerHTML = format(str, page, opts.interval, opts.start, opts.end, opts.start-1, opts.end+1, opts.max);

		if (a.test(opts.item)) {
			item.href = format(opts.href, page);
		}

		if (opts.event) {
			$(item).bind('click',function(e) {
				var fired = true;
				if ($.isFunction(opts.onclick)) {
					fired = opts.onclick.call(item, e, page, opts);
				}
				if (fired == undefined||fired) {
					opts.origin.paging($.extend({}, opts, { current:page }));
				}
				return fired;
			}).appendTo(opts.origin);

			//bind event for each elements.
			var ev = 'on';
			switch(str) {
				case opts.prev:ev += 'prev'; break;
				case opts.next:ev += 'next'; break;
				case opts.first:ev += 'first'; break;
				case opts.last:ev += 'last'; break;
				default:ev += 'item'; break;
			}
			if ($.isFunction(opts[ev])) {
				opts[ev].call(item, page, opts);
			}
		}
		return item;
	};

	$.fn.paging = function(opts) {
		opts = $.extend({ origin:this }, defs, opts || {});
		this.html('');
		
		if (opts.totalRows) {
			opts.max = Math.ceil(opts.totalRows / opts.pageSize);
		}

		if (opts.max < 1) {
			opts.max = 1;
		}

		if (opts.current < 1) {
			opts.current = 1;
		}

		opts.start = Math.floor((opts.current-1) / opts.interval) * opts.interval + 1;
		opts.end = opts.start-1 + opts.interval;

		if (opts.end > opts.max) {
			opts.end = opts.max;
		}

		if (!opts.append) {
			this.empty();
		}

		// first button
		make(opts, 1, opts.sideClass, opts.first);

		//prev button
		var prevPageNo = opts.start;
		if (opts.current > opts.interval) {
			--prevPageNo;
		}
		make(opts, prevPageNo, opts.sideClass, opts.prev);

		//pages button
		for (var i = opts.start; i <= opts.end; i++) {
			make(opts, i, opts.itemClass + (i == opts.current ? ' ' + opts.itemCurrent:''), opts.format);
		}

		//next button
		var nextPageNo = opts.end;
		if (opts.current <= Math.floor(opts.max / opts.interval) * opts.interval) {
			++nextPageNo;
		}

		if (nextPageNo >= opts.max) {
			nextPageNo = opts.max;
		}

		make(opts, nextPageNo, opts.sideClass, opts.next);

		//last button
		make(opts, opts.max, opts.sideClass, opts.last);
	};
})(jQuery);
