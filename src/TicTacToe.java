import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2020년도 2학기 
 * @author 2016136035 노기현
 * MVP 패턴
 * TicTacToe 프로그램
 */
public class TicTacToe extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		TTTModel theModel = new TTTModel();
		TTTView theView = new TTTView();
		@SuppressWarnings("unused")
		TTTPresenter thePresenter = new TTTPresenter(theModel, theView);
		primaryStage.setTitle("TicTacToe");
		primaryStage.setScene(new Scene(theView));
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	public static void main(String[] args) {
		Application.launch(args);
	}
}
