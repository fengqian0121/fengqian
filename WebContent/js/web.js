$(function(){
	// 自动生成的弹窗
	function tanBox(tishi) {
		$('.tanchuangBox').show();// 弹窗显示
		$('.tanchuang').html(tishi)// 内容自动生成

		setTimeout(function() {// 2秒后弹窗消失
			$('.tanchuangBox').hide();
		}, 2000);
	};
	$("#sendMsg").on('click',function(){
		var name=$.trim($('input[name=name]').val());
		var phone=$.trim($('input[name=phone]').val());
		var msg=$.trim($('#msg').val());
		if(name==''){
			tanBox("请输入用户名");
			return;
		}
		if(phone==''){
			tanBox("请输入联系方式");
			return;
		}
		if(!is11Number(phone)){
			tanBox("请输入11位电话");
			return;
		}
		if(!isPhone3(phone)){
			tanBox("请输入有效联系方式");
			return;
		}
		if(msg==''){
			tanBox("请输入您的建议");
			return;
		}
		$.ajax({
			dataType:'json',
			type:'post',
			url:'/addAdvice',
			data:{
				"name":name,
				"phone":phone,
				"advice":msg
			},
			success:function(data){
				if(data){
					tanBox("信息提交成功");
					window.history.go(0);
				}
			}
		})
		
	})
})