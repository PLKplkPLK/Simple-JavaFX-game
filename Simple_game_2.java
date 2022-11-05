import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.GraphicsContext.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;


public class Simple_game_2 extends Application implements ChangeListener<Number>
 {
  public class Ball{
    int x0 = 25;
    int y0 = 400;
    double v0 = 20;
    double a = 30;
    double x = 0;
    double y = 0;
  };
  
  private static final int FRAME_WIDTH  = 640;
  private static final int FRAME_HEIGHT = 480;  

  int x, y;

  GraphicsContext gc;
  Canvas canvas;
  Slider alpha, v;
  Ball ball = new Ball();
  double time;
  Timeline timeline; 
  int flag = 0;

      
  public static void main(String[] args) 
   {
    launch(args);
   }


    @Override
    public void start(Stage primaryStage) 
     {
      AnchorPane root = new AnchorPane();
      primaryStage.setTitle("Volleyball");

      canvas = new Canvas(FRAME_WIDTH, FRAME_HEIGHT);
      canvas.setOnMousePressed(this::mouse);

      gc = canvas.getGraphicsContext2D();
      
      gc.setFill(Color.GREEN);
      gc.fillRect(40, 440, 555, 5);
      gc.setFill(Color.BLACK);
      gc.fillRect(305, 240, 5, 200); 
      gc.setFill(Color.ORANGE);

      primaryStage.setResizable(false);
      
      
      x = 10;
      y = 10;
          
      root.getChildren().add(canvas);    
    
      Button btn = new Button();
      btn.setText("Play");
      btn.setOnAction(this::play);    

      root.getChildren().add(btn);
      AnchorPane.setBottomAnchor( btn, 5.0d );


      Slider alpha, v;

      alpha = new Slider(30, 80, 5);
      alpha.setShowTickMarks(true);
      alpha.setShowTickLabels(true);
      alpha.valueProperty().addListener(new ChangeListener<Number>() 
                             {
                              public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) 
                   {  
                    ball.a = (double)new_val;
                   }
                 });
                   
                   
                   
      root.getChildren().add(alpha);      

      AnchorPane.setBottomAnchor( alpha, 2.0d );
      AnchorPane.setLeftAnchor( alpha, 150.0d );      
      
      
      v = new Slider(20, 100, 10);
      v.setShowTickMarks(true);      
      v.setShowTickLabels(true);
      v.valueProperty().addListener(this::changed);
      
      root.getChildren().add(v);
            
      AnchorPane.setBottomAnchor( v, 2.0d );
      AnchorPane.setLeftAnchor( v, 300.0d );            
      
      Scene scene = new Scene(root);
      primaryStage.setTitle("Volleybal");
      primaryStage.setScene( scene );
      primaryStage.setWidth(FRAME_WIDTH + 10);
      primaryStage.setHeight(FRAME_HEIGHT+ 80);
      primaryStage.show();   
    }


    public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) 
     {    
      System.out.println("v=" + new_val);
      ball.v0 = (double)new_val;
     }

    
    private void step()
     {
      
      time += 0.2;
      gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
      gc.setFill(Color.GREEN);
      gc.fillRect(40, 440, 555, 5);
      gc.setFill(Color.BLACK);
      gc.fillRect(305, 240, 5, 200); 
      gc.setFill(Color.ORANGE);
      gc.fillOval(ball.x, ball.y, 10, 10);
      ball.x = ball.x0 + ball.v0*Math.cos(Math.toRadians(ball.a)) * time;
      ball.y = ball.y0 - ball.v0*Math.sin(Math.toRadians(ball.a)) * time + (9.86 * time * time)/2;
      
      if(ball.y >= 440){
         timeline.stop();
      }
      if(ball.x >= 633){
         timeline.stop();
      }
    }
     
   private void mouse(MouseEvent e)
    {
      ball.x0 = (int)e.getX();
      ball.y0 = (int)e.getY();
      if (ball.y0 >= 440){
          ball.y0 = 430;
      }
      gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
      gc.setFill(Color.GREEN);
      gc.fillRect(40, 440, 555, 5);
      gc.setFill(Color.BLACK);
      gc.fillRect(305, 240, 5, 200); 
      gc.setFill(Color.ORANGE);
      gc.fillOval(ball.x0, ball.y0, 10, 10);
    }   
    
   private void play(ActionEvent e)
    {
     time = 0;
     ball.x = ball.x0;
     ball.y = ball.y0;
     
     timeline = new Timeline(new KeyFrame(Duration.millis(50), e1->step()));

//     timeline.setCycleCount(Timeline.INDEFINITE);
     timeline.setCycleCount(200);
     timeline.play();
     
    }    
    
}    
