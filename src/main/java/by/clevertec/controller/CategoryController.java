package by.clevertec.controller;

import by.clevertec.dto.CategoryDto;
import by.clevertec.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> findCategoryById(@PathVariable("categoryId") @Valid Long categoryId) {
        CategoryDto categoryDto = categoryService.findById(categoryId);
        return ResponseEntity.ok()
                .body(categoryDto);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAllCategories() {
        return ResponseEntity.ok()
                .body(categoryService.findAll());
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.create(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") @Valid Long id,
                                                      @RequestBody @Valid CategoryDto categoryDto) {
        CategoryDto newCategory = categoryService.update(id, categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newCategory);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("categoryId") @Valid Long categoryId) {
        categoryService.delete(categoryId);
    }
}
