package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.tool.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface TChapterService {
    ServerResponse chapter(Chapter chapter, MultipartFile video);

    ServerResponse chapterModify(Chapter chapter);

    ServerResponse chapterDelete(String chapterId);

    void associateDelete(String courseId);
}
