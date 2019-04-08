package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.complex.ExamComplex;
import edu.qit.cloudclass.tool.ServerResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @author nic
 * @version 1.0
 */
@RestController
@RequestMapping("/examination")
public class ExaminationController {

    @RequestMapping(value = "/{courseId}",method = RequestMethod.POST)
    public ServerResponse createFinalExam(@PathVariable("courseId")String courseId, @RequestBody(required = false) ExamComplex exam){
        return ServerResponse.createBySuccessMsg("flag:" + courseId);
    }

    @RequestMapping(value = "/{courseId}/{chapterId}",method = RequestMethod.POST)
    public ServerResponse createExam(@PathVariable("courseId")String courseId, @PathVariable("chapterId")String chapterId, @RequestBody(required = false) ExamComplex exam){
        return ServerResponse.createBySuccessMsg("flag:" +courseId + "," + chapterId);
    }
}
