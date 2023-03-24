package inc.evil.aws.fiddle.category.web;

import java.util.List;

import inc.evil.aws.fiddle.category.facade.CategoryFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryFacade categoryFacade;

    public CategoryController(CategoryFacade categoryFacade) {
        this.categoryFacade = categoryFacade;
    }

    @GetMapping
    public List<CategoryResponse> findAll() {
        return categoryFacade.findAll();
    }

    @GetMapping("{id}")
    public CategoryResponse findById(@PathVariable String id) {
        return categoryFacade.findById(id);
    }

    @PostMapping
    public CategoryResponse create(@RequestBody CreateCategoryRequest createCategoryRequest) {
        return categoryFacade.create(createCategoryRequest);
    }
}
