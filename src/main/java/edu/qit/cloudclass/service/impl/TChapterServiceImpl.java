package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ChapterMapper;
import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.service.ChapterExamService;
import edu.qit.cloudclass.service.FileService;
import edu.qit.cloudclass.service.TChapterService;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TChapterServiceImpl implements TChapterService {

    private final ChapterMapper chapterMapper;
    private final FileService fileService;
    private final ChapterExamService chapterExamService;

    @Override
    public ServerResponse chapter(Chapter chapter) {
        chapter.setId(Tool.uuid());
        int maxNum = chapterMapper.findMaxNum(chapter.getCourse());
        if (chapter.getNum() <= 0 || chapter.getNum() > maxNum) {
            //追加章节-章节序号递增
            chapter.setNum(maxNum + 1);
        } else {
            //指定章节-后面的章节序号递增
            chapterMapper.updateNumBeforeInsert(chapter.getNum(), chapter.getCourse());
        }
        log.info("创建章节:" + chapter.toString());
        if (chapterMapper.insert(chapter) == 0) {
            log.error("创建失败");
            ServerResponse.createByError("创建失败");
        }
        return ServerResponse.createBySuccessMsg("创建成功");
    }

    @Override
    public ServerResponse chapterModify(Chapter chapter) {
        log.info("更新章节:" + chapter.toString());
        if (chapterMapper.modify(chapter) == 0) {
            log.error("更新失败");
            return ServerResponse.createByError("更新失败");
        }
        return ServerResponse.createBySuccessMsg("更新成功");
    }

    @Override
    public ServerResponse chapterDelete(String chapterId) {
        log.info("==========删除章节开始==========");
        //查找记录
        Chapter chapter = chapterMapper.findChapterByPrimaryKey(chapterId);
        if (chapter == null) {
            log.error("==========获取章节记录失败==========");
            return ServerResponse.createByError("删除失败");
        }
        //删除记录
        log.info("删除章节:" + chapter.toString());
        if (chapterMapper.delete(chapterId) == 0) {
            log.error("==========删除章节记录失败==========");
            return ServerResponse.createByError("删除失败");
        }
        chapterMapper.updateNumAfterDelete(chapter.getNum(), chapter.getCourse());
        //级联删除
        if (chapter.getVideo() != null) {
            fileService.associateDelete(chapter.getVideo());
        }
        if (chapter.getChapterExam() != null) {
            chapterExamService.associateDelete(chapter.getChapterExam());
        }
        log.info("==========删除章节结束==========");
        return ServerResponse.createBySuccessMsg("删除成功");
    }

    @Override
    public void associateDelete(String courseId) {
        List<Chapter> list = chapterMapper.chapterList(courseId);
        if (list == null) {
            log.warn("章节列表查询失败");
            return;
        }
        for (Chapter chapter : list) {
            if (chapter.getVideo() != null) {
                fileService.associateDelete(chapter.getVideo());
            }
            if (chapter.getChapterExam() != null) {
                chapterExamService.associateDelete(chapter.getChapterExam());
            }
            log.info("删除章节:" + chapter.toString());
            if (chapterMapper.delete(chapter.getId()) == 0) {
                log.warn("章节记录删除失败");
            }
        }
    }
}
