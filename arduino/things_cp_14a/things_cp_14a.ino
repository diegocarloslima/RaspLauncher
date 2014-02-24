#include <Thing.h>
#include <Stepper.h>

void setup() {  
  //thing.addComponent("vermelho",    DIGITAL, 11);
  //thing.addComponent("verde",  DIGITAL, 12);
  thing.addComponent("roxo",   DIGITAL, 13);
 
  thing.addComponent("servoarm",     CUSTOM,  6, servo_read, servo_write_arm);
  thing.addComponent("speed",     CUSTOM,  9, servo_read, speed);
  thing.addComponent("dir",    CUSTOM,  9, servo_read, direction);
 
  thing.addMode(0, automation);
  thing.start(-1); //this mean that we dont want multiple behavior / loops here... any other number 
  Serial.begin(115200);
}

char* all() {
  
  //Serial.println(temp_out);
 // Serial.println(humidity);
  
}

void loop() {
  thing.loop();
  initStep();
}

void automation() {}

