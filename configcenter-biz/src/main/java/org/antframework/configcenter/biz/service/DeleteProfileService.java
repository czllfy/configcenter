/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-08-20 15:40 创建
 */
package org.antframework.configcenter.biz.service;

import org.antframework.boot.bekit.AntBekitException;
import org.antframework.common.util.facade.Status;
import org.antframework.configcenter.dal.dao.ProfileDao;
import org.antframework.configcenter.dal.dao.PropertyValueDao;
import org.antframework.configcenter.dal.entity.Profile;
import org.antframework.configcenter.facade.enums.ResultCode;
import org.antframework.configcenter.facade.order.manage.DeleteProfileOrder;
import org.antframework.configcenter.facade.result.manage.DeleteProfileResult;
import org.bekit.service.annotation.service.Service;
import org.bekit.service.annotation.service.ServiceExecute;
import org.bekit.service.engine.ServiceContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
@Service(enableTx = true)
public class DeleteProfileService {
    @Autowired
    private ProfileDao profileDao;
    @Autowired
    private PropertyValueDao propertyValueDao;

    @ServiceExecute
    public void execute(ServiceContext<DeleteProfileOrder, DeleteProfileResult> serviceContext) {
        DeleteProfileOrder order = serviceContext.getOrder();

        Profile profile = profileDao.findLockByProfileCode(order.getProfileCode());
        if (profile == null) {
            return;
        }
        if (propertyValueDao.existsByProfileCode(order.getProfileCode())) {
            throw new AntBekitException(Status.FAIL, ResultCode.ILLEGAL_STATE.getCode(), String.format("环境[%s]还存在属性值，不能删除", order.getProfileCode()));
        }

        profileDao.delete(profile);
    }
}
