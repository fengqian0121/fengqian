package com.fengqian.controller;

import java.util.Date;
import java.util.List;

import com.fengqian.model.Advice;
import com.fengqian.model.Article;
import com.fengqian.model.ArticleLike;
import com.fengqian.model.Column;
import com.jfinal.core.Controller;

/**
 * @author fengqian
 */
public class WebController extends Controller {
	public void index() {
		// 获取栏目
		List<Column> columnList = Column.dao.find("select * from " + Column.TABLE_NAME+" where is_up=1");
		this.setAttr("columnList", columnList);
		this.render("index.html");
	}

	/**
	 * 收集建议(json)
	 */
	public void addAdvice() {
		Advice advice = new Advice();
		advice.setName(this.getPara("name"));// 名字
		advice.setAdvice(this.getPara("advice"));// 建议内容
		advice.setPhone(this.getPara("phone"));// 联系电话
		advice.setCreateTime(new Date());
		advice.save();
		this.renderJson(true);
	}

	/**
	 * 根据栏目获得文章列表
	 * 
	 */
	public void articleList() {
		List<Article> articleList = Article.dao.find(
				"select * from " + Article.TABLE_NAME + " where column_id=" + this.getPara("id") + " and is_up=1");
		this.setAttr("articleList", articleList);
		Column column = Column.dao.findById(this.getPara("id"));
		this.setAttr("column", column);
		this.render("/web/article_list.html");
	}

	/**
	 * 文章详情
	 */
	public void articleInfo() {
		Article article = Article.dao.findById(this.getPara("id"));
		this.setAttr("article", article);
		this.render("/web/article_info.html");
	}

	/**
	 * 点赞或是取消点赞
	 */
	public void isLike() {
		Integer flag = this.getParaToInt("flag");// 1.点赞 2.取消点赞
		Long articleId = this.getParaToLong("articleId");// 文章id
		Long uid = (Long) this.getSession().getAttribute("uid");// 用户id
		if (flag == 1) {
			ArticleLike like = new ArticleLike();
			like.setArticleId(articleId);
			like.setUserId(uid);
			like.save();
		} else {
			ArticleLike like = ArticleLike.dao.findFirst("select * from " + ArticleLike.TABLE_NAME
					+ " where article_id=" + articleId + " and user_id=" + uid);
			like.delete();
		}
		this.renderJson(true);
	}

}
