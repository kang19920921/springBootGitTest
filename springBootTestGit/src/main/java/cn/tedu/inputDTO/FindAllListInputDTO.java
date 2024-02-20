package cn.tedu.inputDTO;


import cn.tedu.pojo.BaseInputDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FindAllListInputDTO extends BaseInputDTO {

    @NotNull(message = "用户id不能为空")
    private Integer idUser;


}
