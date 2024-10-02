package com.ddd.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Category;
import com.ddd.domain.vo.ExcelCategoryVo;
import com.ddd.enums.AppHttpCodeEnum;
import com.ddd.service.ICategoryService;
import com.ddd.utils.BeanCopyUtils;
import com.ddd.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;

	@GetMapping("/listAllCategory")
	public ResponseResult listAllCategory(){
		ResponseResult result = categoryService.listAllCategory();
		return result;
	}

	@GetMapping("/export")
	//注意返回值类型是void
	public void export(HttpServletResponse response)   {
		try {
			WebUtils.setDownLoadHeader("分类.xlsx",response);
			List<Category> list = categoryService.list();
			List<ExcelCategoryVo> vos = BeanCopyUtils.copyBeanList(list, ExcelCategoryVo.class);
			EasyExcel.write(response.getOutputStream(),ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("文章分类").doWrite(vos);
		} catch (IOException e) {
			ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
			//WebUtils是我们在huanf-framework工程写的类，里面的renderString方法是将json字符串写入到请求体，然后返回给前端
			WebUtils.renderString(response, JSON.toJSONString(result));
		}
	}

}
