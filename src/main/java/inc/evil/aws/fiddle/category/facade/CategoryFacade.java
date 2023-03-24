package inc.evil.aws.fiddle.category.facade;

import java.util.List;

import inc.evil.aws.fiddle.category.domain.Category;
import inc.evil.aws.fiddle.category.service.CategoryService;
import inc.evil.aws.fiddle.category.web.CategoryResponse;
import inc.evil.aws.fiddle.category.web.CreateCategoryRequest;
import org.springframework.stereotype.Component;

@Component
public class CategoryFacade {
    private final CategoryService categoryService;

    public CategoryFacade(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<CategoryResponse> findAll() {
        return categoryService.findAll()
                              .stream()
                              .map(CategoryResponse::from)
                              .toList();
    }

    public CategoryResponse findById(String id) {
        Category category = categoryService.findById(id);
        return CategoryResponse.from(category);
    }

    public CategoryResponse create(CreateCategoryRequest createCategoryRequest) {
        Category categoryToCreate = createCategoryRequest.toCategory();
        Category createdCategory = categoryService.create(categoryToCreate);
        return CategoryResponse.from(createdCategory);
    }
}
