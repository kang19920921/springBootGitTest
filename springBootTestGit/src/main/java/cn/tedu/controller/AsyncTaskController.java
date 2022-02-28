package cn.tedu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.enums.ProcessCodeEnum;
import cn.tedu.pojo.ResultVO;
import cn.tedu.service.AsyncImportService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/async")
@Slf4j
public class AsyncTaskController {
	
	@Autowired
	private AsyncImportService  asyncImportService;

	@RequestMapping(value = "/import/test")
	@ResponseBody
	public ResultVO<String> asyncImportTask(String str) {
		log.info("进入 controller 啦啦啦 ");
		log.info("gittest");

		asyncImportService.importTest(str);
		return ProcessCodeEnum.SUCCESS.buildSuccessResultVO();		
	}	
}