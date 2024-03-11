package utils;

public class Commands {

  private static final String[] openingCommands = new String[] {"1: Visitor", "2: Admin"};
  private static final String[] visitorCommands =
      new String[] {"1: Get a list of all the books", "2:Borrow a book", "3: Back"};
  private static final String[] adminCommands =
      new String[] {"1: List books on loan", "2: data.Book loan history ", "3: Back"};

  public String[] getOpeningCommands() {
    return openingCommands;
  }

  public String[] getVisitorCommands() {
    return visitorCommands;
  }

  public String[] getAdminCommands() {
    return adminCommands;
  }
}
