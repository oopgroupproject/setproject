import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class SetBoard extends JFrame {

    private CardUI[][] cardMatrix = new CardUI[3][4];
    Card first, second, third;
    Card cardClass = new Card();
    Deck deckClass = new Deck();
    ArrayList<Card> deck;
    ArrayList<Card> board = new ArrayList<>();
    int points = 0;
    JPanel cardsPanel = new JPanel();
    JPanel scorePanel = new JPanel();
    JLabel cardLeftLabel;
    JLabel setLabel;

    public SetBoard() {
        super("Set Card Game");
        deck = deckClass.createDeck();
        moveCards(Deck.shuffle(deck), board, 12);

        while (!cardClass.setExists(board)) {
            moveCards(board, deck, 12);
            moveCards(Deck.shuffle(deck), board, 12);
        }

        updateBoard();

        cardsPanel.setBackground(Color.GRAY);
        cardsPanel.setLayout(new GridLayout(3, 4));
        cardsPanel.setSize(700, 600);
        scorePanel.setBackground(new Color(197, 190, 104));
        scorePanel.setLayout(new GridLayout(1, 2));
        scorePanel.setSize(700, 100);
        cardLeftLabel = new JLabel("Card Left : " + deck.size());
        cardLeftLabel.setFont(new Font("Serif", Font.PLAIN, 50));
        setLabel = new JLabel("Points : " + points);
        setLabel.setFont(new Font("Serif", Font.PLAIN, 50));
        scorePanel.add(cardLeftLabel);
        scorePanel.add(setLabel);
        setLayout(new BorderLayout());
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new CheckOnExit());
        setSize(800, 800);
        add(cardsPanel, BorderLayout.NORTH);
        add(scorePanel);
        setVisible(true);

        if(deck.size() == 0 && cardClass.isSetPresent(board) == false) {
            System.out.println("Game over.");
        }
    }

    private void updateBoard() {
        String fileName;
        Card card;
        int index = 0;
        for (int i = 0; i < cardMatrix.length; i++) {
            for (int j = 0; j < cardMatrix[0].length; j++) {
                card = board.get(index++);
                if(cardMatrix[i][j] != null){
                    cardsPanel.remove(cardMatrix[i][j]);
                }

                cardMatrix[i][j] = new CardUI(i, j, card.getColor(), card.getShading(), card.getShape(), card.getNumber());
                fileName = "/images/" + card.toString() + ".jpeg";
                try {
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(this.getClass().getResource(fileName)).getImage().getScaledInstance(170, 180, Image.SCALE_DEFAULT));
                    cardMatrix[i][j].setIcon(imageIcon);
                } catch (Exception e) {
                    System.out.println("File not found for" + fileName);
                }

                cardMatrix[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        CardUI clickedCard = (CardUI) e.getSource();
                        handleClickEvent(clickedCard.getCoordinates());
                    }
                });
                cardsPanel.add(cardMatrix[i][j]);
            }
        }
    }

    private void handleClickEvent(int[] clickedCardXY) {
        int i = clickedCardXY[0];
        int j = clickedCardXY[1];
        cardMatrix[i][j].setHighlight(true);
        CardUI card = cardMatrix[i][j];
        Card tempCard = new Card(card.getColor(), card.getShading(), card.getShape(), card.getNum());
        if (first == null) {
            first = tempCard;
        } else if (first != null && second == null) {
            second = tempCard;
        } else if (first != null && second != null && third == null) {
            third = tempCard;
            if (cardClass.isSet(first, second, third)) {
                points++;
                for (int k = 0; k < board.size(); k++) {
                    String tableCardString = board.get(k).toString();
                    if(first.toString().equals(tableCardString) ||
                            second.toString().equals(tableCardString) ||
                            third.toString().equals(tableCardString)) {
                        board.remove(k);
                    }
                }
                moveCards(deck, board, 3);
                updateBoard();
                updateTitleBoard();
                Card.getAllSets(board);
            }
            first = second = third = null;
            setColorToDefault();
        }
    }

    private void updateTitleBoard() {
        cardLeftLabel.setText("Card Left : " + deck.size());
        setLabel.setText("Points : " + points);
    }

    private void setColorToDefault() {
        for (int i = 0; i < cardMatrix.length; i++) {
            for (int j = 0; j < cardMatrix[0].length; j++) {
                cardMatrix[i][j].setHighlight(false);
            }
        }
    }

    void moveCards(ArrayList<Card> from, ArrayList<Card> to, int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            if (from.isEmpty()) break;
            to.add(from.remove(from.size() - 1));
        }
    }

    private class CheckOnExit extends WindowAdapter implements WindowListener {
        public void windowClosing(WindowEvent e) {
            ExitWindow window = new ExitWindow();
            window.setVisible(true);
        }
    }

    private class ExitWindow extends JFrame implements ActionListener {
        public ExitWindow() {
            setSize(250, 250);
            getContentPane().setBackground(new Color(86, 187, 241));
            setLayout(new BorderLayout());

            JLabel confirmLabel = new JLabel("Are you sure you want to end the game?");
            add(confirmLabel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.blue);
            buttonPanel.setLayout(new FlowLayout());

            JButton exitButton = new JButton("Yes");
            exitButton.addActionListener(this);
            buttonPanel.add(exitButton);

            JButton cancelButton = new JButton("No");
            cancelButton.addActionListener(this);
            buttonPanel.add(cancelButton);

            add(buttonPanel, BorderLayout.SOUTH);

        }

        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("Yes")) {
                System.exit(0);
            } else {
                dispose();
            }
        }
    }
}





