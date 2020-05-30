package by.jrr.moodle.service;

import by.jrr.moodle.bean.Course;
import by.jrr.moodle.bean.Topic;
import by.jrr.moodle.repository.CourseRepository;
import by.jrr.moodle.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public Page<Course> findAll(String page, String items) {
        Page<Course> topics;
        try {
            topics = courseRepository.findAll(PageRequest.of(Integer.valueOf(page), Integer.valueOf(items)));
        } catch (Exception ex) {
            topics = courseRepository.findAll(PageRequest.of(Integer.valueOf(0), Integer.valueOf(10)));
        }
        return topics;
    }

    public Course create(Course topic) {
        topic = courseRepository.save(topic);
        return topic;
    }
    public Course update(Course topic) {
        topic = courseRepository.save(topic);
        return topic;
    }
    public void delete() {
    }
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }




}
