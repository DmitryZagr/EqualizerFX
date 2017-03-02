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
import ru.bmstu.www.effects.impl.Delay;
import ru.bmstu.www.effects.impl.Overdrive;
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

	private int countOfPointsOnPlot = 128;

	private GraphListener graphListener = new GraphListener();

	private Map<Slider, Label> equalizerSliderToLabel = new HashMap<>();

	private String overdrive = "Overdrive";
	private String delay = "Delay";

	private boolean draw = false;

	/**
	 * Initializes the controller class.
	 * 
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		this.initializeEqualizerElements();
		
		this.initializeEffectElements();
		
		this.initializeGraph();

		this.volumeFromSlider();

		initializeAudioPlayer();
	}
	
	@SuppressWarnings("unchecked")
	private void initializeGraph() {
		
		XYChart.Series<Integer, Number> series1 = new XYChart.Series<>();
		XYChart.Series<Integer, Number> series2 = new XYChart.Series<>();
		series1.setName("Модифицированный");
		series2.setName("Оригинал");
		series1Data = new XYChart.Data[this.countOfPointsOnPlot];
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

		yAxis.setAutoRanging(false);
		this.yAxis.setUpperBound(100);
		this.yAxis.setLowerBound(-10);
		this.yAxis.setAnimated(false);
	}

	private void initializeAudioPlayer() {
		this.audioPlayer = new AudioPlayer();
		this.audioPlayer.getEqualizer().bindEffect(overdrive, new Overdrive());
		this.audioPlayer.getEqualizer().bindEffect(delay, new Delay());
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

		this.audioPlayer.setMusicFile(selectedFile);
		this.audioPlayer.play();
		this.audioPlayer.addObserver(graphListener);
	}

	@FXML
	private void clickStopPlay() {
		if (this.audioPlayer != null) {
			if (!this.audioPlayer.isPause())
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
			iter.next().getKey().setValue(0.0);
		}

		this.delayCheck.setSelected(false);
		this.overdriveCheck.setSelected(false);

		soundSlider.setValue(0.5);
		this.overdriveSlider.setValue(0.5);
		this.delaySlider.setValue(1.0);

		this.audioPlayer.getEqualizer().setDefaults();
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

			sliderToLabel.getKey().setBlockIncrement(1.0);
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

				audioPlayer.getEqualizer().getEffect(overdrive).setCoef(newValue.doubleValue());
			}
		});

		delaySlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				audioPlayer.getEqualizer().getEffect(delay).setCoef(newValue.doubleValue());
			}
		});
	}

	private void volumeFromSlider() {
		soundSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				audioPlayer.getEqualizer().setVolume(newValue.doubleValue());
			}
		});
	}

	@FXML
	private void createDelay() {
		if (this.audioPlayer != null)
			this.audioPlayer.getEqualizer().getEffect(delay).setStatus(this.delayCheck.isSelected());
	}

	@FXML
	private void createOverdrive() {
		if (this.audioPlayer != null) {
			this.audioPlayer.getEqualizer().getEffect(overdrive).setStatus(this.overdriveCheck.isSelected());
		}
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
		this.graphFlag = this.graphCheck.isSelected();
	}

	private class GraphListener implements Observer {

		private double[] notModify = new double[countOfPointsOnPlot];
		private double[] modify = new double[countOfPointsOnPlot];

		@Override
		public void update(Observable o, Object arg) {
			if (audioPlayer.isFftReady() && graphFlag && !draw) {
				draw = true;
				System.arraycopy(audioPlayer.getFTvlOutput(), 0, modify, 0, countOfPointsOnPlot);
				System.arraycopy(audioPlayer.getFTvlInput(), 0, notModify, 0, countOfPointsOnPlot);
				for (int i = 0; i < countOfPointsOnPlot; i++) {
					series1Data[i].setYValue(20 * Math.log10(modify[i] /*/ 0.05*/));
					series2Data[i].setYValue(20 * Math.log10(notModify[i]));
				}
				draw = false;
			}
		}

	}

}
