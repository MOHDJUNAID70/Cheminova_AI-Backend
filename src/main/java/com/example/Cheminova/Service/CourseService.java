package com.example.Cheminova.Service;

import com.example.Cheminova.DTOs.Request.CourseRequest;
import com.example.Cheminova.DTOs.Request.RatingRequest;
import com.example.Cheminova.DTOs.Response.CourseResponse;
import com.example.Cheminova.Exception.CustomException;
import com.example.Cheminova.Mapper.CourseMapper;
import com.example.Cheminova.Model.Courses;
import com.example.Cheminova.Repository.CourseRepository;
import com.example.Cheminova.Specification.CourseSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Transactional
    public void addCourse(CourseRequest request) {
        Courses newCourse = new Courses();
        newCourse.setTitle(request.getTitle());
        newCourse.setDescription(request.getDescription());
        newCourse.setCategory(request.getCategory());
        newCourse.setDuration(request.getDuration());
        newCourse.setPrice(request.getPrice());
        newCourse.setLecture(request.getLecture());

        String skills= objectMapper.writeValueAsString(request.getSkills());

        newCourse.setSkills(skills);
        newCourse.setImage("https://images.unsplash.com/photo-1498050108023-c5249f4df085");

        courseRepository.save(newCourse);
    }

    @Transactional
    public void updateCourse(Integer id, CourseRequest request) {
        Courses course = courseRepository.findById(id)
                .orElseThrow(() -> new CustomException("Course not found"));

        if (request.getTitle() != null)       course.setTitle(request.getTitle());
        if (request.getDescription() != null) course.setDescription(request.getDescription());
        if (request.getCategory() != null)    course.setCategory(request.getCategory());
        if (request.getDuration() != null)    course.setDuration(request.getDuration());
        if (request.getLecture() != null)     course.setLecture(request.getLecture());
        if (request.getPrice() != null)       course.setPrice(request.getPrice());
        if (request.getSkills() != null) {
            course.setSkills(objectMapper.writeValueAsString(request.getSkills()));
        }

        courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Integer id) {
        courseRepository.deleteById(id);
    }

    @Transactional
    public ResponseEntity<?> updateRating(Integer id, RatingRequest request) {
        if (request.getRating() < 1.0 || request.getRating() > 5.0) {
            return ResponseEntity.badRequest().body("Rating must be between 1.0 and 5.0");
        }
        Courses course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // calculate new average rating
        // formula: (currentRating * students + newRating) / (students + 1)
        Double currentRating = course.getRating();
        Integer currentStudents = course.getStudents();

        Double newAvgRating = ((currentRating * currentStudents) + request.getRating()) / (currentStudents + 1);

        // round to 1 decimal place
        newAvgRating = Math.round(newAvgRating * 10.0) / 10.0;

        course.setRating(newAvgRating);
        courseRepository.save(course);

        return ResponseEntity.ok("Rating updated. New rating: " + newAvgRating);
    }

    @Transactional
    public ResponseEntity<?> enrollStudent(Integer id) {
        Courses courses= courseRepository.findById(id)
                .orElseThrow(() -> new CustomException("Course not found"));
        courses.setStudents(courses.getStudents() + 1);
        courseRepository.save(courses);
        return new ResponseEntity<>("Student enrolled successfully", HttpStatus.OK);
    }

    public Page<CourseResponse> getAllCourses(Pageable pageable, String title, Integer minDuration,
                                              Integer maxDuration, Double minPrice, Double maxPrice)
    {
        Specification<Courses> spec= CourseSpecification.getSpecification(title, minDuration, maxDuration, minPrice, maxPrice);

        return  courseRepository.findAll(spec, pageable).map(courseMapper::toCourseResponse);
    }
}
