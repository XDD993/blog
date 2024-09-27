package com.ddd.controller;


import com.ddd.domain.ResponseResult;
import com.ddd.domain.dto.EditTagDto;
import com.ddd.domain.dto.TagListDto;
import com.ddd.domain.entity.Tag;
import com.ddd.domain.vo.PageVo;
import com.ddd.service.ITagService;
import com.ddd.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 标签 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-09-24
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

	@Autowired
	private ITagService tagService;

	/**
	 * 分页查询标签列表
	 * @param pageNum
	 * @param pageSize
	 * @param tagListDto
	 * @return
	 */
	@GetMapping("/list")
	public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
		ResponseResult result = tagService.pageList(pageNum, pageSize, tagListDto);
		return result;
	}

	/**
	 * 新增标签
	 * @param tagListDto
	 * @return
	 */
	@PostMapping()
	public ResponseResult add(@RequestBody TagListDto tagListDto) {
		ResponseResult result = tagService.add(tagListDto);
		return result;
	}

	/**
	 * 逻辑删除标签
	 * @param ids
	 * @return
	 */
	@DeleteMapping()
	public ResponseResult delete(@RequestParam(value = "ids") String ids) {
		if (!ids.contains(",")) {
			tagService.removeById(ids);
		} else {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				tagService.removeById(id);
			}
		}
		return ResponseResult.okResult();
	}

	/**
	 * 查询标签详情
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseResult detail(@PathVariable("id") Long id){
		Tag tag = tagService.getById(id);
		return ResponseResult.okResult(tag);
	}

	/**
	 * 修改标签
	 * @param editTagDto
	 * @return
	 */
	@PutMapping
	public ResponseResult update(@RequestBody EditTagDto editTagDto){
		Tag tag = BeanCopyUtils.copyBean(editTagDto, Tag.class);
		tagService.updateById(tag);
		return ResponseResult.okResult();
	}
}
