package com.fengqian.config;

import com.fengqian.controller.AdminController;
import com.fengqian.controller.WebController;
import com.fengqian.model._MappingKit;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;

public class DemoConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);// 设置开发模式
		PropKit.use("db.properties");// 读取数据库配置文件

	}

	@Override
	public void configRoute(Routes me) {
		me.add("/admin", AdminController.class);
		me.add("/", WebController.class);
	}

	@Override
	public void configPlugin(Plugins me) {
		//配置数据库信息
		DruidPlugin dp=new DruidPlugin(PropKit.get("jdbcurl"), PropKit.get("username"), PropKit.get("password"), PropKit.get("driverClass"), "wall");
		me.add(dp);
		//使用ActiveRecord操作数据库
		ActiveRecordPlugin plugin=new ActiveRecordPlugin(dp);
		_MappingKit.mapping(plugin);// 简历数据库表名到model的映射关系
		plugin.setShowSql(true);
		plugin.setTransactionLevel(4);
		me.add(plugin);
		
	}

	@Override
	public void configInterceptor(Interceptors me) {
	}

	@Override
	public void configHandler(Handlers me) {

	}

}
