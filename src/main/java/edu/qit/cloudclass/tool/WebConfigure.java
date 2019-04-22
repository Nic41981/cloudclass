package edu.qit.cloudclass.tool;

import edu.qit.cloudclass.tool.intercepors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author nic
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebConfigure implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final TeacherIdentifyInterceptor teacherIdentifyInterceptor;
    private final TeacherCourseInterceptor teacherCourseInterceptor;
    private final TeacherChapterInterceptor teacherChapterInterceptor;
    private final FinalExamInterceptor finalExamInterceptor;
    private final ChapterExamInterceptor chapterExamInterceptor;
    private final StudentCourseInterceptor studentCourseInterceptor;
    private final SpinnerInterceptor spinnerInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**").addResourceLocations("file:/usr/cloudclass/files/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/teacher/**","/student/**","/exam/**","/upload/**","/course/chapter/list");
        registry.addInterceptor(teacherIdentifyInterceptor).addPathPatterns("/teacher/**");
        registry.addInterceptor(teacherCourseInterceptor).addPathPatterns("/teacher/course/**").excludePathPatterns("/teacher/course/list","/teacher/course/spinner","/teacher/course","/teacher/course/notice");
        registry.addInterceptor(teacherChapterInterceptor).addPathPatterns("/teacher/chapter/**").excludePathPatterns("/teacher/chapter/list","/teacher/chapter");
        registry.addInterceptor(finalExamInterceptor).addPathPatterns("/exam/final/**").excludePathPatterns("/exam/final");
        registry.addInterceptor(chapterExamInterceptor).addPathPatterns("/exam/chapter/**").excludePathPatterns("/exam/chapter");
        registry.addInterceptor(studentCourseInterceptor).addPathPatterns("/student/course/**").excludePathPatterns("/student/course/list","/student/course/spinner");
        registry.addInterceptor(spinnerInterceptor).addPathPatterns("/spinner/chapter","/spinner/exam");
    }
}