package fr.codeonce.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Book {
    private int id;
    private String name;
    private int totalPages;
    private int authorId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public Book(int id, String name, int totalPages, int authorId) {
		super();
		this.id = id;
		this.name = name;
		this.totalPages = totalPages;
		this.authorId = authorId;
	}
    
}
