package cn.lemongo97.wol.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseService {
    /**
     * 创建时间
     */
    @TableField
    protected Date createTime;

    /**
     * 更新时间
     */
    @TableField
    protected Date updateTime;

    /**
     * 用户 ID
     */
    @TableField
    protected Long userId;
}
