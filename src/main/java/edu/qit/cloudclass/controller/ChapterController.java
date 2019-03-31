package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.service.ChapterService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChapterController {

    private final ChapterService chapterService;

    @RequestMapping(value = "/chapter/list",method = RequestMethod.POST)
    public ServerResponse<List<Chapter>> chapterList(@RequestBody(required = false) Map<String,String> params){

        String course = params.get("course");

        if (course == null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }

        return chapterService.chapterList(course);

    }

    @RequestMapping(value = "/chapter",method = RequestMethod.POST)
    public ServerResponse<Map> chapter(@RequestBody(required = false) Map<String,String> params){
        Chapter chapter = new Chapter();

        String name = params.get("course");
        String info = params.get("info");
        String course = params.get("course");
        String video = params.get("video");
        String test = params.get("test");

        if (!Tool.checkParamsNotNull(name,info,course)){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }

        chapter.setId(Tool.uuid());
        chapter.setCourse(course);
        chapter.setInfo(info);
        chapter.setName(name);
        chapter.setVideo(video);
        chapter.setTest(test);

        return chapterService.chapter(chapter);
    }

    @RequestMapping(value = "/chapter/{num}",method = RequestMethod.POST)
    public ServerResponse<List<Chapter>> chapter(@PathVariable("num") int num){

        String course = params.get("co);

        if (course == null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }

        return chapterService.chapterList(course);

    }
}
