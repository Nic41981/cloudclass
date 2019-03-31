package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ChapterMapper;
import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.service.ChapterService;
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
    public ServerResponse<List<Chapter>> chapterList(String course) {
        List<Chapter> chapterList = chapterMapper.chapterList(course);
        System.out.println(course);
        if (chapterList.size()==0){
            log.info("查询内容为空!");
            return ServerResponse.createBySuccessMsg("查询内容为空");
        }

        log.info("查询成功!");
        return ServerResponse.createBySuccess(chapterList);

    }

    @Override
    public ServerResponse<Map> chapter(Chapter chapter) {
        int num = chapterMapper.SearchNum(chapter.getCourse());
        chapter.setNum(num);

        log.info(chapter.getCourse()+"章节为"+chapter.getNum());
        if (chapterMapper.insert(chapter)==0){
            ServerResponse.createByError("插入失败!");
        }

        log.info(chapter.getCourse()+"课程插入"+chapter.getNum()+"章节成功!");
        Map<String,String> map = new HashMap<>();
        map.put("id",chapter.getId());
        return ServerResponse.createBySuccess("插入成功!",map);

    }


}
