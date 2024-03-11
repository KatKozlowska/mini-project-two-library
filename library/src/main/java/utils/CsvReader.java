package utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import data.Book;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
  public static List<Book> readBooksFromFile(String filePath) {

    // Setting up the CSV Schema Objects
    // Schema is a template for things ie structure, types, allowable values etc...
    CsvMapper csvMapper = new CsvMapper();
    CsvSchema csvSchema = csvMapper.schemaFor(Book.class).withHeader();

    List<Book> bookList = new ArrayList<>();

    // try with resource. This way I don't have to worry about memory leaks and closing my streams
    // :wesmart
    try (Reader fileReader = new FileReader(filePath)) {

      MappingIterator<Book> iterator =
          csvMapper.readerFor(Book.class).with(csvSchema).readValues(fileReader);
      // Read line by line, parse into book and add to list
      while (iterator.hasNext()) {
        Book book = iterator.next();
        bookList.add(book);
      }

    } catch (Exception e) {
      //
      System.out.printf("Womp Womp %s%n", e);
      throw new RuntimeException(e);
    }

    return bookList;
  }
}
