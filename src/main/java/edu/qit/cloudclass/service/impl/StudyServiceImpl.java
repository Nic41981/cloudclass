package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.StudyMapper;
import edu.qit.cloudclass.domain.Study;
import edu.qit.cloudclass.service.ScoreService;
import edu.qit.cloudclass.service.StudyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-18
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudyServiceImpl implements StudyService {

    private final StudyMapper studyMapper;
    private final ScoreService scoreService;

    @Override
    public void associateDelete(String courseId) {
        List<Study> list = studyMapper.findStudyListByCourse(courseId);
        if (list == null) {
            log.warn("学习列表查询失败");
            return;
        }
        for (Study study : list) {
            //级联删除成绩
            scoreService.associateDelete(study.getId());
            //删除学习记录
            log.info("删除学习:" + study.toString());
            if (studyMapper.delete(study.getId()) == 0) {
                log.warn("学习记录删除失败");
            }
        }
    }
}
