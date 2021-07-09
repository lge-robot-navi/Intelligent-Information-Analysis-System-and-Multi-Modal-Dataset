<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="/tags/custom-taglib" prefix="custom" %>
<div class="paging text-center">
	<ul class="pagination">
		<custom:paging name="paging">
			<!-- First -->
			<c:if test="${!empty paging.firstPage.href}">
				<li><a href="${paging.firstPage.href}">&laquo;</a></li>
			</c:if>

			<!-- Prev -->
			<c:if test="${!empty paging.previousPage.href}">
		    	<li><a href="${paging.previousPage.href}">&lt;</a></li>
			</c:if>

			<!-- Numbering -->
			<c:forEach var="numbering" items="${paging.pages}" varStatus="status">
				<c:choose>
					<c:when test="${numbering.index eq paging.currentIndex}">
						<li class="active"><a href="#">${numbering.index}</a></li>
					</c:when>

					<c:otherwise>
						<li <c:if test="${status.last}">class="last"</c:if>><a href='${numbering.href}' title="${numbering.index} Page">${numbering.index}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<!-- Next -->
			<c:if test="${!empty paging.nextPage.href}">
				<li><a href="${paging.nextPage.href}">&gt;</a></li>
			</c:if>

			<!-- Last -->
			<c:if test="${!empty paging.lastPage.href}">
				<li><a href="${paging.lastPage.href}">&raquo;</a></li>
			</c:if>
		</custom:paging>
	</ul>
</div>
