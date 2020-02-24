package com.ihrm.${ClassName}.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.${ClassName}.service.${ClassName}Service;
import com.ihrm.domain.${ClassName}.${ClassName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//解决跨域问题
@CrossOrigin
@RestController
@RequestMapping(value="/${ClassName}")
public class ${ClassName}Controller {

    @Autowired
    private ${ClassName}Service ${ClassName}Service;

    //保存用户
    @RequestMapping(value="",method = RequestMethod.POST)
    public Result save(@RequestBody ${ClassName} ${ClassName})  {
        //业务操作
        ${ClassName}Service.add(${ClassName});
        return new Result(ResultCode.SUCCESS);
    }

    //根据id更新用户
    /**
     * 1.方法
     * 2.请求参数
     * 3.响应
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value="id") String id, @RequestBody ${ClassName} ${ClassName} ) {
        //业务操作
        ${ClassName}.setId(id);
        ${ClassName}Service.update(${ClassName});
        return new Result(ResultCode.SUCCESS);
    }

    //根据id删除用户
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value="id") String id) {
        ${ClassName}Service.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    //根据id查询用户
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value="id") String id) throws CommonException {
        //throw new CommonException(ResultCode.UNAUTHORISE);
        ${ClassName} ${ClassName} = ${ClassName}Service.findById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(${ClassName});
        return result;
    }

    //查询全部用户列表
    @RequestMapping(value="",method = RequestMethod.GET)
    public Result findAll() {
        //int i = 1/0;
        List<${ClassName}> list = ${ClassName}Service.findAll();
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(list);
        return result;
    }
}
