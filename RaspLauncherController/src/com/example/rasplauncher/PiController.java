package com.example.rasplauncher;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;

public class PiController {

	final GpioController mGpio = GpioFactory.getInstance();

	public void move(int distance) {
	}

	public void launch(int power) {
	}
	
	public static void main(String[] args) {
		final Serial serial = SerialFactory.createInstance();
		serial.addListener(new SerialDataListener() {
			
			@Override
			public void dataReceived(SerialDataEvent event) {
				System.out.println(event.getData());
			}
		});
		
		try {
			serial.open(Serial.DEFAULT_COM_PORT, 115200);
			for(int i = 0; i < 10; i++) {
			System.out.println("writing:" + i);
				serial.write("a");
				Thread.sleep(1000);
				serial.write("b");
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		try {
//
//			System.out.println("I2C Sender, parmeter [Paket size] [Loop count]");
//			int size = 96;
//			int loops = 3;
//			System.out.println("Starting, i2c size: "+size+", loops: "+loops);
//
//			System.out.println("get bus 1");
//			// get I2C bus instance
//			final I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
//
//			System.out.println("get device with id 4");
//			I2CDevice arduino = bus.getDevice(0x04);
//			byte[] buffer = new byte[size];
//			for (int i=0; i<buffer.length; i++) {
//				buffer[i] = (byte)i;
//			}
//
//			for (int i=0; i<loops; i++) {
//				System.out.println("send buffer now");
//
//				long l = System.currentTimeMillis();
//				//write(int address, byte[] buffer, int offset, int size) throws IOException        
//				arduino.write(buffer, 0, buffer.length);
//				long needed = System.currentTimeMillis() - l;
//				//arduino.write((byte)65);
//
//				System.out.println("done in "+needed+"ms");         
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}


