package by.clevertec.service;

import by.clevertec.dto.CategoryDto;
import by.clevertec.entity.Category;
import by.clevertec.exception.CategoryNotFoundException;
import by.clevertec.mapper.CategoryMapper;
import by.clevertec.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.save(categoryMapper.toCategory(categoryDto));
        return categoryMapper.toCategoryDto(category);
    }

    public CategoryDto update(Long id,CategoryDto categoryDto) {
        return categoryMapper.toCategoryDto(
                categoryRepository.findById(id)
                        .map(category -> {
                            Category updatedCategoryDTO = categoryMapper.toCategory(categoryDto);
                            category.setName(updatedCategoryDTO.getName());
                            return categoryRepository.save(category);
                        })
                        .orElseThrow(() -> new CategoryNotFoundException("Category not found with id : " + id))
        );
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryDto getById(Long id) {
        return categoryMapper.toCategoryDto(categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id : " + id))
        );
    }

    public List<CategoryDto> getAll() {
        return categoryMapper.toCategoryDtoList(categoryRepository.findAll());
    }

}
