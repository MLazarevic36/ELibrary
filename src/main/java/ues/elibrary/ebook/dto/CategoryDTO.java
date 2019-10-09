package ues.elibrary.ebook.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import ues.elibrary.ebook.entity.Category;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO implements Serializable {

    private Long id;
    private String name;

    public CategoryDTO(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category category) {
        this(category.getId(), category.getName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}