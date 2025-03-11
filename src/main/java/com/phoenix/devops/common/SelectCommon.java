package com.phoenix.devops.common;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.phoenix.devops.utils.ParamUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author wjj-phoenix
 * @since 2025-02-18
 */
@Slf4j
public class SelectCommon<T> {
    /**
     * 根据条件查询信息
     *
     * @param condition 条件
     * @param service   查询实体
     * @return 查询结果
     */
    public List<T> findAll(String condition, IService<T> service) {
        return service.list(wrapper(condition));
    }

    /**
     * 分页查询所有信息
     *
     * @param page      页码
     * @param limit     每页大小
     * @param condition 查询条件
     * @param service   查询实体
     * @return 分页对象
     */
    public Page<T> findAllPaginate(Integer page, Integer limit, String condition, IService<T> service) {
        return service.page(new Page<>(page, limit), wrapper(condition));
    }

    /**
     * 根据条件查询信息(关联查询)
     *
     * @param condition 条件
     * @param mapper    查询实体
     * @return 查询结果
     */
    public List<T> findAllWithRelations(String condition, BaseMapper<T> mapper) {
        return mapper.selectListWithRelationsByQuery(wrapper(condition));
    }

    /**
     * 分页查询所有信息(关联查询)
     *
     * @param page      页码
     * @param limit     每页大小
     * @param condition 查询条件
     * @param mapper    查询实体
     * @return 分页对象
     */
    public Page<T> findAllPaginateWithRelations(Integer page, Integer limit, String condition, BaseMapper<T> mapper) {
        return mapper.paginateWithRelations(new Page<>(page, limit), wrapper(condition));
    }

    private QueryWrapper wrapper(String condition) {
        QueryWrapper wrapper = QueryWrapper.create().where("1 = 1");
        if (StrUtil.isNotBlank(condition)) {
            Map<String, Object> map = ParamUtil.split(condition);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                wrapper.and(new QueryColumn(entry.getKey()).like(entry.getValue()));
            }
        }
        return wrapper;
    }
}
