package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.domain.Teacher;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.ChapterService;
import edu.qit.cloudclass.service.PermissionService;
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

    private final PermissionService permissionService;

    @RequestMapping(value = "/chapter/list",method = RequestMethod.POST)
    public ServerResponse<List<Chapter>> chapterList(@RequestBody(required = false) Map<String,String> params){
        //TODO 权限验证
        String courseId = params.get("courseId");

        if (courseId == null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }

        return chapterService.chapterList(courseId);
    }

    @RequestMapping(value = "/chapter",method = RequestMethod.POST)
    public ServerResponse<Map> chapter(@RequestBody(required = false) Map<String,String> params,HttpSession session){
        //TODO 权限验证
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        Chapter chapter = new Chapter();
        String name = params.get("name");
        String info = params.get("info");
        String courseId = params.get("courseId");
        String video = params.get("video");
        String test = params.get("test");

        if (!Tool.checkParamsNotNull(name,info,courseId)){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }

        ServerResponse resultResponse = permissionService.checkCourseOwnerPermission(user.getId(),courseId);

        if (!resultResponse.isSuccess()){
            return resultResponse;
        }

        chapter.setId(Tool.uuid());
        chapter.setCourse(courseId);
        chapter.setInfo(info);
        chapter.setName(name);
        chapter.setVideo(video);
        chapter.setTest(test);

        return chapterService.chapter(chapter);

    }

    @RequestMapping(value = "/chapter/{chapterId}",method = RequestMethod.PUT)
    public ServerResponse chapterModify(@PathVariable("chapterId") String chapterId,@RequestBody(required = false) Map<String,String> params,HttpSession session){
        //TODO 权限验证
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        Chapter chapter = new Chapter();
        String name = params.get("name");
        String info = params.get("info");
        String test = params.get("test");
        String courseId = params.get("courseId");
        if (chapterId == null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        ServerResponse resultResponse = permissionService.checkChapterCoursePermission(courseId,chapterId,user.getId());
        if (!resultResponse.isSuccess()){
            return resultResponse;
        }
        chapter.setId(chapterId);
        chapter.setName(name);
        chapter.setTest(test);
        chapter.setInfo(info);
        return chapterService.chapterModify(chapter);
    }


    @RequestMapping(value = "/chapter/{chapterId}/{courseId}",method = RequestMethod.DELETE)
    public ServerResponse chapterDelete(@PathVariable("chapterId") String chapterId,@PathVariable("courseId") String courseId,HttpSession session){
        //TODO 权限验证
        User user = (User) session.getAttribute(UserController.SESSION_KEY);

        if (!Tool.checkParamsNotNull(chapterId,courseId)){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }

        ServerResponse resultResponse = permissionService.checkChapterCoursePermission(courseId,chapterId,user.getId());

        if (!resultResponse.isSuccess()){
            return resultResponse;
        }

        return chapterService.chapterDelete(chapterId);

    }

//    @RequestMapping(value = "/chapter",method = RequestMethod.POST)
//    public ServerResponse<Map> chapter(@RequestBody(required = false) Map<String,String> params){
//        Chapter chapter = new Chapter();
//
//        String name = params.get("course");
//        String info = params.get("info");
//        String course = params.get("course");
//        String video = params.get("video");
//        String test = params.get("test");
//
//        if (!Tool.checkParamsNotNull(name,info,course)){
//            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
//        }
//
//        chapter.setId(Tool.uuid());
//        chapter.setCourse(course);
//        chapter.setInfo(info);
//        chapter.setName(name);
//        chapter.setVideo(video);
//        chapter.setTest(test);
//
//        return chapterService.chapter(chapter);
//    }

//    @RequestMapping(value = "/chapter/{num}",method = RequestMethod.POST)
//    public ServerResponse<List<Chapter>> chapter(@PathVariable("num") int num){
//
//        String course = params.get("co);
//
//        if (course == null){
//            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
//        }
//
//        return chapterService.chapterList(course);
//
//    }

}
