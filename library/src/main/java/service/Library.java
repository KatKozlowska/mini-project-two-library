package service;

import data.Book;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import utils.Commands;
import utils.CsvReader;

public class Library {

  private String typeOfUser;
  private static Scanner scanner;
  private Commands commands;
  private List<Book> bookList;

  public Library() {
    scanner = new Scanner(System.in);
    commands = new Commands();
    bookList = CsvReader.readBooksFromFile("src/main/resources/books_data.csv");
  }

  public static Scanner getScanner() {
    return scanner;
  }

  public static void setScanner(Scanner scanner) {
    Library.scanner = scanner;
  }

  public void enterLibrary() {
    while (true) {
      System.out.println("Welcome to the Library! Please choose one of the options");
      System.out.println(Arrays.toString(commands.getOpeningCommands()));
      int userInput = scanner.nextInt();
      switch (userInput) {
        case 1:
          typeOfUser = "visitor";
          visitor();
          break;
        case 2:
          typeOfUser = "admin";
          admin();
          break;
        default:
          System.out.println("Invalid input. Please choose from given options.");
      }
    }
  }

  public void visitor() {
    System.out.println(Arrays.toString(commands.getVisitorCommands()));
    int userInput = scanner.nextInt();
    switch (userInput) {
      case 1:
        // get all books
        bookList.stream().filter(book -> !book.isLoaned()).forEach(System.out::println);
        break;
      case 2:
        searchBooks();
      case 3:
        System.out.println(Arrays.toString(commands.getOpeningCommands()));
//        returnBook();
        break;
      default:
        System.out.println("Invalid input. Please choose from given options.");
    }
  }
  ;

  public void admin() {
    System.out.println(Arrays.toString(commands.getAdminCommands()));
    int userInput = scanner.nextInt();
    switch (userInput) {
      case 1:
        getBorrowedBooks();
        break;
      case 2:
        searchBooks();
      case 3:
        System.out.println(Arrays.toString(commands.getOpeningCommands()));
        break;
      default:
        System.out.println("Invalid input. Please choose from given options.");
    }
  }
  ;

  public void searchBooks() {
    System.out.println("Enter the title of the book you wish to borrow");
    String userInput = scanner.next();
    bookList.stream()
        .filter(
            book ->
                !book.isLoaned() && book.getTitle().toLowerCase().contains(userInput.toLowerCase()))
        .findFirst()
        .ifPresentOrElse(
            book -> {
              switch (typeOfUser) {
                case "visitor":
                  borrowBook(book);
                  break;
                case "admin":
                  break;
              }
            },
            () -> System.out.println("No Book"));
  }

  public void borrowBook(Book book) {
    System.out.println(book);
    System.out.println("Is this the book you would like to borrow? Y/N");
    String userInput = scanner.next();
    if (userInput.equalsIgnoreCase("y")) {
      book.setLoaned(true);
      book.setCouter(+1);
      System.out.printf("You have borrowed %s", book);
    } else searchBooks();
  }

  public void getBorrowedBooks() {
    System.out.println("The following books have been checked out:");
    bookList.stream().filter(Book::isLoaned).forEach(System.out::println);
  }
}


//function which would allow you tp=o return borrowed books and adds it back to the list available to loan
//  public void returnBook() {
//    System.out.println("Enter the title of the book you would like to return");
//    String userInput = scanner.next();
//    bookList.stream().filter(book -> book.isLoaned() && bookList.contains(userInput.toLowerCase())).findFirst().ifPresent(book -> book.setLoaned(false));
//
//  }