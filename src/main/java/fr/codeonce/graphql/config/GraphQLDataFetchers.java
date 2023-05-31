package fr.codeonce.graphql.config;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import fr.codeonce.graphql.dao.AuthorRepository;
import fr.codeonce.graphql.dao.BookRepository;
import fr.codeonce.graphql.model.Book;

@Component
public class GraphQLDataFetchers {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public GraphQLDataFetchers(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }


    public DataFetcher getBooksDataFetcher() {
        return dataFetchingEnvironment -> bookRepository.findAll();
    }

    public DataFetcher getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            int bookId = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return bookRepository.findById(bookId);
        };
    }

    public DataFetcher getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Book book = dataFetchingEnvironment.getSource();
            int authorId = book.getAuthorId();
            return authorRepository.findById(authorId);
        };
    }

    public DataFetcher getPageCountDataFetcher() {
        return dataFetchingEnvironment -> {
            Book book = dataFetchingEnvironment.getSource();
            return book.getTotalPages();
        };
    }

}