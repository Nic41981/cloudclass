package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ChapterMapper;
import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.service.ChapterService;
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
public class ChapterServiceImpl implements ChapterService {

    private final ChapterMapper chapterMapper;


    @Override
    public ServerResponse<List<Chapter>> chapterList(String courseId) {
        List<Chapter> chapterList = chapterMapper.chapterList(courseId);
        return ServerResponse.createBySuccess("查询成功",chapterList);
    }

    @Override
    public ServerResponse chapter(Chapter chapter) {
        chapter.setId(Tool.uuid());
        if (chapter.getNum() == 0) {
            //追加章节-章节序号递增
            chapter.setNum(chapterMapper.findNextNum(chapter.getCourse()));
        }
        else {
            //指定章节-后面的章节序号递增
            chapterMapper.updateNumBeforeInster(chapter.getNum(),chapter.getCourse());
        }
        log.info("创建章节:" + chapter.toString());
        if (chapterMapper.insert(chapter) == 0){
            log.error("创建失败");
            ServerResponse.createByError("创建失败");
        }
        log.info("创建成功");
        return ServerResponse.createBySuccessMsg("创建成功");
    }

    @Override
    public ServerResponse chapterModify(Chapter chapter) {
        log.info("修改章节:" + chapter.toString());
        if (chapterMapper.chapterModify(chapter) == 1){
            log.info("修改成功");
            return ServerResponse.createBySuccessMsg("修改成功");
        }
        log.error("修改异常");
        return ServerResponse.createByError("修改异常");
    }

    @Override
    public ServerResponse chapterDelete(String courseId,String chapterId) {
        log.info("删除章节:" + chapterId);
        int num = chapterMapper.findNumByPrimaryKey(chapterId);
        if (chapterMapper.deleteChapter(chapterId)!=1){
            log.error("删除异常");
            return ServerResponse.createByError("删除异常");
        }
        chapterMapper.updateNumAfterDelete(num,courseId);
        log.info("删除成功");
        return ServerResponse.createBySuccessMsg("删除成功");
    }

}
