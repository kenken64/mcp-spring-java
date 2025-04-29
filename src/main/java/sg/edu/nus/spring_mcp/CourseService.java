package sg.edu.nus.spring_mcp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class CourseService {
    
    private static final Logger log = 
                LoggerFactory.getLogger(CourseService.class);
    
    private List<Course> courses = new ArrayList<>();
    
    
    @Tool(name="get_courses" , description="Get a list of courses")
    public List<Course> getCourses(){
        return courses;
    }

    @Tool(name="get_course", description="Get a single course by title")
    public Course getCourse(String title){
        return courses.stream()
                .filter(course -> course.title()
                .equals(title))
                .findFirst()
                .orElse(null);
    }

    @Tool(name="add_course", description="Add new course")
    public void addCourse(String title, String url){
        courses.add(new Course(title, url));
    }

    @Tool(name="update_course", description="Update existing course")
    public void updateCourse(String title, String url){
        boolean updated = false;
    
        // First try to update an existing course
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            if (course.title().equals(title)) {  // Use exact matching instead of contains()
                courses.set(i, new Course(title, url));
                updated = true;
                break;
            }
        }
        
        // If no course was found and updated, add a new one
        if (!updated) {
            courses.add(new Course(title, url));
        }
    }

    @Tool(name="remove_course", description="Remove course from the list")
    public String removeCourse(String title){
        int initialSize = courses.size();
    
        // Use removeIf for a cleaner approach to remove matching elements
        courses.removeIf(course -> course.title().contains(title));
        
        int removedCount = initialSize - courses.size();
        
        if (removedCount > 0) {
            return "Removed " + removedCount + " course(s) with title: " + title;
        } else {
            return "No course found with title: " + title;
        }
    }

    @PostConstruct
    public void init(){
        var c1 = new Course("Code Your Own Llama 4 LLM from Scratch – Full Course", "https://www.youtube.com/watch?v=biveB0gOlak");
        var c2 = new Course("Essential Machine Learning and AI Concepts Animated", "https://www.youtube.com/watch?v=PcbuKRNtCUc");

        courses.addAll(List.of(
            c1,
            c2
        ));
    }
}
