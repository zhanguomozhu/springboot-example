<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>activiti工作流-登录</title>

    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <script src="/webjars/jquery/jquery.min.js "></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div style="width: 100%;height: 300px;padding-left: 30%;padding-right: 30%;margin-top: 10%;">
    <div style="width: 100%;height: 100px;background-color: aliceblue;font-size: 18p;font-weight: bolder;text-align: center;line-height: 100px;margin-bottom: 10px;">
        登录界面
    </div>
    <div class="input-group mb-3">
        <div class="input-group-prepend">
            <span class="input-group-text" id="basic-addon1">账号：</span>
        </div>
        <input type="text" id="username" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1">
    </div>
    <div class="input-group mb-3">
        <div class="input-group-prepend">
            <span class="input-group-text" id="basic-addon1">密码：</span>
        </div>
        <input type="text" id="password" class="form-control" placeholder="Password" aria-label="Username" aria-describedby="basic-addon1">
    </div>
    <div style="width: 100%;display: flex;justify-content: center;">
        <button onclick="login()" style="width: 100%;" type="button" class="btn btn-primary">登录</button>
    </div>
</div>
</body>
<script>
    //登录
    function login(){
        $.ajax({
            url:"http://localhost:8080/activiti/loginAction?username="+$("#username").val(),
            type:"get",
            dataType: "json",
            success: function(data) {
                console.log('登录成功', data);
                localStorage.setItem("id",data.data.id);
                localStorage.setItem("username",data.data.username);
                localStorage.setItem("password",'123456');

                window.location.href = '/activiti/my_list'
            }
        });
    }
</script>
</html>