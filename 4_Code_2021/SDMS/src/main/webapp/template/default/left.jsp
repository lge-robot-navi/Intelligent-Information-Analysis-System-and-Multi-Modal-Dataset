<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<!-- Tree -->
<nav id="side-menu">
	<c:set var="programTree" value="${sessionScope.SESSION_USER_PROGRAMS}" scope="request" />
	<jsp:include page="/include/inc-program-tree.jsp" />
</nav>

<script type="text/javascript">
	// 최초 페이지 로딩
	$(window).load(function() {
		//$('#snb h2:eq(0)').trigger('click');
		var latestMenu = 'PGM_ID_' + $('#CURRENT_PGM_ID').text() || $.cookie('latestMenu');

		var menu = $('#' + latestMenu);
		var $parent = menu.parent();
		$parent.addClass('active');
		$parent.parents('li').addClass('open');
		$("ul",$parent.parents('li')).css("display","block");
		//$parent.parents('li').find('>a').trigger('click');
	});

	function close() {
		$('#snb h2.menu').removeClass('on');
	}

	$(function() {
		// click event
		$('#side-menu li').delegate('a', 'click', function(event) {
			var $this = $(this);
			var rel = $this.attr('rel');
			if (rel != '') {
				// set cookie
				$.cookie('latestMenu', $this.attr('id'), { path: '/' } );
				location.href = rel;
			}
			return false;
		});
		
/*  	$('nav#side-menu li a[rel="#"]').click(function(event){
			var $this = $(this);
			$("ul > li:first > a ",$this.parents('li')).trigger('click');
			
		})   */
	});
</script>