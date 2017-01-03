package com.fengqian.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;

import com.fengqian.model.Advice;
import com.fengqian.model.Article;
import com.fengqian.model.ArticleLike;
import com.fengqian.model.Column;
import com.fengqian.model.User;
import com.fengqian.util.FileUploadCommon;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

/**
 * @author fengqian
 */
public class AdminController extends Controller {
	private static final String imgPath = PropKit.get("imgPath"); // 图片显示目录
	public void index() {
		String name=this.getPara("name");
		String pwd=this.getPara("pwd");
		if(name!=null&&pwd!=null&&name.equals("admin")&&pwd.equals("admin")){
			this.render("index.html");
		}else{
			this.redirect("/");
		}
	}

	/**
	 * 获取用户信息
	 */
	public void showUsers() {
		List<User> userList = User.dao.find("select * from " + User.TABLE_NAME + " where role=2");
		this.setAttr("userList", userList);
		this.render("user_list.html");
	}

	/**
	 * 获取栏目信息
	 */
	public void showColumns() {
		List<Map<String, Object>> vos = new ArrayList<>();
		List<Column> columnList = Column.dao.find("select * from " + Column.TABLE_NAME);
		// 获取栏目下面的文章数量
		for (Column column : columnList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("column", column);
			map.put("count", Article.dao.getArticleCountByCol(column.getId()));
			vos.add(map);
		}
		this.setAttr("vos", vos);
		this.render("column_list.html");
	}

	/**
	 * 获取文章信息
	 */
	public void showArticles() {
		List<Map<String, Object>> vos = new ArrayList<>();
		List<Article> articleList = Article.dao.find("select * from " + Article.TABLE_NAME);
		for (Article article : articleList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("article", article);
			//获取文章的点赞数量
			map.put("lcount", ArticleLike.dao.getLikeCountByArt(article.getId()));
			vos.add(map);
		}
		this.setAttr("vos", vos);
		this.render("article_list.html");
	}

	/**
	 * 建议列表
	 */
	public void showAdvice() {
		List<Advice> adviceList = Advice.dao.find("select * from " + Advice.TABLE_NAME);
		this.setAttr("adviceList", adviceList);
		this.render("advice_list.html");
	}
	
	/**
	 * 上传图片(json)
	 */
	public void uploadImg() {
		boolean isMultipart = ServletFileUpload.isMultipartContent(this.getRequest());
		Map<String, String> map = new HashMap<String, String>();
		if (isMultipart) {
			try {
				String fName = FileUploadCommon.fileSave(getRequest());
				if(fName!=null){
					map.put("code", "200"); // -- 上传成功
					map.put("img", imgPath+fName);
				}
			} catch (Exception e) {
				e.printStackTrace();
				map.put("code", "500"); // -- 上传失败
			}
		}
		this.renderJson(map);
	}
	/**
	 * 上传图片(json)
	 */
	public void uploadImg2() {
		boolean isMultipart = ServletFileUpload.isMultipartContent(this.getRequest());
		JSONObject obj = new JSONObject();
		if (isMultipart) {
			try {
				String fName = FileUploadCommon.fileSave(getRequest());
				if(fName!=null){
					obj.put("error", 0);
					obj.put("url", imgPath+fName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.renderJson(obj.toJSONString());
	}

	/**
	 * 添加模板(json)
	 */
	public void addColumn() {
		Column column = new Column();
		column.setColumnName(this.getPara("name"));// 栏目名称
		column.setColumnDesc(this.getPara("desc"));// 栏目描述
		column.setColumnImg(this.getPara("img"));// 图片
		column.setIsUp(2);// 默认下线
		column.setUpTime(new Date());
		column.save();
		this.renderJson(true);
	}
	/**
	 * 跳转至修改模版
	 * */
	public void toUpdateColumn(){
		Column column = Column.dao.findById(this.getPara("id"));
		this.setAttr("column", column);
		this.render("column_info.html");
	}
	/**
	 * 修改模板(json)
	 */
	public void updateColumn() {
		Column column = Column.dao.findById(this.getPara("id"));
		column.setColumnName(this.getPara("name"));// 栏目名称
		column.setColumnDesc(this.getPara("desc"));// 栏目描述
		column.setColumnImg(this.getPara("img"));// 图片
		column.update();
		this.renderJson(true);
	}

	/**
	 * 模版上下线
	 */
	public void upDownColumn() {
		Column column = Column.dao.findById(this.getPara("id"));
		Integer flag = this.getParaToInt("flag");
		column.setIsUp(flag);
		if (flag == 1) {
			column.setUpTime(new Date());
		}
		column.update();
		this.redirect("showColumns");
	}
	/**
	 * 跳转至添加文章
	 * */
	public void toAddArticle() {
		//获取模版列表
		List<Column> columnList = Column.dao.find("select * from " + Column.TABLE_NAME);
		this.setAttr("columnList", columnList);
		this.render("article_add.html");
		
	}

	/**
	 * 添加文章
	 */
	public void addArticle() {
		Article article = new Article();
		article.setTitle(this.getPara("title"));// 文章标题
		article.setContent(this.getPara("content"));// 文章内容
		article.setColumnId(this.getParaToLong("columnId"));// 栏目id
		article.setColumnName(this.getPara("columnName"));// 栏目标题
		article.setImg(this.getPara("img"));//文章图片
		article.setIsUp(2);// 1.上线 2.下线
		article.setUpTime(new Date());
		article.save();
		this.renderJson(true);
	}

	/**
	 * 文章上下线
	 */
	public void upDownArticle() {
		Integer flag = this.getParaToInt("flag");// 1.上线 2.下线
		Article article = Article.dao.findById(this.getPara("id"));
		article.setIsUp(flag);
		if (flag == 1) {
			article.setUpTime(new Date());
		}
		article.update();
		this.redirect("showArticles");
	}

	/**
	 * 跳转修改文章
	 */
	public void toUpdateArticle() {
		Article article = Article.dao.findById(this.getPara("id"));
		List<Column> columnList = Column.dao.find("select * from " + Column.TABLE_NAME);
		this.setAttr("columnList", columnList);
		this.setAttr("article", article);
		this.render("article_info.html");
	}
	/**
	 *修改文章
	 */
	public void updateArticle() {
		Article article = Article.dao.findById(this.getPara("id"));
		article.setTitle(this.getPara("title"));// 文章标题
		article.setContent(this.getPara("content"));// 文章内容
		article.setColumnId(this.getParaToLong("columnId"));// 栏目id
		article.setColumnName(this.getPara("columnName"));// 栏目标题
		article.setImg(this.getPara("img"));//文章图片
		article.update();
		this.renderJson(true);
	}
}
