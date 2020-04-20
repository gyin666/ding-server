alert(66666);
var url = "http://kingdee.ngrok2.xiaomiqiu.cn/eas/doEasLogin?userId=" + userId;
alert(url);
var easUrl =  "http://kingdee.ngrok2.xiaomiqiu.cn/ding/gotoEas?userId=" + userId;
$(function(){
    $("#button").click(function(event){
        $.post(url,{
                username: $("#login_user_name").val(),
                password: $("#login_password").val(),
                userid: userId,
            },function(data){
                alert("登录返回结果："+data);
                if (data) {
                    location.href = easUrl;//跳转流程助手主页
                }
                else{
                    alert("登录失败，请重试！");
                }
            },
            "json")
    })
})