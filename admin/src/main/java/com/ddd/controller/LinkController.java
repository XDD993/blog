package com.ddd.controller;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Link;
import com.ddd.service.ILinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {

	@Autowired
	private ILinkService linkService;

	@GetMapping("/list")
	public ResponseResult list(Link link, Integer pageNum, Integer pageSize) {
		ResponseResult result = linkService.selectLinkPage(link, pageNum, pageSize);
		return result;
	}

	@PostMapping
	public ResponseResult add(@RequestBody Link link) {
		linkService.save(link);
		return ResponseResult.okResult();
	}

	@GetMapping("/{id}")
	public ResponseResult getInfo(@PathVariable("id") Long id) {
		Link link = linkService.getById(id);
		return ResponseResult.okResult(link);
	}

	@PutMapping
	public ResponseResult edit(@RequestBody Link link) {
		linkService.updateById(link);
		return ResponseResult.okResult();
	}

	@DeleteMapping
	public ResponseResult remove(@RequestParam(value = "ids") String ids) {
		if (!ids.contains(",")) {
			linkService.removeById(ids);
		} else {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				linkService.removeById(id);
			}
		}
		return ResponseResult.okResult();
	}
}
