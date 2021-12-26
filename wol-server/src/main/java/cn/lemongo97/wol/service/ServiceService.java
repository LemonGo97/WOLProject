package cn.lemongo97.wol.service;

import cn.lemongo97.wol.model.ServicePO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ServiceService extends IService<ServicePO> {
    ServicePO addService(ServicePO service);

    IPage<ServicePO> listService(Long userId, Page<ServicePO> page);

    void modifyService(ServicePO service);
}
