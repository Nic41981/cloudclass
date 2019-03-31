package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.tool.ServerResponse;
import java.util.List;

public interface ChapterService {
    ServerResponse<List<Chapter>> chapterList(String course);
    ServerResponse<Map> chapter(Chapter chapter);
}
