package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.TChapterService;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TChapterController {

    private final TChapterService TChapterService;
    private final PermissionService permissionService;

    @RequestMapping(value = "/chapter/list",method = RequestMethod.GET)
    public ServerResponse chapterList(@RequestParam(value = "course",required = false) String courseId,HttpSession session){
        //参数检查
        if (!Tool.checkParamsNotNull(courseId)){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        //权限判断
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        ServerResponse resultResponse = permissionService.checkCourseOwnerPermission(user.getId(),courseId);
        if (!resultResponse.isSuccess()){
            return resultResponse;
        }
        //查询列表
        return TChapterService.chapterList(courseId);

    }

    @RequestMapping(value = "/chapter",method = RequestMethod.POST)
    public ServerResponse chapter(@RequestBody(required = false) Chapter chapter,HttpSession session){
        //参数检查
        if (chapter == null || !Tool.checkParamsNotNull(chapter.getName(),chapter.getInfo(),chapter.getCourse())){
            log.warn(chapter.toString());
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        //权限判断
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        ServerResponse resultResponse = permissionService.checkCourseOwnerPermission(user.getId(),chapter.getCourse());
        if (!resultResponse.isSuccess()){
            return resultResponse;
        }
        //创建章节
        return TChapterService.chapter(chapter);
    }

    @RequestMapping(value = "/chapter/{chapterId}",method = RequestMethod.PUT)
    public ServerResponse chapterModify(@PathVariable("chapterId") String chapterId,@RequestBody(required = false) Chapter chapter,HttpSession session){
        //参数检查
        if (chapter == null || !Tool.checkParamsNotNull(chapterId,chapter.getCourse())){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        //权限判断
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        ServerResponse resultResponse = permissionService.checkChapterOwnerPermission(user.getId(),chapter.getCourse(),chapterId);
        if (!resultResponse.isSuccess()){
            return resultResponse;
        }
        chapter.setId(chapterId);
        //修改章节
        return TChapterService.chapterModify(chapter);
    }


    @RequestMapping(value = "/chapter/{chapterId}",method = RequestMethod.DELETE)
    public ServerResponse chapterDelete(@PathVariable("chapterId") String chapterId,@RequestBody(required = false) Map<String,String> params,HttpSession session){
        //参数检查
        if (params == null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        String courseId = params.get("course");
        if (!Tool.checkParamsNotNull(chapterId)){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        //权限判断
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        ServerResponse resultResponse = permissionService.checkChapterOwnerPermission(user.getId(),courseId,chapterId);
        if (!resultResponse.isSuccess()){
            return resultResponse;
        }
        //删除章节
        return TChapterService.chapterDelete(courseId,chapterId);
    }

    @RequestMapping(value = "course/chapter/list/{course}",method = RequestMethod.GET)
    public ServerResponse getChaperList(@PathVariable("course") String course,@RequestBody(required = false) Chapter chapter){
        //查询课程
        return TChapterService.CourseChapterList(course);
    }
}
