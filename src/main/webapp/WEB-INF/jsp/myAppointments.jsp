<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta firstName="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<title>Корзина</title>

	<spring:url var="home" value="/" scope="request"/>

	<spring:url value="/resources/css/main.css" var="coreCss"/>
	<spring:url value="/resources/css/bootstrap.css" var="bootstrapCss"/>
	<spring:url value="/resources/css/font-awesome.css" var="fontAwesomeCss"/>

	<link href="${bootstrapCss}" rel="stylesheet"/>
	<link href="${coreCss}" rel="stylesheet"/>
	<link href="${fontAwesomeCss}" rel="stylesheet"/>
	<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="/resources/js/lib/bootstrap.js"></script>

	<spring:url value="/resources/js/lib/jquery.1.10.2.min.js"
				var="jqueryJs"/>
	<script src="${jqueryJs}"></script>

	<spring:url value="/resources/js/lib/jquery.i18n.properties-min-1.0.9.js" var="jqueryi18n"/>
	<script src="${jqueryi18n}"></script>
	<script src="https://momentjs.com/downloads/moment.min.js"></script>
	<%--bootstrap-tables--%>
	<link rel="stylesheet" href="/resources/css/bootstrap-table.css"/>
	<script src="/resources/js/lib/bootstrap-table.js"></script>
	<script src="/resources/js/lib/lodash.min.js"></script>
	<script src="/resources/js/lib/bootstrap-table-filter-control.min.js"></script>
	<script src="/resources/js/lib/bootstrap-table-multiple-sort.js"></script>


	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>
<body>
<div class="container">
	<div class="row">
		<h1>MusicShop</h1>
		<div class="navbar navbar-inverse">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#responsive-menu">
						<span class="sr-only">Open navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="/"><img src="/resources/images/companyLogo.png"></a>
				</div>
				<div class="collapse navbar-collapse" id="responsive-menu">
					<ul class="nav navbar-nav">
						<li><a href="/">Главная</a> </li>
						<li><a href="/departments">Отделения</a></li>
						<li><a href="/prices">Цены</a></li>
						<li><a href="/feedback">Отзывы</a></li>
						<li><a href="/contacts">Контакты</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
                        <c:if test="${currentUser.id == 0}">
                            <li>
                                <a href="/login">
                                    <span class="glyphicon glyphicon-log-in"></span>
                                    Войти
                                </a>
                            </li>
                        </c:if>
                        <c:if test="${currentUser.id != 0}">
                            <c:if test="${currentUser.login != null}">
                                <li id="user-name-label">
                                    <a href="/login">
                                        <span class="glyphicon glyphicon-user"></span>
                                            ${currentUser.login}
                                    </a>
                                </li>
								<li><a href="/myAppointments">Мои приёмы<span class="badge">${appointmentIds.size()}</span></a></li>
                            </c:if>

                            <li>
                                <form action="/logout" method="post" class="navbar-form">
                                    <button type="submit" class="btn btn-link navbar-btn">
                                        <span class="glyphicon glyphicon-log-out"></span>
                                        Выйти
                                    </button>
                                </form>
                            </li>
                        </c:if>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="container">
	<div class="row">
		<h3>Корзина</h3>
	</div>
</div>

<div class="container">
	<div class="row">
		<div class="col-md-11 col-lg-11" id="contentContainer">
			<h4>Мои приёмы</h4>
			<table class="table-responsive table-no-bordered" id="appointments_table" data-toggle="table"
				   data-classes="table"
				   data-click-to-select="true"
				   data-url="/api/userAppointments/${currentUser.id}"
				   data-side-pagination="client"
				   data-pagination="true"
				   data-page-size="10"
				   data-page-list="[10, 25, 35, 45]"
				   data-search="true"
				   data-show-columns="true"
				   data-show-refresh="true"
				   data-show-toggle="true"
				   data-show-multi-sort="true">
				<thead>
				<tr>
					<th data-field="appointmentDate" data-align="center" data-sortable="true" data-formatter="dateFormat">Дата приёма</th>
					<th data-field="commentToAppointment" data-align="center" data-sortable="true">Коментарий к приёму</th>
					<th data-field="doctor.login" data-align="center" data-sortable="true">Логин доктора</th>
					<th data-field="doctor.surname" data-align="center" data-sortable="true">Фамилия доктора</th>
					<th data-field="doctor.phoneNumber" data-align="center" data-sortable="true">Номер телефона доктора</th>
					<th data-field="patient.login" data-align="center" data-sortable="true">Логин пациента</th>
					<th data-field="patient.surname" data-align="center" data-sortable="true">Фамилия пациента</th>
					<th data-field="patient.phoneNumber" data-align="center" data-sortable="true">Номер телефона пациента</th>
					<th data-field="patient.address" data-align="center" data-sortable="true">Адрес пациента</th>
				</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
</div>

<div class="container" id="footer">
	<hr/>
	<div class="text-center center-block">
		<p class="txt-railway">Путь к сайту</p>
		<br />
		<a href="https://vk.com/lizonatrishka"><i class="fa fa fa-vk fa-3x social"></i></a>
		<a href="mailto:liza.lagunovskaya@gmail.com"><i class="fa fa-envelope-square fa-3x social"></i></a>
	</div>
	<hr />
</div>
<script>
	function dateFormat(value, row, index) {
		return moment(value).format('DD/MM/YYYY');
	}
</script>
</body>
</html>