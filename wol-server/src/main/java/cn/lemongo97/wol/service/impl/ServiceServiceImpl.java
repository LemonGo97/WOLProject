package cn.lemongo97.wol.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.lemongo97.wol.common.ResultCode;
import cn.lemongo97.wol.config.Result;
import cn.lemongo97.wol.exception.WOLBaseException;
import cn.lemongo97.wol.mapper.ServiceMapper;
import cn.lemongo97.wol.model.ServicePO;
import cn.lemongo97.wol.service.ServiceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class ServiceServiceImpl extends ServiceImpl<ServiceMapper, ServicePO> implements ServiceService {

    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    public ServicePO addService(ServicePO service) {
        String clientKey = Base64.encode(SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded());
        service.setClientKey(clientKey);
        service.setCreateTime(Date.from(Instant.now()));
        service.setUpdateTime(Date.from(Instant.now()));
        this.save(service);
        return service;
    }

    @Override
    public IPage<ServicePO> listService(Long userId, Page<ServicePO> page) {
        return serviceMapper.selectPage(page, new QueryWrapper<ServicePO>().select("*").eq("user_id", userId));
    }

    @Override
    public void modifyService(ServicePO service) {
        ServicePO old = this.getById(service.getId());
        old.setName(service.getName());
        old.setUpdateTime(Date.from(Instant.now()));
        this.updateById(old);
    }
}
