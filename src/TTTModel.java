
/**
 * 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * 2019년도 2학기 
 * MVP 패턴
 * @author 2016136035 노기현
 * TTTModel.java
 * TicTacToe 게임에서 모델 역할
 *  1) 승자 존재 여부 검사
 *  2) 사용자 조작 결과를 받아 보드 정보 갱신
 *  3) minimax-algorithm 사용 
 *  참고 자료 : https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-3-tic-tac-toe-ai-finding-optimal-move/
 */
public class TTTModel {
	public enum CellState {CIRCLE, CROSS, EMPTY};
	private boolean player1 = true;
	public boolean player2 = false;
	private boolean gameFinished = false;
	private CellState[][] board = new CellState[3][3];
	int count = 0;
	public Move comMove;				// computer pick
	public TTTModel() {
		clear();
	}
	private boolean validIndex(int i) {
		return (i>=0&&i<3);
	}
	public void clear() {
		player1 = true;
		player2 = false;
		gameFinished = false;
		count = 0;
		for(int r=0; r<3; r++)
			for(int c=0; c<3; c++) 
				board[r][c] = CellState.EMPTY;
	}
	public boolean hasWinner() {
		gameFinished = true;
		// row test
		for(int r=0; r<3; r++) {
			CellState test = board[r][0];
			if(test!=CellState.EMPTY&&test==board[r][1]&&test==board[r][2]) return true;
		}
		// col test
		for(int c=0; c<3; c++) {
			CellState test = board[0][c];
			if(test!=CellState.EMPTY&&test==board[1][c]&&test==board[2][c]) return true;
		}
		// diagonal test
		CellState test = board[0][0];
		if(test!=CellState.EMPTY&&test==board[1][1]&&test==board[2][2]) return true;
		test = board[0][2];
		if(test!=CellState.EMPTY&&test==board[1][1]&&test==board[2][0]) return true;
		if(count<9) gameFinished = false;
		return false;
	}
	public boolean isEmpty(int r, int c) {
		assert (validIndex(r)&&validIndex(c));
		return board[r][c]==CellState.EMPTY;
	}
	public boolean hasGameFinished() {
		return gameFinished;
	}
	public boolean setCell(int r, int c) {
		assert (validIndex(r)&&validIndex(c));
		++count;
		board[r][c] = player1 ? CellState.CIRCLE : CellState.CROSS;
		player1 = !player1;
		return !player1;
	}
	public void computerPick() {
		comMove = findBestMove(board);
		player2 = !player1;
	}

	static class Move 
	{ 
	    int row, col; 
	};
	static Boolean isMovesLeft(CellState board[][]) 
	{ 
	    for (int i = 0; i < 3; i++) 
	        for (int j = 0; j < 3; j++) 
	            if (board[i][j] == CellState.EMPTY) 
	                return true; 
	    return false; 
	} 
	  
	// This is the evaluation function as discussed 
	// in the previous article ( http://goo.gl/sJgv68 ) 
	static int evaluate(CellState b[][]) 
	{ 
	    // Checking for Rows for X or O victory. 
	    for (int row = 0; row < 3; row++) 
	    { 
	        if (b[row][0] == b[row][1] && 
	            b[row][1] == b[row][2]) 
	        { 
	            if (b[row][0] == CellState.CROSS) 
	                return +10; 
	            else if (b[row][0] == CellState.CIRCLE) 
	                return -10; 
	        } 
	    } 
	  
	    // Checking for Columns for X or O victory. 
	    for (int col = 0; col < 3; col++) 
	    { 
	        if (b[0][col] == b[1][col] && 
	            b[1][col] == b[2][col]) 
	        { 
	            if (b[0][col] == CellState.CROSS) 
	                return +10; 
	  
	            else if (b[0][col] == CellState.CIRCLE) 
	                return -10; 
	        } 
	    } 
	  
	    // Checking for Diagonals for X or O victory. 
	    if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) 
	    { 
	        if (b[0][0] == CellState.CROSS) 
	            return +10; 
	        else if (b[0][0] == CellState.CIRCLE) 
	            return -10; 
	    } 
	  
