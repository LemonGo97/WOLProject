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
@TableName(value = "t_service")
public class ServicePO extends BaseService {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    @TableField
    private String name;
    @TableField
    private String clientKey;
}
