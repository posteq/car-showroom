package by.clevertec.mapper;

import by.clevertec.dto.CategoryDto;
import by.clevertec.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "cars", ignore = true)
    Category toCategory(CategoryDto categoryDto);

    CategoryDto toCategoryDto(Category category);

}
