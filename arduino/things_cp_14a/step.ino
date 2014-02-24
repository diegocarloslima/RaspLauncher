Stepper meuMotor(2037, 8, 10, 11, 9);
int directionv=5;
int speedv=10;

char* speed(char* args) {
  speedv = atoi(args);
  initStep();
  return args;
}   

char* direction(char* args) {
  directionv = atoi(args);
  initStep();  
  return args;
}   

void initStep() {
  Serial.println(directionv);  
  Serial.println(directionv);
  meuMotor.step(directionv);
  meuMotor.setSpeed(speedv);

}
