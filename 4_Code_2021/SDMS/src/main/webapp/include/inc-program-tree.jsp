<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty programTree}">
	<ul>
		<c:forEach var="item" items="${programTree}">
			<c:url var="url" value="${item.urlDs}" />
			<li>
				<c:choose>
					<c:when test="${item.pgmTp eq 'U'}">
						<c:set var="clazz" value="${item.levelNo eq 1 ? 'menu-item-parent' : ''}" />
						<a href="#" rel="${url}" id="PGM_ID_${item.pgmId}">
							<i class="fa ${item.iconDs}"></i> <span class="${clazz}">${item.pgmNm}</span></a>
					</c:when>
					<c:otherwise>
						<a href="#" rel="#" id="PGM_ID_${item.pgmId}">
							<i class="fa ${item.iconDs}"></i> <span class="menu-item-parent">${item.pgmNm}</span></a>
					</c:otherwise>
				</c:choose>

				<c:if test="${not empty item.subPrograms}">
					<c:set var="programTree" value="${item.subPrograms}" scope="request" />
					<jsp:include page="/include/inc-program-tree.jsp" />
				</c:if>
			</li>
		</c:forEach>
	</ul>
</c:if>
