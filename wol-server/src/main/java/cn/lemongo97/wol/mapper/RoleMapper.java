package cn.lemongo97.wol.mapper;

import cn.lemongo97.wol.model.Role;
import cn.lemongo97.wol.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lemongo97
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 通过 userId 查询其关联角色
     * @param userId
     * @return
     */
    List<Role> getByUserId(Long userId);


}
