$(function() {
	$("#b1").fileupload({
		dataType : 'json',
		url : "/admin/uploadImg",
		done : function(e, data) {
			if (data.result.code == "500") {
			} else {
				$("#bag_b1").attr("src", data.result.img);
				$("#b1_img").attr("value", data.result.img);
			}
		}
	});
	$("#scl").click(function() {
		name = $("#name").val();
		if (name == "") {
			$("#warn").html("* 请填写栏目名称")
			return false;
		}
		desc = $("#desc").val();
		if (desc == "") {
			$("#warn").html("* 请填写栏目描述")
			return false;
		}
		b1 = $("#b1_img").attr("value");
		if (b1 == "") {
			$("#warn").html("* 请上传栏目图片")
			return false;
		}
		var columnId = "";
		nurl = "/admin/addColumn";
		if ($("#sql").val() == 'update') {
			nurl = "/admin/updateColumn";
			columnId = $("#columnId").val();
		}
		$.ajax({
			dataType : "json",
			type : "post",
			url : nurl,
			data : {
				"name" : name,
				"desc" : desc,
				"img" : b1,
				"id" : columnId
			},
			success : function(data) {
				if (data == true)
					window.location.href = "/admin/showColumns";
				else {
					$("#warn").html("* 上传失败")
				}

			}
		});
	})
	$("#addArticle").click(function() {
		title = $("#title").val();
		if (title == "") {
			$("#warn").html("* 请填写文章名称")
			return false;
		}
		content = $("#content").val();
		if (content == "") {
			$("#warn").html("* 请填写文章内容")
			return false;
		}
		b1 = $("#b1_img").attr("value");
		if (b1 == "") {
			$("#warn").html("* 请上传文章图片")
			return false;
		}
		var articleId = "";
		nurl = "/admin/addArticle";
		if ($("#sql").val() == 'update') {
			nurl = "/admin/updateArticle";
			articleId = $("#articleId").val();
		}
		$.ajax({
			dataType : "json",
			type : "post",
			url : nurl,
			data : {
				"title" : title,
				"content" : content,
				"id" : articleId,
				"img" : b1,
				"columnId":$("#column").val(),
				"columnName":$("#column_"+$("#column").val()).html()
			},
			success : function(data) {
				if (data == true)
					window.location.href = "/admin/showArticles";
				else {
					$("#warn").html("* 上传失败")
				}

			}
		});
	})
})