package ru.bmstu.www.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
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
import ru.bmstu.www.util.EqualizerUtil;
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
	private LineChart<Integer, Number> graph;

	@FXML
	private NumberAxis xAxis, yAxis;

	@FXML
	CheckBox overdriveCheck, delayCheck, graphCheck;

	private boolean graphFlag = false;

	private XYChart.Data<Integer, Number>[] series1Data;
	private XYChart.Data<Integer, Number>[] series2Data;

	private AudioPlayer audioPlayer;
	private Thread playThread;

	private int countOfPointsOnPlot = 128;

	private GraphListener graphListener = new GraphListener();

	private Map<Slider, Label> equalizerSliderToLabel = new HashMap<>();

	/**
	 * Initializes the controller class.
	 * 
	 * @param url
	 * @param rb
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		this.initializeEqualizerElements();
		this.initializeEffectElements();

		XYChart.Series<Integer, Number> series1 = new XYChart.Series<>();
		XYChart.Series<Integer, Number> series2 = new XYChart.Series<>();
		series1.setName("Модифицированный");
		// series1.getChart()
		series2.setName("Оригинал");
		series1Data = new XYChart.Data[this.countOfPointsOnPlot]; // 256
		series2Data = new XYChart.Data[this.countOfPointsOnPlot];

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
		this.graph.setCreateSymbols(false);
		this.graph.getYAxis();
		this.graph.setCache(true);

		this.yAxis.setLowerBound(-0.2);
		this.yAxis.setUpperBound(0.3);
		this.yAxis.setAnimated(false);
		
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

		if (this.audioPlayer != null) {
			this.audioPlayer.deleteObserver(graphListener);
			this.audioPlayer.stop();
			this.audioPlayer = null;
		}
		
		this.clickReset();

		this.audioPlayer = new AudioPlayer(selectedFile);

		playThread = new Thread(this.audioPlayer);

		this.audioPlayer.addObserver(graphListener);

		playThread.start();
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

		Iterator<Entry<Slider, Label>> iter = equalizerSliderToLabel.entrySet().iterator();

		while (iter.hasNext()) {
			iter.next().getKey().setValue(1.0);
		}

		if (this.audioPlayer != null)
			this.audioPlayer.resetToDefault();
		
		this.delayCheck.setSelected(false);
		this.overdriveCheck.setSelected(false);

		soundSlider.setValue(0.3);
		this.overdriveSlider.setValue(1.0);
		this.delaySlider.setValue(1.0);
	}

	private void initializeEqualizerElements() {

		this.equalizerSliderToLabel.put(Slider_0, labelForSlider_0);
		this.equalizerSliderToLabel.put(Slider_1, labelForSlider_1);
		this.equalizerSliderToLabel.put(Slider_2, labelForSlider_2);
		this.equalizerSliderToLabel.put(Slider_3, labelForSlider_3);
		this.equalizerSliderToLabel.put(Slider_4, labelForSlider_4);
		this.equalizerSliderToLabel.put(Slider_5, labelForSlider_5);
		this.equalizerSliderToLabel.put(Slider_6, labelForSlider_6);

		Iterator<Entry<Slider, Label>> iter = equalizerSliderToLabel.entrySet().iterator();

		while (iter.hasNext()) {

			Entry<Slider, Label> sliderToLabel = iter.next();

			sliderToLabel.getKey().valueProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					String str = String.format("%.1f", (newValue.doubleValue()));
					sliderToLabel.getValue().setText(str);
					int filterID = EqualizerUtil.getSerialNumber(sliderToLabel.getKey().idProperty().getValue());
					audioPlayer.getEqualizer().getFilter(filterID).setGain((float) newValue.doubleValue());
				}
			});
		}
	}

	private void initializeEffectElements() {

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
		if (this.audioPlayer != null)
			this.audioPlayer.setDelay(this.delayCheck.isSelected());
	}

	@FXML
	private void createOverdrive() {
		if (this.audioPlayer != null)
			this.audioPlayer.setOverdrive(this.overdriveCheck.isSelected());
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
	}

	private class GraphListener implements Observer {

		@Override
		public void update(Observable o, Object arg) {
			if (audioPlayer.getFftReady() && graphFlag) {
				for (int i = 0; i < audioPlayer.getFTvlOutput().length; i++) {
					series2Data[i].setYValue(Math.log10(audioPlayer.getFTvlInput()[i]) / 10);
					series1Data[i].setYValue(Math.log10(audioPlayer.getFTvlOutput()[i] * 10) / 10);
				}
			}
		}

	}

}
