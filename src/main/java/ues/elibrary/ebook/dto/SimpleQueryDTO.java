package ues.elibrary.ebook.dto;

public class SimpleQueryDTO {

    private String field;
    private String value;
    private String searchType;

    public SimpleQueryDTO() {

    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }


}
