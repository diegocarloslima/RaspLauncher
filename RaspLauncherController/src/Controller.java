import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

public class Controller {
	final GpioController mGpio = GpioFactory.getInstance();

	public void turnLeft(int duration) {
		final GpioPinDigitalOutput gpio01 = this.mGpio.provisionDigitalOutputPin(
				RaspiPin.GPIO_01, "turn_left");
		gpio01.pulse(duration, true);
	}

	public void turnRight(int duration) {
		final GpioPinDigitalOutput gpio02 = this.mGpio.provisionDigitalOutputPin(
				RaspiPin.GPIO_02, "turn_right");
		gpio02.pulse(duration, true);
	}

	public void shoot(int power) {
		final GpioPinDigitalOutput gpio03 = this.mGpio.provisionDigitalOutputPin(
				RaspiPin.GPIO_03, "shoot");
		gpio03.pulse(power, true);
	}

	private void load(int level) {
		final GpioPinDigitalOutput gpio04 = this.mGpio.provisionDigitalOutputPin(
				RaspiPin.GPIO_04, "load");
		gpio04.pulse(level, true);
	}
}
