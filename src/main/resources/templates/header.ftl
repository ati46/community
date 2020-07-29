<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Community</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/layui/css/layui.css"  media="all">
<#--    <link rel="shortcut icon" href="/images/logo.ico" type="image/x-icon"/> -->
    <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
    <script src="/js/jquery-3.1.1.min.js"></script>
    <script src="/js/jquery.cookie.js"></script>

    <style>
        input:-webkit-autofill {-webkit-box-shadow:inset 0 0 0 1000px #fff;background-color:transparent;}
        .admin-login-background {width:300px;height:300px;position:absolute;left:50%;top:40%;margin-left:-150px;margin-top:-100px;}
        .admin-header {text-align:center;margin-bottom:20px;color:#ffffff;font-weight:bold;font-size:40px}
        .admin-input {border-top-style:none;border-right-style:solid;border-bottom-style:solid;border-left-style:solid;height:50px;width:300px;padding-bottom:0px;}
        .admin-input::-webkit-input-placeholder {color:#a78369}
        .layui-icon-username {color:#a78369 !important;}
        .layui-icon-username:hover {color:#9dadce !important;}
        .layui-icon-password {color:#a78369 !important;}
        .layui-icon-password:hover {color:#9dadce !important;}
        .admin-input-username {border-top-style:solid;border-radius:10px 10px 0 0;}
        .admin-input-verify {border-radius:0 0 10px 10px;}
        .admin-button {margin-top:20px;font-weight:bold;font-size:18px;width:300px;height:50px;border-radius:5px;background-color:#a78369;border:1px solid #d8b29f}
        .admin-icon {margin-left:260px;margin-top:10px;font-size:30px;}
        .highlight { color: #aa5500}
        i {position:absolute;}
        .admin-captcha {position:absolute;margin-left:205px;margin-top:-40px;}

        #acs{
            overflow-x: hidden;
            overflow-y: scroll;
            height: calc( 100vh - 435px);
        }
        #acs::-webkit-scrollbar {
            display: none;
        }
        .layui-input{height: 30px; padding-right: 30px; cursor: pointer; padding-left: 12px; background-color: #424652; background-color: rgba(255,255,255,.05); border: none 0; color: #fff; color: rgba(255,255,255,.5); font-size: 12px;}

    </style>
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>
<div class="layui-header header header-demo" style="background-color: #393D49; border-bottom: 1px solid #009688;">
    <div class="layui-select-title" style="width: 200px; float: left; margin-top: 15px; margin-left: 30px;">
        <input id="search" type="text" placeholder="搜索内容" value="" class="layui-input" onkeydown="search(event)">
    </div>
    <ul class="layui-nav" id="menus" style="float: right;">
    </ul>

</div>
<div id="loginDiv" style="display: none ">
        <div>
            <i class="layui-icon layui-icon-username admin-icon"></i>
            <input type="text" id="loginName" name="loginName" placeholder="请输入用户名" autocomplete="off" class="layui-input admin-input admin-input-username" value="admin">
        </div>
        <div>
            <i class="layui-icon layui-icon-password admin-icon"></i>
            <input type="password" id="passWord" name="passWord" placeholder="请输入密码" autocomplete="off" class="layui-input admin-input" value="123456">
        </div>
        <#--            <div>-->
        <#--                <input type="text" name="captcha" placeholder="请输入验证码" autocomplete="off" class="layui-input admin-input admin-input-verify" value="xszg">-->
        <#--                <img class="admin-captcha" width="90" height="30" src="../images/captcha.jpg">-->
        <#--            </div>-->
        <button class="layui-btn admin-button" lay-submit="" lay-filter="login" onclick="login()">登 陆</button>
</div>
<script src="/layui/layui.js" charset="utf-8"></script>
<script>
    var local_token = null;
    var loginForm;
    $(function () {
        getMenu();

    });

    function getMenu() {
        initToken();
        $.ajax({
            "type": 'get',
            "cache": false,
            "async": false,
            "url": "/menu",
            success: function (ret) {
                $("#menus").html('');
                $.each(ret.data.list, function (idx, obj) {
                    if (obj.url === "") {
                        $('#menus').append('<li class="layui-nav-item" id="folder" av="' + obj.id + '"><a href="' + "/web/folder/" + obj.id + '">' + obj.name + '</a></li>');
                    } else {
                        $('#menus').append('<li class="layui-nav-item" id="index"><a href="' + obj.url + '">' + obj.name + '</a></li>');
                    }
                });
                if(ret.data.status){
                    $('#menus').append('<li class="layui-nav-item" lay-unselect="">\n' +
                        '        <a href="javascript:;"><img src="//t.cn/RCzsdCq" class="layui-nav-img">' + $.cookie("name") + '</a>\n' +
                        '        <dl class="layui-nav-child">\n' +
                        '            <dd><a href="javascript:;">修改信息</a></dd>\n' +
                        '            <dd><a href="javascript:;">安全管理</a></dd>\n' +
                        '            <dd><a href="javascript:logout();">退了</a></dd>\n' +
                        '        </dl>\n' +
                        '    </li>');
                }else{
                    $('#menus').append('<li class="layui-nav-item" id="login"><a href="javascript:;" onclick="loginWeb()">登录</a></li>');
                }
            },
            error: function (err) {
                console.log('请求失败')
            }
        });
    }

    function logout() {
        $.ajax({
            "type": 'post',
            "cache": false,
            "url": "/logout",
            success: function (ret) {
                if (ret.code === 10000) {
                    $.removeCookie("token");
                    $.removeCookie("name");
                    $.removeCookie("photo");
                    $.removeCookie("loginName");
                    getMenu();
                    layer.msg('退出成功', function () {
                        //window.location = '/';
                    });
                }else{
                    layer.msg(ret.msg);
                }
            },
            error: function (err) {
                console.log('请求失败')
            }
        });
    }

    function loginWeb() {
        // layer.open({
        //     type: 1
        //     ,title: false //不显示标题栏
        //     ,closeBtn: false
        //     ,area: '300px;'
        //     ,shade: 0.8
        //     ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
        //     ,btn: ['火速围观', '残忍拒绝']
        //     ,btnAlign: 'c'
        //     ,moveType: 1 //拖拽模式，0或者1
        //     ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">你知道吗？亲！<br>layer ≠ layui<br><br>layer只是作为Layui的一个弹层模块，由于其用户基数较大，所以常常会有人以为layui是layerui<br><br>layer虽然已被 Layui 收编为内置的弹层模块，但仍然会作为一个独立组件全力维护、升级。<br><br>我们此后的征途是星辰大海 ^_^</div>'
        //     ,success: function(layero){
        //         var btn = layero.find('.layui-layer-btn');
        //         btn.find('.layui-layer-btn0').attr({
        //             href: 'http://www.layui.com/'
        //             ,target: '_blank'
        //         });
        //     }
        // });
        loginForm = layer.open({
            type: 1,
            skin: 'layui-layer-demo', //样式类名
            closeBtn: 0, //不显示关闭按钮
            anim: 2,
            shadeClose: true, //开启遮罩关闭
            content: $("#loginDiv").html()
        });
    }

    function login() {
        var loginName = $("#loginName").val();
        var passWord = $("#passWord").val();
        var data = {
            "loginName": loginName,
            "passWord": passWord
        }
        $.ajax({
            "type": 'post',
            "cache": false,
            "url": "/login",
            "contentType": "application/json",
            "data": JSON.stringify(data),
            //data: $('#form1').serialize(),
            success: function (ret) {
                if (ret.code === 10000) {
                    //var cookietime = new Date();
                    //cookietime.setTime(cookietime.getTime() + (2 * 60 * 60 * 1000));//coockie保存2小时
                    // $.cookie("token", ret.data.token, {expires:cookietime});
                    // $.cookie("name", ret.data.name);
                    // $.cookie("photo", ret.data.photo);
                    // $.cookie("loginName", ret.data.loginName);
                    layer.close(loginForm);
                    getMenu();
                    layer.msg('登录成功', function () {
                        //window.location = '/';
                    });
                }else{
                    layer.msg(ret.msg);
                }
            },
            error: function (err) {
                console.log('请求失败');
                layer.msg('请求失败，请稍后重试！');
            }
        });

    }

    function initToken() {
        var token = $.cookie("token");
        local_token = "Bearer " + (token === undefined ? "" : token);
        $.ajaxSetup({
            headers: {"Authorization": local_token},
            scriptCharset: 'utf-8',
            complete: function(XMLHttpRequest,textStatus){
                //通过XMLHttpRequest取得响应结果
                var res = XMLHttpRequest.responseText;
                try{
                    var jsonData = JSON.parse(res);
                    if(jsonData.code === 20001 || jsonData.code === 20002 || jsonData.code === 20003){
                        layer.msg(jsonData.message, function () {
                            window.location = '/web/login';
                        });
                    }else if(jsonData.code === 50000){
                        layer.msg(jsonData.msg);
                    }else{
                        //正常情况就不统一处理了
                    }
                }catch(e){
                }
            }
        });
    }

    layui.use('element', function(){
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块

        //监听导航点击
        element.on('nav(demo)', function(elem){
            //console.log(elem)
            layer.msg(elem.text());
        });
    });

</script>

<div id="webContent">