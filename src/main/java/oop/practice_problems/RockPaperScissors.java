import java.util.Random;

class RockPaperScissors {

    static String playRound(String playerMove, String computerMove) {
        if (playerMove.equalsIgnoreCase(computerMove)) return "Draw";

        boolean playerWins =
                (playerMove.equalsIgnoreCase("Rock") && computerMove.equalsIgnoreCase("Scissors")) ||
                (playerMove.equalsIgnoreCase("Paper") && computerMove.equalsIgnoreCase("Rock")) ||
                (playerMove.equalsIgnoreCase("Scissors") && computerMove.equalsIgnoreCase("Paper"));

        return playerWins ? "Player Wins" : "Computer Wins";
    }

    public static void main(String[] args) {
        String[] moves = {"Rock", "Paper", "Scissors"};
        String[] playerMoves = {"Rock", "Paper", "Scissors", "Rock", "Paper"}; // predefined for demo
        Random rand = new Random();

        int wins = 0, losses = 0, draws = 0;
        int n = playerMoves.length;

        System.out.println("Round | Player Move | Computer Move | Result");
        for (int i = 0; i < n; i++) {
            String computerMove = moves[rand.nextInt(3)];
            String result = playRound(playerMoves[i], computerMove);

            System.out.println((i + 1) + "     | " + playerMoves[i] + "        | " + computerMove + "         | " + result);

            if (result.equals("Player Wins")) wins++;
            else if (result.equals("Computer Wins")) losses++;
            else draws++;
        }

        double winPercent = (wins * 100.0) / n;
        System.out.println("\nFinal Summary: Wins: " + wins + " | Losses: " + losses + " | Draws: " + draws
                + " | Win % = " + winPercent + "%");
    }
}