package com.ddd.service.impl;

import com.ddd.domain.entity.Tag;
import com.ddd.mapper.TagMapper;
import com.ddd.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-24
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
