package com.fengqian.util;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jfinal.kit.PropKit;

public class FileUploadCommon {

	private static final String pat = PropKit.get("uploadPath"); // 图片本地上传目录

	/**
	 * 上传图片(保存在本地)
	 * 
	 * @param request
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static String fileSave(HttpServletRequest request) throws Exception {
		String nName = "";
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File("/fengqian/server"));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(-1);
		List<FileItem> items = upload.parseRequest(request);
		for (FileItem item : items) {
			if (!item.isFormField()) {
				if (item.getName() != null && !item.getName().equals("")) {
					String point = item.getName().substring(item.getName().indexOf("."), item.getName().length());
					nName = newFileName() + point;

					File finder = new File(pat + File.separator);
					if (!finder.exists()) {
						finder.mkdirs();
					}
					File file = new File(pat + File.separator + File.separator + nName);
					file.setWritable(true, false);
					item.write(file);
				}
			}
		}
		return nName;
	}
	private static String newFileName() {
		String newName = UUID.randomUUID().toString();
		return newName;
	}
}