<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="/training-java/static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="/training-java/static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="/training-java/static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<%@ page import="model.ComputerList" %>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.jsp"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${nbComputers}"/> Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="add">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>
		<c:if test="${empty current}">
              	<c:set var="page" value="1" scope="page" />
        </c:if>  
        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach items="${computers}" var="entry">
					
					 
	                    <tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="0">
	                        </td>
	                        <td>
	                            <a href="editComputer.jsp" onclick=""><c:out value="${entry.name}"/></a>
	                        </td>
	                        <td><c:if test="${not empty entry.introduced}">${entry.introduced}</c:if></td>
	                        <td><c:if test="${not empty entry.discontinued}">${entry.discontinued}</c:if></td>
	                        <td><c:if test="${not empty entry.company}">${entry.company}</c:if></td>
	
	                    </tr>
					</c:forEach>
                </tbody>
            </table>
        </div>
    </section>
    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li>
                    <a href="computers?page=${current-1}&size=${size}" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
              </li>
              	<c:set var="beginning" value="1" scope="page" />
              	
              	<c:if test="${current>5}">
              		<c:set var="beginning" value="${current-5}" scope="page" />
              	</c:if>
              	<c:set var="end" value="${beginning+10}" scope="page" />
              	<c:if test="${end>nbPages}">
              		<c:set var="end" value="${nbPages}" scope="page" />
              		<c:set var="beginning" value="${end-10}" scope="page" />
              		<c:if test="${beginning<1}">
              			<c:set var="beginning" value="1" scope="page" />
              		</c:if>
              	</c:if>
              	
				<c:forEach begin="${beginning}" end="${end}" var="i" step="1">
					<li><a href="computers?page=${i}&size=${size}">${i}</a></li>
				</c:forEach>
				
              <li>
                <a href="computers?page=${current+1}&size=${size}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>


        <div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default" onclick="size10()">10</button>
            <button type="button" class="btn btn-default" onclick="size50()">50</button>
            <button type="button" class="btn btn-default" onclick="size100()">100</button>
        </div></div>

    </footer>
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/dashboard.js"></script>
<script> 
	function size10() {
		var searchParams = new URLSearchParams(window.location.search);
		if (searchParams.get('page') == null)
		{
			window.location.replace("computers?page=1&size=10");
		}
		else
		{
			window.location.replace("computers?page="+ searchParams.get('page') +"&size=10");
		}
	}
	function size50() {
		var searchParams = new URLSearchParams(window.location.search);
		if (searchParams.get('page') == null)
		{
			window.location.replace("computers?page=1&size=50");
		}
		else
		{
			window.location.replace("computers?page="+ searchParams.get('page') +"&size=50");
		}
	}
	function size100() {
		var searchParams = new URLSearchParams(window.location.search);
		if (searchParams.get('page') == null)
		{
			window.location.replace("computers?page=1&size=100");
		}
		else
		{
			window.location.replace("computers?page="+ searchParams.get('page') +"&size=100");
		}
	}
</script>
</body>
</html>