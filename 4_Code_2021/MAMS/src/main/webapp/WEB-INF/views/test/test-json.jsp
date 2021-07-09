<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>JSON.</title>
</head>

<body>

  RJSON TEST입니다.

  <input id="startDt" readonly="false" class="form-control datepicker" data-dateformat="yy-mm-dd" />

  <input type="text" id="datepicker1">

  <link rel="stylesheet" type="text/css" href="${REQUEST_CONTEXT_PATH}/resources/js/jquery/themes/base/jquery-ui.css" />
  <script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/jquery/jquery-1.9.1.js"></script>
  <script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/jquery/ui/jquery-ui.min.js"></script>

  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery.min.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>

  <script>
    $('#startDt').datepicker({
      dateFormat: 'yy-mm-dd'
    });

    $(function () {
      $("#datepicker1").datepicker({
        dateFormat: 'yy-mm-dd'
      });
    });
  </script>
</body>

</html>