	    if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) 
	    { 
	        if (b[0][2] == CellState.CROSS) 
	            return +10; 
	        else if (b[0][2] == CellState.CIRCLE) 
	            return -10; 
	    } 
	  
	    // Else if none of them have won then return 0 
	    return 0; 
	} 
	  
	// This is the minimax function. It considers all 
	// the possible ways the game can go and returns 
	// the value of the board 
	static int minimax(CellState board[][],  
	                    int depth, Boolean isMax) 
	{ 
	    int score = evaluate(board); 
	  
	    // If Maximizer has won the game  
	    // return his/her evaluated score 
	    if (score == 10) 
	        return score; 
	  
	    // If Minimizer has won the game  
	    // return his/her evaluated score 
	    if (score == -10) 
	        return score; 
	  
	    // If there are no more moves and  
	    // no winner then it is a tie 
	    if (isMovesLeft(board) == false) 
	        return 0; 
	  
	    // If this maximizer's move 
	    if (isMax) 
	    { 
	        int best = -1000; 
	  
	        // Traverse all cells 
	        for (int i = 0; i < 3; i++) 
	        { 
	            for (int j = 0; j < 3; j++) 
	            { 
	                // Check if cell is empty 
	                if (board[i][j]== CellState.EMPTY) 
	                { 
	                    // Make the move 
	                    board[i][j] = CellState.CROSS; 
	  
	                    // Call minimax recursively and choose 
	                    // the maximum value 
	                    best = Math.max(best, minimax(board,  
	                                    depth + 1, !isMax)); 
	  
	                    // Undo the move 
	                    board[i][j] = CellState.EMPTY; 
	                } 
	            } 
	        } 
	        return best; 
	    } 
	  
	    // If this minimizer's move 
	    else
	    { 
	        int best = 1000; 
	  
	        // Traverse all cells 
	        for (int i = 0; i < 3; i++) 
	        { 
	            for (int j = 0; j < 3; j++) 
	            { 
	                // Check if cell is empty 
	                if (board[i][j] == CellState.EMPTY) 
	                { 
	                    // Make the move 
	                    board[i][j] = CellState.CIRCLE; 
	  
	                    // Call minimax recursively and choose 
	                    // the minimum value 
	                    best = Math.min(best, minimax(board,  
	                                    depth + 1, !isMax)); 
	  
	                    // Undo the move 
	                    board[i][j] = CellState.EMPTY; 
	                } 
	            } 
	        } 
	        return best; 
	    } 
	} 
	  
	// This will return the best possible 
	// move for the player 
	static Move findBestMove(CellState board[][]) 
	{ 
	    int bestVal = -1000; 
	    Move bestMove = new Move(); 
	    bestMove.row = -1; 
	    bestMove.col = -1; 
	  
	    // Traverse all cells, evaluate minimax function  
	    // for all empty cells. And return the cell  
	    // with optimal value. 
	    for (int i = 0; i < 3; i++) 
	    { 
	        for (int j = 0; j < 3; j++) 
	        { 
	            // Check if cell is empty 
	            if (board[i][j] == CellState.EMPTY) 
	            { 
	                // Make the move 
	                board[i][j] = CellState.CROSS; 
	  
	                // compute evaluation function for this 
	                // move. 
	                int moveVal = minimax(board, 0, false); 
	  
	                // Undo the move 
	                board[i][j] = CellState.EMPTY; 
	  
	                // If the value of the current move is 
	                // more than the best value, then update 
	                // best/ 
	                if (moveVal > bestVal) 
	                { 
	                    bestMove.row = i; 
	                    bestMove.col = j; 
	                    bestVal = moveVal; 
	                } 
	            } 
	        } 
	    } 
	    return bestMove; 
	} 
}
