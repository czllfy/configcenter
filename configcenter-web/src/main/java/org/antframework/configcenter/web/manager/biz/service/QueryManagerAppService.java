/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-09-20 12:28 创建
 */
package org.antframework.configcenter.web.manager.biz.service;

import org.antframework.common.util.facade.FacadeUtils;
import org.antframework.configcenter.web.manager.dal.dao.ManagerAppDao;
import org.antframework.configcenter.web.manager.dal.entity.ManagerApp;
import org.antframework.configcenter.web.manager.facade.order.QueryManagerAppOrder;
import org.antframework.configcenter.web.manager.facade.result.QueryManagerAppResult;
import org.bekit.service.annotation.service.Service;
import org.bekit.service.annotation.service.ServiceExecute;
import org.bekit.service.engine.ServiceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询管理员和应用关联服务
 */
@Service
public class QueryManagerAppService {
    @Autowired
    private ManagerAppDao managerAppDao;

    @ServiceExecute
    public void execute(ServiceContext<QueryManagerAppOrder, QueryManagerAppResult> context) {
        QueryManagerAppOrder order = context.getOrder();

        Page<ManagerApp> page = managerAppDao.query(buildSearchParams(order), new PageRequest(order.getPageNo() - 1, order.getPageSize()));
        FacadeUtils.setQueryResult(context.getResult(), new FacadeUtils.SpringDataPageExtractor<>(page));
    }

    // 构建searchParams
    public Map<String, Object> buildSearchParams(QueryManagerAppOrder queryManagerAppOrder) {
        Map<String, Object> searchParams = new HashMap<>();
        if (queryManagerAppOrder.getUsername() != null) {
            searchParams.put("LIKE_username", "%" + queryManagerAppOrder.getUsername() + "%");
        }
        if (queryManagerAppOrder.getAppCode() != null) {
            searchParams.put("LIKE_appCode", "%" + queryManagerAppOrder.getAppCode() + "%");
        }
        return searchParams;
    }
}
