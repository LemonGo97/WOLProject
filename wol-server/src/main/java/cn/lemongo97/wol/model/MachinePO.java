package cn.lemongo97.wol.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName(value = "t_machine")
public class MachinePO extends BaseService {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 唤醒机名称
     */
    @TableField
    private String name;

    /**
     * IP 地址
     */
    @TableField
    private String ipAddress;

    /**
     * mac 地址
     */
    @TableField
    private String macAddress;

    /**
     * 服务 ID
     */
    @TableField
    private String serviceId;

    /**
     * 描述
     */
    @TableField
    private String description;
}
