<html>
<head>
    <title>add</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="http:////cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="http:////cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="http:////cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script type='text/javascript' src='/stock/common/knockout-3.4.0.js'></script>
    <script type="text/javascript" src="/stock/js/user/add.js"></script>
</head>
<body>
<form method="post" action="saveorupdate">
<div class="form-group">
    <label for="id">ID</label>
    <input type="text" class="form-control" id="id" data-bind="value: username" placeholder="User ID">
</div>
<div class="form-group">
    <label for="password">Password</label>
    <input type="password" class="form-control" id="password" data-bind="value: password" placeholder="Password">
</div>

<input type="button" class="btn btn-default" data-bind="click:submit" value="Submit" />
</form>
</body>
<script>
    $(function(){

    })
</script>
</html>
