package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.tool.ServerResponse;

import java.util.List;
import java.util.Map;

public interface TChapterService {
    ServerResponse chapter(Chapter chapter);

    ServerResponse chapterModify(Chapter chapter);

    ServerResponse chapterDelete(String courseId, String chapterId);

    void associateDelete(String courseId);
}
