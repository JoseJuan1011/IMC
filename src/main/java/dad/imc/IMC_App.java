package dad.imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC_App extends Application {
	
	// model
	private DoubleProperty pesoProperty;
	
	private DoubleProperty alturaProperty;
	
	private DoubleProperty valorIMCProperty;
	
	// view
	
	//pesoHBox
	private Label pesoLabel;
	private TextField pesoTextField;
	private Label kgLabel;
	
	//alturaHBox
	private Label alturaLabel;
	private TextField alturaTextField;
	private Label cmLabel;
	
	//imcHBox
	private Label imcLabel;
	private Label valorIMCLabel;
	
	private Label estadoLabel;
	
	private HBox pesoHBox;
	private HBox alturaHBox;
	private HBox imcHBox;
	
	private VBox root;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		pesoLabel = new Label("Peso: ");
		
		pesoTextField = new TextField();
		
		kgLabel = new Label("kg");
		
		pesoHBox = new HBox();
		pesoHBox.setSpacing(5);
		pesoHBox.setAlignment(Pos.CENTER);
		pesoHBox.getChildren().addAll(pesoLabel, pesoTextField, kgLabel);
		
		alturaLabel = new Label("Altura: ");
		
		alturaTextField = new TextField();
		
		cmLabel = new Label("cm");
		
		alturaHBox = new HBox();
		alturaHBox.setSpacing(5);
		alturaHBox.setAlignment(Pos.CENTER);
		alturaHBox.getChildren().addAll(alturaLabel, alturaTextField, cmLabel);
		
		imcLabel = new Label("IMC:");
		
		valorIMCLabel = new Label("(peso * altura ^ 2)");
		
		imcHBox = new HBox();
		imcHBox.setSpacing(5);
		imcHBox.setAlignment(Pos.CENTER);
		imcHBox.getChildren().addAll(imcLabel, valorIMCLabel);
		
		estadoLabel = new Label("Bajo Peso | Normal | Sobrepeso | Obeso");
		
		root = new VBox();
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(pesoHBox, alturaHBox, imcHBox, estadoLabel);
		
		Scene scene = new Scene(root, 420, 300);
		
		primaryStage.setTitle("IMC");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		pesoProperty = new SimpleDoubleProperty();
		alturaProperty = new SimpleDoubleProperty();
		valorIMCProperty = new SimpleDoubleProperty();
		
		Bindings.bindBidirectional(pesoTextField.textProperty(), pesoProperty, new NumberStringConverter());
		Bindings.bindBidirectional(alturaTextField.textProperty(), alturaProperty, new NumberStringConverter());
		
		DoubleExpression expressionAltura = alturaProperty.divide(100).multiply(alturaProperty.divide(100));
		valorIMCProperty.bind(pesoProperty.divide(expressionAltura));
		
		valorIMCLabel.textProperty().bindBidirectional(valorIMCProperty, new NumberStringConverter());
		
		pesoTextField.textProperty().addListener((obv, ov, nv) -> {
			if (nv.isBlank()) {
				estadoLabel.setText("Bajo Peso | Normal | Sobrepeso | Obeso");
			}
		});
		
		alturaTextField.textProperty().addListener((obv, ov, nv) -> {
			if (nv.isBlank()) {
				estadoLabel.setText("Bajo Peso | Normal | Sobrepeso | Obeso");
			}
		});
		
		valorIMCProperty.addListener((obv, ov, nv) -> {
			double imc = nv.doubleValue();
			if (imc < 18.5) {
				estadoLabel.setText("Bajo Peso");
			}
			else if (imc >= 18.5 && imc < 25) {
				estadoLabel.setText("Normal");
			}
			else if (imc >= 25 && imc < 30) {
				estadoLabel.setText("Sobrepeso");
			}
			else if (imc >= 30) {
				estadoLabel.setText("Obeso");
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

}
