package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ChapterMapper;
import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.service.ChapterService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChapterServiceImpl implements ChapterService {

    private final ChapterMapper chapterMapper;


    @Override
    public ServerResponse<List<Chapter>> chapterList(String courseId) {
        List<Chapter> chapterList = chapterMapper.chapterList(courseId);

        if (chapterList.size()==0){
            log.info("查询内容为空!");
            return ServerResponse.createBySuccessMsg("查询内容为空");
        }

        log.info("查询成功!");
        return ServerResponse.createBySuccess("查询成功",chapterList);

    }

    @Override
    public ServerResponse<Map> chapter(Chapter chapter) {
        int num = chapterMapper.searchNum(chapter.getCourse());
        chapter.setNum(num);

        log.info(chapter.getCourse()+"章节为"+chapter.getNum());
        if (chapterMapper.insert(chapter)==0){
            ServerResponse.createByError("插入失败");
        }

        log.info(chapter.getCourse()+"课程插入"+chapter.getNum()+"章节成功");
        Map<String,String> map = new HashMap<>();
        map.put("id",chapter.getId());
        return ServerResponse.createBySuccess("插入成功",map);

    }

    @Override
    public ServerResponse chapterModify(Chapter chapter) {
        //判断章节存不存在
        if (chapterMapper.selectChapter(chapter.getId())==0){
            log.info(chapter.getId()+"章节不存在");
            ServerResponse.createBySuccessMsg("该章节不存在");
        }
        log.info(chapter.getId()+"该章节修改成功!");
        if (chapterMapper.chapterModify(chapter)==1){
            return ServerResponse.createBySuccessMsg("该章节修改成功");
        }
        return ServerResponse.createByError(ResponseCode.ERROR.getStatus(),"修改异常");
    }

    @Override
    public ServerResponse chapterDelete(String chapterId) {
        //判断章节存不存在
        if (chapterMapper.selectChapter(chapterId)==0){
            log.info(chapterId+"章节不存在!");
            ServerResponse.createByError("该章节不存在");
        }

        log.info(chapterId+"章节删除成功!");
        if (chapterMapper.deleteChapter(chapterId)==1){
            return ServerResponse.createBySuccessMsg("该章节删除成功");
        }
        return ServerResponse.createByError(ResponseCode.ERROR.getStatus(),"删除异常");
    }

}
