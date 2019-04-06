package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ChapterMapper;
import edu.qit.cloudclass.domain.Chapter;
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
            chapterMapper.updateNumBeforeInsert(chapter.getNum(),chapter.getCourse());
        }
        log.info("创建章节:" + chapter.toString());
        if (chapterMapper.insert(chapter) == 0){
            log.error("创建失败");
            ServerResponse.createByError("创建失败");
        }
        return ServerResponse.createBySuccessMsg("创建成功");
    }

    @Override
    public ServerResponse chapterModify(Chapter chapter) {
        log.info("修改章节:" + chapter.toString());
        if (chapterMapper.modify(chapter) == 0){
            log.error("修改失败");
            return ServerResponse.createByError("修改异常");
        }
        return ServerResponse.createBySuccessMsg("修改成功");
    }

    @Override
    public ServerResponse chapterDelete(String courseId,String chapterId) {
        //查找记录
        Chapter chapter = chapterMapper.findChapterByPrimaryKey(chapterId);
        if (chapter == null){
            return ServerResponse.createByError("删除失败");
        }
        //删除记录
        log.info("删除章节:" + chapter.toString());
        if (chapterMapper.delete(chapterId) == 0){
            log.error("删除失败");
            return ServerResponse.createByError("删除失败");
        }
        chapterMapper.updateNumAfterDelete(chapter.getNum(),courseId);
        //级联删除
        if (chapter.getVideo() != null){
            fileService.associateDelete(chapter.getVideo());
        }
        if (chapter.getTest() != null){
            log.info("习题级联删除未完成");
            //TODO 删除章节习题
        }
        return ServerResponse.createBySuccessMsg("删除成功");
    }

    @Override
    public ServerResponse associateDelete(String courseId) {
        ServerResponse<List<Chapter>> listResult = chapterList(courseId);
        if (!listResult.isSuccess()){
            log.warn("章节列表获取失败");
            return ServerResponse.createBySuccess();
        }
        List<Chapter> list = listResult.getData();
        for (Chapter chapter : list){
            if (chapter.getVideo() != null) {
                fileService.associateDelete(chapter.getVideo());
            }
            if (chapter.getTest() != null) {
                log.info("习题级联删除未完成");
                //TODO 删除章节习题
            }
            log.info("删除章节:" + chapter.toString());
            if (chapterMapper.delete(chapter.getId()) == 0){
                log.warn("章节记录删除失败");
            }
        }
        return ServerResponse.createBySuccess();
    }

}