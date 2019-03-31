package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.domain.Teacher;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.ChapterService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
        //TODO 权限验证
        String course = params.get("course");

        if (course == null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }

        return chapterService.chapterList(course);

    }

    @RequestMapping(value = "/chapter",method = RequestMethod.POST)
    public ServerResponse<Map> chapter(@RequestBody(required = false) Map<String,String> params){
        //TODO 权限验证
        Chapter chapter = new Chapter();

        String name = params.get("name");
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

    @RequestMapping(value = "/chapter/{id}",method = RequestMethod.PUT)
    public ServerResponse chapterModify(@PathVariable("id") String id,@RequestBody(required = false) Map<String,String> params,HttpSession session){
        //TODO 权限验证
        Chapter chapter = new Chapter();
        System.out.println(params.get("name"));
        String name = params.get("name");
        String info = params.get("info");
        String test = params.get("test");

        if (id == null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }

        chapter.setId(id);
        chapter.setName(name);
        chapter.setTest(test);
        chapter.setInfo(info);

        return chapterService.chapterModify(chapter);

    }

    @RequestMapping(value = "/chapter/{id}",method = RequestMethod.DELETE)
    public ServerResponse chapterDelete(@PathVariable("id") String id,HttpSession session){
        //TODO 权限验证
        if (id == null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        return chapterService.chapterDelete(id);

    }
}
