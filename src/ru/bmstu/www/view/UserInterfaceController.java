package ru.bmstu.www.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ru.bmstu.www.player.impl.AudioPlayer;
import javafx.stage.Stage;;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class UserInterfaceController implements Initializable {

	@FXML
	private Label labelForSlider_0, labelForSlider_1, labelForSlider_2, labelForSlider_3, labelForSlider_4,
			labelForSlider_5, labelForSlider_6;

	@FXML
	private Slider Slider_0, Slider_1, Slider_2, Slider_3, Slider_4, Slider_5, Slider_6, soundSlider, timeSlider,
			overdriveSlider, delaySlider;

	@FXML
	private LineChart graph;

	@FXML
	private NumberAxis xAxis, yAxis;

	@FXML
	CheckBox overdriveCheck, delayCheck, graphCheck;

	private boolean graphFlag = false;

	private XYChart.Data[] series1Data;
	private XYChart.Data[] series2Data;

	private AudioPlayer audioPlayer;
	private Thread playThread, graphThread;

	private int countOfPointsOnPlot = 512;

	/**
	 * Initializes the controller class.
	 * 
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		this.labelInitialize();
		XYChart.Series<Integer, Number> series1 = new XYChart.Series<>();
		XYChart.Series<Integer, Number> series2 = new XYChart.Series<>();
		series1.setName("Модифицированный");
		// series1.getChart()
		series2.setName("Оригинал");
		series1Data = new XYChart.Data[this.countOfPointsOnPlot]; // 256
		series2Data = new XYChart.Data[this.countOfPointsOnPlot];
		System.out.println(this.series1Data.length);
		for (int i = 0; i < series1Data.length; i++) {
			series1Data[i] = new XYChart.Data<>(i, 0);
			series1.getData().add(series1Data[i]);

			series2Data[i] = new XYChart.Data<>(i, 0);
			series2.getData().add(series2Data[i]);
		}
		ObservableList<XYChart.Series<Integer, Number>> lineChartData = FXCollections.observableArrayList();

		lineChartData.add(series1);
		lineChartData.add(series2);

		graph.setData(lineChartData);
		graph.createSymbolsProperty();
		graph.setAnimated(false);
		this.graph.getYAxis();
		this.yAxis.setLowerBound(-0.2);
		this.yAxis.setUpperBound(0.3);
		this.yAxis.setAnimated(false);

		this.checkBoxInnitial();
		this.volumeFromSlider();
	}

	@FXML
	private void clickOpen()
			throws UnsupportedAudioFileException, IOException, InterruptedException, LineUnavailableException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Audio Files", "*.wav"));
		File selectedFile = fileChooser.showOpenDialog(new Stage());

		if (selectedFile == null)
			return;
		
		if(this.audioPlayer != null)
			this.audioPlayer.stop();

		this.audioPlayer = new AudioPlayer(selectedFile);

		playThread = new Thread(this.audioPlayer);

		playThread.start();

		System.out.println("PLAY");

	}

	@FXML
	private void clickStopPlay() {
		if (this.audioPlayer != null) {
			if (!this.audioPlayer.getPause())
				this.audioPlayer.pause();
			else
				this.audioPlayer.resume();
		}

	}

	@FXML
	private void clickReset() {
		if (this.audioPlayer == null)
			return;
		Slider_0.setValue(1.0);
		Slider_1.setValue(1.0);
		Slider_2.setValue(1.0);
		Slider_3.setValue(1.0);
		Slider_4.setValue(1.0);
		Slider_5.setValue(1.0);
		Slider_6.setValue(1.0);
		soundSlider.setValue(0.65);
		this.overdriveSlider.setValue(1.0);
		this.delaySlider.setValue(1.0);

	}

	// Listen for Slider value changes
	private void labelInitialize() {

		Slider_0.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String str = String.format("%.1f", (newValue.doubleValue()));
				labelForSlider_0.setText(str);
				audioPlayer.getEqualizer().getFilter((short) 0).setGain((float) newValue.doubleValue());

			}
		});

		Slider_1.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String str = String.format("%.1f", (newValue.doubleValue()));
				labelForSlider_1.setText(str);
				audioPlayer.getEqualizer().getFilter((short) 1).setGain((float) newValue.doubleValue());
			}
		});

		Slider_2.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String str = String.format("%.1f", (newValue.doubleValue()));
				labelForSlider_2.setText(str);
				audioPlayer.getEqualizer().getFilter((short) 2).setGain((float) newValue.doubleValue());
			}
		});

		Slider_3.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String str = String.format("%.1f", (newValue.doubleValue()));
				labelForSlider_3.setText(str);
				audioPlayer.getEqualizer().getFilter((short) 3).setGain((float) newValue.doubleValue());
			}
		});

		Slider_4.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String str = String.format("%.1f", (newValue.doubleValue()));
				labelForSlider_4.setText(str);
				audioPlayer.getEqualizer().getFilter((short) 4).setGain((float) newValue.doubleValue());
			}
		});

		Slider_5.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String str = String.format("%.1f", (newValue.doubleValue()));
				labelForSlider_5.setText(str);
				audioPlayer.getEqualizer().getFilter((short) 5).setGain((float) newValue.doubleValue());
			}
		});

		Slider_6.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String str = String.format("%.1f", (newValue.doubleValue()));
				labelForSlider_6.setText(str);
				audioPlayer.getEqualizer().getFilter((short) 6).setGain((float) newValue.doubleValue());
			}
		});

		overdriveSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				audioPlayer.setOverdriveCoef(newValue.doubleValue());
			}
		});

		delaySlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				audioPlayer.setDelayCoef(newValue.doubleValue());
			}
		});

	}

	private void checkBoxInnitial() {
		this.delayCheck = new CheckBox();
		this.delayCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			}
		});
	}

	private void volumeFromSlider() {
		soundSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				audioPlayer.setVolume(newValue.doubleValue());
			}
		});
	}

	@FXML
	private void createDelay() {
		System.out.println("Delay");
		if (!this.audioPlayer.delayIsActive())
			this.audioPlayer.setDelay(true);
		else
			this.audioPlayer.setDelay(false);
	}

	@FXML
	private void createOverdrive() {
		System.out.println("Overdrive");
		if (!this.audioPlayer.overdriveIsActive())
			this.audioPlayer.setOverdrive(true);
		else
			this.audioPlayer.setOverdrive(false);
	}

	@FXML
	private void clickClose() {
		if (this.audioPlayer != null) {
			this.audioPlayer.getEqualizer().close();
			this.audioPlayer.stop();
		}

		System.exit(0);
	}

	@FXML
	private void createGraph() {
		if (this.graphFlag == false) {
			this.graphFlag = true;
		} else
			this.graphFlag = false;

		this.graphThread = new Thread(() -> {
			while (this.graphFlag) {
				if (this.graphFlag == false)
					for (;;) {
						try {
							if (this.graphFlag == true)
								break;
							this.graphThread.sleep(150);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				if (this.audioPlayer.getFftReady()) {
					for (int j = 0; j < this.audioPlayer.getFTvlOutput().length; j += 1) {
						this.series2Data[j].setYValue(Math.log10(this.audioPlayer.getFTvlInput()[j] * 0.1) / 10);
						this.series1Data[j].setYValue(Math.log10(this.audioPlayer.getFTvlOutput()[j]) / 10);
					}
				}
				try {
					this.graphThread.sleep(90);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		this.graphThread.start();
	}

}
