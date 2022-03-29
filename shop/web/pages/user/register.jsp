<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>惠民会员注册页面</title>
		<%@ include file="/pages/common/head.jsp"%>
		<script type="text/javascript">
			$(function (){

				$(function () {
					$("#username").blur(function () {
						//获取用户名
						var username = this.value;
						$.getJSON("http://localhost:8080/book/userServlet","action=ajaxExistUsername&username="+username,function (data) {
							if (data.existUsername){
								$("span.errorMsg").text("用户名已存在！")
							}else{
								$("span.errorMsg").text("用户名可用！")
							}
						})
					})
				})

				$("#code_img").click(function (){
					//在事件响应的function函数中有一个this对象。这个this对象，是当前正在响应事件的dom对象。
					//src属性表示验证码img标签的图片路径。它可读，可写
					this.src = "${BasePath}Kaptcha.jpg" + new Date();
					//浏览器为提高访问速度会把请求名和值保存到缓存或者本地磁盘中，下次相同的请求直接会从缓存中取。加上 new Date()为了使请求不同
				})

				$("#sub_btn").click(function (){
					//验证用户名：必须由字母、数字、下划线组成，并且长度为5到12位
					//1 获取用户名输入框里的内容
					var usernameText = $("#username").val();
					//2 创建正则表达式对象
					var usernamePatt = /^\w{5,12}$/;
					//3 使用test方法验证
					if(!usernamePatt.test(usernameText)){
						//4 提示用户结果
						$("span.errorMsg").text("用户名不合法!")
						return false;
					}
					//验证密码：必须由字母，数字下划线组成，并且长度位5到12位
					//1 获取密码输入框的内容
					var passwordText = $("#password").val();
					//2 创建正则表达式
					var passwordPatt = /^\w{5,12}$/;
					//3 使用test方法验证
					if(!passwordPatt.test(passwordText)){
					//4 提示用户结果
						$("span.errorMsg").text("密码不合法！")
						return false;
					}
					//验证确认密码：和密码相同
					//1 获取确认密码内容
					var repwdText = $("#repwd").val();
					//2 和密码相比较
					if(repwdText!=passwordText){
						//3 提示用户
						$("span.errorMsg").text("密码不一致！")
						return false;
					}
					//邮箱验证
					//获取邮箱的内容
					var emailText = $("#email").val();
					// 创建正则表达式
					var emailPatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
					//使用test方法验证是否合法
					if(!emailPatt.test(emailText)){
						//提示用户
						$("span.errorMsg").text("邮箱格式不正确！")
						return false;
					}

					//验证码验证，只需用户输入
					var codeText = $("#code").val();
					//去掉验证码前后空格
					codeText= $.trim(codeText)
					if(codeText==null||codeText==""){
						$("span.errorMsg").text("验证码不能为空");
						return false;
					}
				})

			})
		</script>
	<style type="text/css">
		.login_form{
			height:420px;
			margin-top: 25px;
		}

	</style>
	</head>
	<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>

			<div class="login_banner">

				<div id="l_content">
					<span class="login_word">欢迎注册</span>
				</div>

				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>注册惠民会员</h1>
								<span class="errorMsg">
									<%=request.getAttribute("msg")==null?"":request.getAttribute("msg")%>
								</span>
							</div>
							<div class="form">
								<form action="userServlet" method="post">
									<input type="hidden" name="action" value="register">
									<label>用户名称：</label>
									<input class="itxt" type="text" placeholder="请输入用户名"
										   autocomplete="off" tabindex="1" name="username" id="username"
											value="${requestScope.username}"/>
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码"
										   autocomplete="off" tabindex="1" name="password" id="password" />
									<br />
									<br />
									<label>确认密码：</label>
									<input class="itxt" type="password" placeholder="确认密码"
										   autocomplete="off" tabindex="1" name="repwd" id="repwd" />
									<br />
									<br />
									<label>电子邮件：</label>
									<input class="itxt" type="text" placeholder="请输入邮箱地址"
										   autocomplete="off" tabindex="1" name="email" id="email"
											value="${requestScope.email}"/>
									<br />
									<br />
									<label>验证码：</label>
									<input class="itxt" type="text" placeholder="请输入验证码" style="width: 118px;margin-left: 15px" name="code" id="code"/>
									<img id="code_img" alt="" src="Kaptcha.jpg" style="float: right; margin-right: 50px;width: 100px;height: 40px">
									<br />
									<br />
									<input type="submit" value="注册" id="sub_btn" />
								</form>
							</div>

						</div>
					</div>
				</div>
			</div>
		<%@ include file="/pages/common/footer.jsp"%>
	</body>
</html>