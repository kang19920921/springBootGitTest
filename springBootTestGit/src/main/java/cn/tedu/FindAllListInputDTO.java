package cn.tedu;


import cn.tedu.pojo.BaseObject;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class FindAllListInputDTO extends BaseObject {

    @NotBlank(message = "用户id不能为空")
    private Integer idUser;

}
