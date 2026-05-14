package com.example.Cheminova.Controller;

import com.example.Cheminova.DTOs.Request.CourseRequest;
import com.example.Cheminova.DTOs.Request.RatingRequest;
import com.example.Cheminova.DTOs.Response.CourseResponse;
import com.example.Cheminova.Service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Course Controller", description = "Endpoints for managing courses, including retrieval, rating, and reviews")
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-courses")
    public Page<CourseResponse> getAllCourses(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "rating", required = false) String sortBy,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer minDuration,
            @RequestParam(required = false) Integer maxDuration,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    )
    {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(sortBy).descending());
        return courseService.getAllCourses(pageable, title, minDuration, maxDuration, minPrice, maxPrice);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-course")
    public ResponseEntity<String> addCourse(@RequestBody CourseRequest request){
        courseService.addCourse(request);
        return new ResponseEntity<>("Course added successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-course/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable Integer id, @RequestBody CourseRequest request){
        courseService.updateCourse(id, request);
        return new ResponseEntity<>("Course updated successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Integer id){
        courseService.deleteCourse(id);
        return new ResponseEntity<>("Course deleted successfully", HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}/rate")
    public ResponseEntity<?> rateCourse(@PathVariable Integer id,
                                        @RequestBody RatingRequest request) {
        return courseService.updateRating(id, request);
    }

//    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}/enroll")
    public ResponseEntity<?> enrollStudent(@PathVariable Integer id) {
        return courseService.enrollStudent(id);
    }

}
