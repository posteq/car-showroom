package by.clevertec.service;

import by.clevertec.dto.CategoryDto;
import by.clevertec.entity.Category;
import by.clevertec.exception.CategoryNotFoundException;
import by.clevertec.mapper.CategoryMapper;
import by.clevertec.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryRepository.save(categoryMapper.toCategory(categoryDto));
        return categoryMapper.toCategoryDto(category);
    }

    @Transactional
    public CategoryDto update(Long id,CategoryDto categoryDto) {
        return categoryMapper.toCategoryDto(
                categoryRepository.findById(id)
                        .map(category -> {
                            Category updatedCategoryDTO = categoryMapper.toCategory(categoryDto);
                            category.setName(updatedCategoryDTO.getName());
                            return categoryRepository.save(category);
                        })
                        .orElseThrow(() -> new CategoryNotFoundException(id))
        );
    }

    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Transactional
    public CategoryDto findById(Long id) {
        return categoryMapper.toCategoryDto(categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id))
        );
    }

    @Transactional
    public List<CategoryDto> findAll() {
        return categoryMapper.toCategoryDtoList(categoryRepository.findAll());
    }

}
